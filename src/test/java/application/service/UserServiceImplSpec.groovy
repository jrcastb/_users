package application.service

import com.bci.service.impl.UserServiceImpl
import com.bci.service.mapper.UserServiceMapperImpl
import com.bci.domain.dto.LoginResponse
import com.bci.domain.Phone
import com.bci.domain.dto.SignUpRequest
import com.bci.domain.dto.SignUpResponse
import com.bci.domain.User
import com.bci.exception.BusinessException
import com.bci.helper.RequestValidator
import com.bci.repository.impl.UserRepositoryImpl
import com.bci.security.JwtUtil
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class UserServiceImplSpec extends Specification {

    // Mocks para las dependencias
    def userAdapterRepository = Mock(UserRepositoryImpl)
    def jwtUtil = Mock(JwtUtil)
    def requestValidator = Mock(RequestValidator)
    def mapper = Mock(UserServiceMapperImpl)

    // Sujeto de prueba (la clase que estamos probando)
    @Subject
    def userService = new UserServiceImpl(requestValidator, userAdapterRepository, jwtUtil, mapper)

    // Datos de prueba
    def validEmail = "test@example.com"
    def validPassword = "Jc@tr34md"
    def validToken = "valid.token.jwt"
    def invalidToken = "invalid.token.jwt"
    def encryptedPassword = "encryptedPassword"
    def name = "Jose Castillo"
    def validPhone = Phone.builder().number(311569489).cityCode(10).countryCode("+57").build()
    def userId = UUID.randomUUID()
    def createdDate = LocalDate.now()
    def lastLoginDate = LocalDate.now()
    def invalidEmail = "testexample.com"
    def user = new User(id: userId, email: validEmail, password: encryptedPassword, token: validToken, created: createdDate, lastLogin: lastLoginDate, isActive: true, name: name, phones: List.of(validPhone))
    def signUpRequest = new SignUpRequest(name: name, email: validEmail, password: validPassword, phones: List.of(validPhone))
    def invalidSignUpRequest = new SignUpRequest(name: name, email: invalidEmail, password: validPassword, phones: List.of(validPhone))
    def signUpResponse = new SignUpResponse(id: userId, created: createdDate, lastLogin: lastLoginDate, token: validToken, isActive: true)
    def loginResponse = new LoginResponse(id: userId, created: createdDate, lastLogin: lastLoginDate, token: validToken, isActive: true, name: name, email: validEmail, password: validPassword, phones: List.of(validPhone))

    // Prueba para el metodo signUp
    /*
    def "test signUp - éxito"() {
        given: "Un request válido y un email no registrado"
        requestValidator.validateRequestSignUp(signUpRequest) >> "Valido"
        requestValidator.validateEmail(validEmail) >> true
        requestValidator.validatePassword(validPassword) >> "Valido"
        userAdapterRepository.findByEmail(validEmail) >> Optional.empty()
        jwtUtil.generateToken(validEmail) >> validToken
        //mapper.toDomain(signUpRequest, encryptedPassword, validToken) >> user
        userAdapterRepository.save(user) >> user
        //mapper.toResponse(user) >> signUpResponse


        when: "Se llama al metodo signUp"
        def result = userService.signUp(signUpRequest)

        then: "Se retorna una respuesta exitosa"
        result == signUpResponse
        result.id == userId
        result.created == createdDate
        result.lastLogin == lastLoginDate
        result.token == validToken
        result.isActive == true
        1 * userAdapterRepository.save(user)
    }*/

    def "test signUp - falla por email ya registrado"() {
        given: "Un email ya registrado"
        requestValidator.validateRequestSignUp(signUpRequest) >> "Valido"
        requestValidator.validateEmail(validEmail) >> true
        requestValidator.validatePassword(validPassword) >> "Valido"
        userAdapterRepository.findByEmail(validEmail) >> Optional.of(user)

        when: "Se llama al método signUp"
        userService.signUp(signUpRequest)

        then: "Se lanza una excepción"
        thrown(BusinessException)
    }

    def "test signUp - falla por validación de request inválida"() {
        given: "Un request inválido"
        requestValidator.validateRequestSignUp(invalidSignUpRequest) >> "Error en validacion"

        when: "Se llama al método signUp"
        userService.signUp(invalidSignUpRequest)

        then: "Se lanza una excepción"
        thrown(BusinessException)
    }

    // Prueba para el metodo login
    def "test login - éxito"() {
        given: "Un token válido y un usuario existente"
        jwtUtil.getEmailFromToken(validToken) >> validEmail
        userAdapterRepository.findByEmail(validEmail) >> Optional.of(user)
        jwtUtil.generateToken(validEmail) >> validToken
        mapper.toLoginResponse(user, validToken) >> loginResponse


        when: "Se llama al método login"
        def result = userService.login(validToken)

        then: "Se retorna una respuesta exitosa"
        result == loginResponse
        1 * userAdapterRepository.updateTokenAndLastLogin(validToken, _ as LocalDate, validEmail)
    }

    def "test login - falla por token expirado"() {
        given: "Un token expirado"
        jwtUtil.isTokenExpired(invalidToken) >> true

        when: "Se llama al método login"
        userService.login(invalidToken)

        then: "Se lanza una excepción"
        thrown(BusinessException)
    }

    def "test login - falla por token no válido"() {
        given: "Un token no válido"
        jwtUtil.getEmailFromToken(invalidToken) >> validEmail
        userAdapterRepository.findByEmail(validEmail) >> Optional.of(user)
        user.token = "otro.token"

        when: "Se llama al método login"
        userService.login(invalidToken)

        then: "Se lanza una excepción"
        thrown(BusinessException)
    }

    def "test login - falla por token vacío o nulo"() {
        when: "Se llama al método login con un token nulo"
        userService.login(null)

        then: "Se lanza una excepción"
        thrown(BusinessException)
    }
}