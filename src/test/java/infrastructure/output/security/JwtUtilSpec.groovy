import com.bci.infrastructure.security.JwtUtil
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import spock.lang.Specification
import spock.lang.Subject

import java.util.Date

class JwtUtilSpec extends Specification {

    // Sujeto de prueba (la clase que estamos probando)
    @Subject
    def jwtUtil = new JwtUtil()

    // Datos de prueba
    def secret = "secretKey"
    def expiration = 3600L // 1 hora en segundos
    def email = "test@example.com"
    def token = "valid.token.jwt"
    def expiredToken = "expired.token.jwt"

    def setup() {
        jwtUtil.secret = secret
        jwtUtil.expiration = expiration
    }

    // Prueba para el metodo generateToken - éxito
    def "test generateToken - éxito"() {
        given: "Un email válido"
        def expectedToken = "generated.token.jwt"

        when: "Se llama al método generateToken"
        def result = jwtUtil.generateToken(email)

        then: "Se genera un token JWT válido"
        result != null
        result.split("\\.").length == 3 // Un token JWT válido tiene 3 partes separadas por puntos
    }

    // Prueba para el metodo generateToken - lanza RuntimeException
    def "test generateToken - lanza RuntimeException"() {
        given: "Un error al generar el token"
        jwtUtil.secret = null // Provocamos un error al no tener una clave secreta

        when: "Se llama al método generateToken"
        jwtUtil.generateToken(email)

        then: "Se lanza una RuntimeException"
        thrown(RuntimeException)
    }

    // Prueba para el metodo getAllClaimsFromToken - éxito
    def "test getAllClaimsFromToken - éxito"() {
        given: "Un token válido"
        def claims = Jwts.claims().setSubject(email)
        def expectedToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()

        when: "Se llama al método getAllClaimsFromToken"
        def result = jwtUtil.getAllClaimsFromToken(expectedToken)

        then: "Se obtienen los claims del token"
        result != null
        result.getSubject() == email
    }

    // Prueba para el metodo getAllClaimsFromToken - lanza RuntimeException
    def "test getAllClaimsFromToken - lanza RuntimeException"() {
        given: "Un token inválido"
        def invalidToken = "invalid.token.jwt"

        when: "Se llama al método getAllClaimsFromToken"
        jwtUtil.getAllClaimsFromToken(invalidToken)

        then: "Se lanza una RuntimeException"
        thrown(RuntimeException)
    }

    // Prueba para el metodo isTokenExpired - token no expirado
    def "test isTokenExpired - token no expirado"() {
        given: "Un token no expirado"
        def claims = Jwts.claims().setSubject(email)
        claims.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
        def validToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()

        when: "Se llama al método isTokenExpired"
        def result = jwtUtil.isTokenExpired(validToken)

        then: "El token no está expirado"
        !result
    }

    // Prueba para el metodo isTokenExpired - token expirado
    def "test isTokenExpired - token expirado"() {
        given: "Un token expirado"
        def claims = Jwts.claims().setSubject(email)
        claims.setExpiration(new Date(System.currentTimeMillis() - expiration * 1000)) // Fecha en el pasado
        def expiredToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()

        when: "Se llama al método isTokenExpired"
        def result = jwtUtil.isTokenExpired(expiredToken)

        then: "El token está expirado"
        thrown(RuntimeException)
    }

    // Prueba para el metodo getEmailFromToken - éxito
    def "test getEmailFromToken - éxito"() {
        given: "Un token válido con un email"
        def claims = Jwts.claims().setSubject(email)
        def validToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()

        when: "Se llama al método getEmailFromToken"
        def result = jwtUtil.getEmailFromToken(validToken)

        then: "Se obtiene el email del token"
        result == email
    }

    // Prueba para el metodo getEmailFromToken - lanza RuntimeException
    def "test getEmailFromToken - lanza RuntimeException"() {
        given: "Un token inválido"
        def invalidToken = "invalid.token.jwt"

        when: "Se llama al método getEmailFromToken"
        jwtUtil.getEmailFromToken(invalidToken)

        then: "Se lanza una RuntimeException"
        thrown(RuntimeException)
    }
}