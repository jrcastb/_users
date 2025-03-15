package com.bci.service.impl;

import com.bci.domain.dto.LoginResponse;
import com.bci.domain.dto.SignUpRequest;
import com.bci.domain.dto.SignUpResponse;
import com.bci.domain.User;
import com.bci.config.JasyptEncryptorConfig;
import com.bci.exception.BusinessException;
import com.bci.exception.messages.BusinessErrorMessage;
import com.bci.helper.RequestValidator;
import com.bci.repository.impl.UserRepositoryImpl;
import com.bci.security.JwtUtil;
import com.bci.service.UserService;
import com.bci.service.mapper.UserServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RequestValidator requestValidator;
    private final UserRepositoryImpl userRepositoryImpl;
    private final JwtUtil jwtUtil;
    private final UserServiceMapper mapper;

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        validateRequest(request);
        validateEmailExists(request.getEmail());

        String passwordEncrypt = JasyptEncryptorConfig.passwordEncryptor().encrypt(request.getPassword());
        log.info("sign-up - Password encriptado: {}", passwordEncrypt);

        String jwt = jwtUtil.generateToken(request.getEmail());

        // Crear y guardar el usuario
        User userEntity = mapper.toDomain(request, passwordEncrypt, jwt);

        // Armar respuesta
        return mapper.toResponse(userRepositoryImpl.save(userEntity));
    }

    @Override
    public LoginResponse login(String token) {
        validateToken(token);

        String email = jwtUtil.getEmailFromToken(token);
        log.info("login - Email obtenido: {}", email);

        User user = userRepositoryImpl.findByEmail(email)
                .orElseThrow(() -> new BusinessException(BusinessErrorMessage.USER_NOT_FOUND));

        validateUserToken(user, token);

        String newJwt = jwtUtil.generateToken(email);
        updateUserTokenAndLastLogin(newJwt, email);

        return mapper.toLoginResponse(user, newJwt);
    }

    private void validateRequest(SignUpRequest request) {
        String validationRequest = requestValidator.validateRequestSignUp(request);
        if (!"Valido".equals(validationRequest)) {
            log.error("sign-up - Error en validación del request: {}", validationRequest);
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY);
        }

        if (!requestValidator.validateEmail(request.getEmail())) {
            log.error("sign-up - Error en formato de email");
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY);
        }

        String validationPassword = requestValidator.validatePassword(request.getPassword());
        if (!"Valido".equals(validationPassword)) {
            log.error("sign-up - Error en validación de password: {}", validationPassword);
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY);
        }
    }

    private void validateEmailExists(String email) {
        userRepositoryImpl.findByEmail(email).ifPresent(user -> {
            log.error("sign-up - Email ya registrado: {}", user.getEmail());
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY);
        });
    }

    private void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            log.error("login - Token vacío o nulo");
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY);
        }

        if (jwtUtil.isTokenExpired(token)) {
            log.error("login - Token expirado");
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY);
        }
    }

    private void validateUserToken(User userEntity, String token) {
        if (!userEntity.getToken().equals(token)) {
            log.error("login - Token no válido");
            throw new BusinessException(BusinessErrorMessage.BAD_REQUEST_BODY);
        }
    }

    private void updateUserTokenAndLastLogin(String newJwt, String email) {
        userRepositoryImpl.updateTokenAndLastLogin(newJwt, LocalDate.now(), email);
        log.info("login - Token y last_login actualizados");
    }
}