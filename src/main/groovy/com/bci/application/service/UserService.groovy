package com.bci.application.service

import com.bci.application.input.port.UserApi
import com.bci.application.service.mapper.UserServiceMapper
import com.bci.domain.LoginResponse
import com.bci.domain.Phone
import com.bci.domain.SignUpRequest
import com.bci.domain.SignUpResponse
import com.bci.domain.User
import com.bci.infrastructure.config.JasyptEncryptorConfig
import com.bci.infrastructure.helper.RequestValidator
import com.bci.infrastructure.output.adapter.UserAdapterRepository
import com.bci.infrastructure.output.repository.entity.PhoneData
import com.bci.infrastructure.output.repository.entity.UserData
import com.bci.infrastructure.security.JwtUtil
import groovy.transform.Canonical
import groovy.util.logging.Slf4j
import org.jasypt.encryption.StringEncryptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
@Canonical
class UserService implements UserApi {
    private final RequestValidator requestValidator
    private final UserAdapterRepository userAdapterRepository
    private final JwtUtil jwtUtil
    private final UserServiceMapper mapper

    @Autowired
    UserService(UserAdapterRepository userAdapterRepository, JwtUtil jwtUtil, RequestValidator requestValidator, UserServiceMapper mapper) {
        this.jwtUtil = jwtUtil
        this.userAdapterRepository = userAdapterRepository
        this.requestValidator = requestValidator
        this.mapper = mapper
    }


    @Override
    SignUpResponse signUp(SignUpRequest request) {
        try {
            // Validar request
            validateRequest(request)

            // Validar que el email no exista en la base de datos
            validateEmailExists(request.email)

            // Encriptar contraseña
            String passwordEncrypt = JasyptEncryptorConfig.passwordEncryptor().encrypt(request.password)
            log.info("sign-up - Password encriptado: {}", passwordEncrypt)

            // Generar token JWT
            String jwt = jwtUtil.generateToken(request.email)

            // Crear y guardar el usuario
            User userEntity = mapper.toDomain(request, passwordEncrypt, jwt)

            // Armar respuesta
            return mapper.toResponse(userAdapterRepository.save(userEntity))

        } catch (ErrorValidacionDTO | ErrorGeneralDTO e) {
            log.error("sign-up - Error: {}", e.message)
            throw e
        } catch (Exception e) {
            log.error("sign-up - Error general: {}", e.message)
            throw new ErrorGeneralDTO(0, "Error general: ${e.message}")
        } finally {
            log.info("sign-up - Fin ServiceImpl")
        }
    }

    @Override
    LoginResponse login(String token) {
        log.info("login - Inicio ServiceImpl")

        try {
            // Validar token
            validateToken(token)

            // Obtener email desde el token
            String email = jwtTokenUtil.getEmailFromJwt(token)
            log.info("login - Email obtenido: {}", email)

            // Buscar usuario por email
            UserData userEntity = findUserByEmail(email)

            // Validar token del usuario
            validateUserToken(userEntity, token)

            // Generar nuevo token y actualizar base de datos
            String newJwt = jwtTokenUtil.generarToken(email)
            updateUserTokenAndLastLogin(newJwt, email)

            // Armar respuesta
            return createLoginResponse(userEntity, newJwt)

        } catch (ErrorValidacionDTO | ErrorGeneralDTO e) {
            log.error("login - Error: {}", e.message)
            throw e
        } catch (Exception e) {
            log.error("login - Error general: {}", e.message)
            throw new ErrorGeneralDTO(0, "Error general: ${e.message}")
        } finally {
            log.info("login - Fin ServiceImpl")
        }
    }

    private void validateRequest(SignUpRequest request) {
        String validationRequest = requestValidator.validateRequestSignUp(request)
        if (validationRequest != "Valido") {
            log.error("sign-up - Error en validación del request: {}", validationRequest)
            throw new ErrorValidacionDTO(1, validationRequest)
        }

        if (!requestValidator.validateEmail(request.email)) {
            log.error("sign-up - Error en formato de email")
            throw new ErrorValidacionDTO(2, "El email no tiene formato válido.")
        }

        String validationPassword = requestValidator.validatePassword(request.password)
        if (validationPassword != "Valido") {
            log.error("sign-up - Error en validación de password: {}", validationPassword)
            throw new ErrorValidacionDTO(3, validationPassword)
        }
    }

    private void validateEmailExists(String email) {
        Optional<UserData> userEntityOptional = userAdapterRepository.findByEmail(email)
        if (userEntityOptional.isPresent()) {
            log.error("sign-up - Email ya registrado: {}", email)
            throw new ErrorValidacionDTO(4, "El email ya está registrado.")
        }
    }

    private void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            log.error("login - Token vacío o nulo")
            throw new ErrorValidacionDTO(1, "Token vacío o nulo")
        }

        if (jwtTokenUtil.isTokenExpirado(token)) {
            log.error("login - Token expirado")
            throw new ErrorValidacionDTO(2, "Token expirado")
        }
    }

    private UserData findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email).orElseThrow {
                log.error("login - Email no registrado: {}", email)
                throw new ErrorValidacionDTO(4, "El email no está registrado.")
            }
        } catch (RuntimeException e) {
            log.error("login - Error al buscar usuario: {}", e.message)
            throw new ErrorGeneralDTO(3, "Error al buscar usuario: ${e.message}")
        }
    }

    private void validateUserToken(UserData userEntity, String token) {
        if (userEntity.token != token) {
            log.error("login - Token no válido")
            throw new ErrorValidacionDTO(5, "Token no válido")
        }
    }

    private void updateUserTokenAndLastLogin(String newJwt, String email) {
        try {
            userRepository.actualizarTokenYLastLoginPorEmail(newJwt, LocalDate.now(), email)
            log.info("login - Token y last_login actualizados")
        } catch (RuntimeException e) {
            log.error("login - Error al actualizar token: {}", e.message)
            throw new ErrorGeneralDTO(6, "Error al actualizar token: ${e.message}")
        }
    }

    private LoginResponse createLoginResponse(UserData userEntity, String newJwt) {
        StringEncryptor stringEncryptor = JasyptEncryptorConfig.passwordEncryptor()
        return new LoginResponse(
                id: userEntity.id,
                created: userEntity.created,
                lastLogin: LocalDate.now(),
                token: newJwt,
                isActive: userEntity.isActive,
                name: userEntity.name,
                email: userEntity.email,
                password: stringEncryptor.decrypt(userEntity.password),
                phones: convertirEntityAPhone(userEntity.phones)
        )
    }

    private static List<Phone> convertirEntityAPhone(List<PhoneData> phones) {
        return phones?.collect { phoneEntity ->
            new Phone(
                    number: phoneEntity.number,
                    citycode: phoneEntity.cityCode,
                    countrycode: phoneEntity.countryCode
            )
        } ?: []
    }
}
