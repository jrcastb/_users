package infrastructure.input.adapter.rest

import com.bci.service.UserService
import com.bci.domain.dto.SignUpRequest
import com.bci.domain.dto.SignUpResponse
import com.bci.domain.dto.LoginResponse
import com.bci.controller.UserController
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class UserControllerSpec extends Specification {

    // Mocks para las dependencias
    def userService = Mock(UserService)

    // Sujeto de prueba (la clase que estamos probando)
    @Subject
    def userController = new UserController(userService)

    // Datos de prueba
    def signUpRequest = new SignUpRequest(email: "test@example.com", password: "Password123")
    def signUpResponse = new SignUpResponse(id: UUID.randomUUID(), created: LocalDate.now(), token: "valid.token.jwt")
    def loginResponse = new LoginResponse(id: UUID.randomUUID(), email: "test@example.com", token: "valid.token.jwt")
    def token = "valid.token.jwt"

    // Prueba para el metodo signUp - éxito
    def "test signUp - éxito"() {
        given: "Un SignUpRequest válido"
        userService.signUp(signUpRequest) >> signUpResponse

        when: "Se llama al método signUp"
        def result = userController.signUp(signUpRequest)

        then: "Se retorna una respuesta con estado CREATED y el SignUpResponse correcto"
        result.statusCode == HttpStatus.CREATED
        result.body == signUpResponse
    }

    // Prueba para el metodo signUp - lanza excepción
    def "test signUp - lanza excepción"() {
        given: "Un error en el servicio al registrar el usuario"
        userService.signUp(signUpRequest) >> { throw new RuntimeException("Error en el servicio") }

        when: "Se llama al método signUp"
        userController.signUp(signUpRequest)

        then: "Se lanza una excepción"
        thrown(RuntimeException)
    }

    // Prueba para el metodo login - éxito
    def "test login - éxito"() {
        given: "Un token válido"
        userService.login(token) >> loginResponse

        when: "Se llama al método login"
        def result = userController.login(token)

        then: "Se retorna una respuesta con estado OK y el LoginResponse correcto"
        result.statusCode == HttpStatus.OK
        result.body == loginResponse
    }

    // Prueba para el metodo login - lanza excepción
    def "test login - lanza excepción"() {
        given: "Un error en el servicio al hacer login"
        userService.login(token) >> { throw new RuntimeException("Error en el servicio") }

        when: "Se llama al método login"
        userController.login(token)

        then: "Se lanza una excepción"
        thrown(RuntimeException)
    }
}
