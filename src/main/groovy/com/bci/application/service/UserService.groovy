package com.bci.application.service

import com.bci.application.input.port.UserApi
import com.bci.application.service.mapper.UserServiceMapper
import com.bci.domain.LoginResponse
import com.bci.domain.SignUpRequest
import com.bci.domain.SignUpResponse
import com.bci.domain.User
import com.bci.infrastructure.config.JasyptEncryptorConfig
import com.bci.infrastructure.exception.BusinessException
import com.bci.infrastructure.exception.messages.BusinessErrorMessage
import com.bci.infrastructure.helper.RequestValidator
import com.bci.infrastructure.output.adapter.UserAdapterRepository
import com.bci.infrastructure.security.JwtUtil
import groovy.util.logging.Slf4j
import org.jasypt.encryption.StringEncryptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDate

@Service
@Slf4j
class UserService implements UserApi {
    private final RequestValidator requestValidator
    private final UserAdapterRepository userAdapterRepository
    private final JwtUtil jwtUtil
    private final UserServiceMapper mapper

    @Autowired
    UserService(UserAdapterRepository userAdapterRepository, JwtUtil jwtUtil, RequestValidator requestValidator , UserServiceMapper mapper){
        this.jwtUtil = jwtUtil
        this.userAdapterRepository = userAdapterRepository
        this.requestValidator = requestValidator
        this.mapper = mapper
    }


    @Override
    SignUpResponse signUp(SignUpRequest request) {

        validateRequest(request)
        validateEmailExists(request.email)
        String passwordEncrypt = JasyptEncryptorConfig.passwordEncryptor().encrypt(request.password)
        log.info("sign-up - Password encriptado: {}", passwordEncrypt)
        String jwt = jwtUtil.generateToken(request.email)

        // Crear y guardar el usuario
        User userEntity = mapper.toDomain(request, passwordEncrypt, jwt)

        // Armar respuesta
        return mapper.toResponse(userAdapterRepository.save(userEntity))
    }

    @Override
    LoginResponse login(String token) {
        validateToken(token)
        String email = jwtUtil.getEmailFromToken(token)
        log.info("login - Email obtenido: {}", email)
        User user = userAdapterRepository.findByEmail(email).orElse(null)
        validateUserToken(user, token)
        String newJwt = jwtUtil.generateToken(email)
        updateUserTokenAndLastLogin(newJwt, email)
        StringEncryptor stringEncryptor = JasyptEncryptorConfig.passwordEncryptor()
        LoginResponse response = mapper.toLoginResponse(user, newJwt)
        return response

    }

    private void validateRequest(SignUpRequest request) {
        String validationRequest = requestValidator.validateRequestSignUp(request)
        if (validationRequest != "Valido") {
            log.error("sign-up - Error en validación del request: {}", validationRequest)
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)
        }

        if (!requestValidator.validateEmail(request.email)) {
            log.error("sign-up - Error en formato de email")
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)
        }

        String validationPassword = requestValidator.validatePassword(request.password)
        if (validationPassword != "Valido") {
            log.error("sign-up - Error en validación de password: {}", validationPassword)
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)
        }
    }

    private void validateEmailExists(String email) {
        userAdapterRepository.findByEmail(email).ifPresent { user ->
            log.error("sign-up - Email ya registrado: {}", user.email)
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)
        }
    }

    private void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            log.error("login - Token vacío o nulo")
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)
        }

        if (jwtUtil.isTokenExpired(token)) {
            log.error("login - Token expirado")
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)
        }
    }

    private static void validateUserToken(User userEntity, String token) {
        if (userEntity.token != token) {
            log.error("login - Token no válido")
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY)
        }
    }

    private void updateUserTokenAndLastLogin(String newJwt, String email) {
        userAdapterRepository.updateTokenAndLastLogin(newJwt, LocalDate.now(), email)
        log.info("login - Token y last_login actualizados")
    }
}
