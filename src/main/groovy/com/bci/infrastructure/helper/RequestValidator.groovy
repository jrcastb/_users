package com.bci.infrastructure.helper

import com.bci.domain.SignUpRequest
import org.springframework.stereotype.Component
import java.util.regex.Pattern

import groovy.util.logging.Slf4j

@Component
@Slf4j
class RequestValidator {

    // Expresiones regulares compiladas para reutilización
    private static final Pattern EMAIL_PATTERN = Pattern.compile('^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{2,}$')
    private static final Pattern PASSWORD_LENGTH_PATTERN = Pattern.compile('^.{8,12}$')
    private static final Pattern PASSWORD_DIGITS_PATTERN = Pattern.compile('^(?=(?:[^0-9]*\\d){2}[^0-9]*$).*')
    private static final Pattern PASSWORD_UPPERCASE_PATTERN = Pattern.compile('^(?=[^A-Z]*[A-Z][^A-Z]*$).*')

    static String validateRequestSignUp(SignUpRequest request) {
        final String initialMessage = "Error en la validación de los campos ["
        StringBuilder response = new StringBuilder(initialMessage)

        if (!validateRequest(request.email)) {
            response.append(" email,")
        }

        if (!validateRequest(request.password)) {
            response.append(" password,")
        }

        if (request.phones != null) {
            request.phones.eachWithIndex { phone, index ->
                final String model = " (phone posición=${index + 1}-"
                StringBuilder temp = new StringBuilder(model)

                if (!validateRequest(phone.countryCode)) {
                    temp.append(" countryCode,")
                }

                if (temp.toString() != model) {
                    temp.append(")")
                    response.append(temp)
                }
            }
        }

        if (response.toString() == initialMessage) {
            return "Valido"
        } else {
            response.append(" ]")
            return response.toString().replaceAll(",,", ",")
        }
    }

    private static Boolean validateRequest(String request) {
        return request != null && !request.isEmpty()
    }

    static boolean validateEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches()
    }

    static String validatePassword(String password) {
        if (password == null) {
            return "La contraseña no puede ser nula"
        }

        if (!PASSWORD_LENGTH_PATTERN.matcher(password).matches()) {
            return "Debe tener entre 8 y 12 caracteres"
        }

        if (!PASSWORD_DIGITS_PATTERN.matcher(password).matches()) {
            return "Debe tener exactamente 2 dígitos"
        }

        if (!PASSWORD_UPPERCASE_PATTERN.matcher(password).matches()) {
            return "Debe tener exactamente una mayúscula"
        }

        return "Valido"
    }
}
