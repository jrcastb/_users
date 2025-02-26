package infrastructure.output.security

import com.bci.infrastructure.security.WebSecurityConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = WebSecurityConfig) // Especifica la clase de configuración de seguridad
@AutoConfigureMockMvc
class WebSecurityConfigSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    // Prueba para verificar que CORS está configurado correctamente
    def "test CORS configuration"() {
        when: "Se realiza una solicitud con origen permitido"
        def result = mockMvc.perform(get("/some-endpoint")
                .header("Origin", "https://allowed-origin.com"))

        then: "La respuesta incluye los encabezados CORS correctos"
        result.andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://allowed-origin.com"))
                .andExpect(header().string("Access-Control-Allow-Methods", "*"))
                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"))
    }

    // Prueba para verificar que CSRF está desactivado
    @WithMockUser
    def "test CSRF is disabled"() {
        when: "Se realiza una solicitud POST sin token CSRF"
        def result = mockMvc.perform(post("/some-endpoint")
                .content("{}")
                .contentType("application/json"))

        then: "La solicitud es exitosa (CSRF está desactivado)"
        result.andExpect(status().isOk())
    }

    // Prueba para verificar que todas las solicitudes están permitidas
    @WithMockUser
    def "test all requests are permitted"() {
        when: "Se realiza una solicitud a cualquier endpoint"
        def result = mockMvc.perform(get("/any-endpoint"))

        then: "La solicitud es exitosa"
        result.andExpect(status().isOk())
    }
}
