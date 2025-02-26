package infrastructure.output.adapter

import com.bci.domain.User
import com.bci.infrastructure.exception.TechnicalException
import com.bci.infrastructure.exception.messages.TechnicalErrorMessage
import com.bci.infrastructure.output.adapter.UserAdapterRepository
import com.bci.infrastructure.output.adapter.mapper.UserMapper
import com.bci.infrastructure.output.repository.UserRepository
import com.bci.infrastructure.output.repository.entity.UserData
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class UserAdapterRepositorySpec extends Specification {

    // Mocks para las dependencias
    def userRepository = Mock(UserRepository)
    def userMapper = Mock(UserMapper)

    // Sujeto de prueba (la clase que estamos probando)
    @Subject
    def userAdapterRepository = new UserAdapterRepository(userRepository, userMapper)

    // Datos de prueba
    def email = "test@example.com"
    def token = "valid.token.jwt"
    def lastLogin = LocalDate.now()
    def user = new User(email: email)
    def userData = new UserData(email: email)
    def userId = UUID.randomUUID()
    def savedUser = new User(id: userId, email: email)

    // Prueba para el metodo findByEmail - éxito
    def "test findByEmail - éxito"() {
        given: "Un email válido y un usuario existente en la base de datos"
        userRepository.findByEmail(email) >> Optional.of(userData)
        userMapper.toDomain(userData) >> user

        when: "Se llama al método findByEmail"
        def result = userAdapterRepository.findByEmail(email)

        then: "Se retorna un Optional con el usuario"
        result.isPresent()
        result.get() == user
    }

    // Prueba para el metodo findByEmail - no se encuentra el usuario
    def "test findByEmail - no se encuentra el usuario"() {
        given: "Un email válido pero no existe en la base de datos"
        userRepository.findByEmail(email) >> Optional.empty()

        when: "Se llama al método findByEmail"
        def result = userAdapterRepository.findByEmail(email)

        then: "Se retorna un Optional vacío"
        !result.isPresent()
    }

    // Prueba para el metodo findByEmail - lanza TechnicalException
    def "test findByEmail - lanza TechnicalException"() {
        given: "Un error al buscar el usuario en la base de datos"
        userRepository.findByEmail(email) >> { throw new RuntimeException("Error de base de datos") }

        when: "Se llama al método findByEmail"
        userAdapterRepository.findByEmail(email)

        then: "Se lanza una TechnicalException"
        thrown(TechnicalException)
    }


    // Prueba para el metodo updateTokenAndLastLogin - éxito
    def "test updateTokenAndLastLogin - éxito"() {
        given: "Un token, lastLogin y email válidos"
        userRepository.updateTokenAndLastLoginByEmail(token, lastLogin, email) >> {}

        when: "Se llama al método updateTokenAndLastLogin"
        userAdapterRepository.updateTokenAndLastLogin(token, lastLogin, email)

        then: "No se lanza ninguna excepción"
        noExceptionThrown()
    }

    // Prueba para el metodo updateTokenAndLastLogin - lanza TechnicalException
    def "test updateTokenAndLastLogin - lanza TechnicalException"() {
        given: "Un error al actualizar el token y lastLogin en la base de datos"
        userRepository.updateTokenAndLastLoginByEmail(token, lastLogin, email) >> { throw new RuntimeException("Error de base de datos") }

        when: "Se llama al método updateTokenAndLastLogin"
        userAdapterRepository.updateTokenAndLastLogin(token, lastLogin, email)

        then: "Se lanza una TechnicalException"
        thrown(TechnicalException)
    }

    // Prueba para el metodo save - éxito
    def "test save - éxito"() {
        given: "Un usuario válido para guardar"
        userMapper.toEntity(user) >> userData
        userRepository.save(userData) >> userData
        userMapper.toDomain(userData) >> savedUser

        when: "Se llama al método save"
        def result = userAdapterRepository.save(user)

        then: "Se retorna el usuario guardado"
        result == savedUser
    }

    // Prueba para el metodo save - lanza TechnicalException
    def "test save - lanza TechnicalException"() {
        given: "Un error al guardar el usuario en la base de datos"
        userMapper.toEntity(user) >> userData
        userRepository.save(userData) >> { throw new RuntimeException("Error de base de datos") }

        when: "Se llama al método save"
        userAdapterRepository.save(user)

        then: "Se lanza una TechnicalException"
        thrown(TechnicalException)
    }
}
