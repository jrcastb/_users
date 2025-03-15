package com.bci.helper;

import com.bci.domain.dto.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Slf4j
public class RequestValidator {

    // Expresiones regulares compiladas para reutilización
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{2,}$");
    private static final Pattern PASSWORD_LENGTH_PATTERN = Pattern.compile("^.{8,12}$");
    private static final Pattern PASSWORD_DIGITS_PATTERN = Pattern.compile("^(?=(?:[^0-9]*\\d){2}[^0-9]*$).*");
    private static final Pattern PASSWORD_UPPERCASE_PATTERN = Pattern.compile("^(?=[^A-Z]*[A-Z][^A-Z]*$).*");

    public String validateRequestSignUp(SignUpRequest request) {
        final String initialMessage = "Error en la validación de los campos [";
        StringBuilder response = new StringBuilder(initialMessage);

        if (!validateRequest(request.getEmail())) {
            response.append(" email,");
        }

        if (!validateRequest(request.getPassword())) {
            response.append(" password,");
        }

        if (request.getPhones() != null) {
            for (int i = 0; i < request.getPhones().size(); i++) {
                final String model = " (phone posición=" + (i + 1) + "-";
                StringBuilder temp = new StringBuilder(model);

                if (!validateRequest(request.getPhones().get(i).getCountryCode())) {
                    temp.append(" countryCode,");
                }

                if (!temp.toString().equals(model)) {
                    temp.append(")");
                    response.append(temp);
                }
            }
        }

        if (response.toString().equals(initialMessage)) {
            return "Valido";
        } else {
            response.append(" ]");
            return response.toString().replaceAll(",,", ",");
        }
    }

    private boolean validateRequest(String request) {
        return request != null && !request.isEmpty();
    }

    public boolean validateEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public String validatePassword(String password) {
        if (password == null) {
            return "La contraseña no puede ser nula";
        }

        if (!PASSWORD_LENGTH_PATTERN.matcher(password).matches()) {
            return "Debe tener entre 8 y 12 caracteres";
        }

        if (!PASSWORD_DIGITS_PATTERN.matcher(password).matches()) {
            return "Debe tener exactamente 2 dígitos";
        }

        if (!PASSWORD_UPPERCASE_PATTERN.matcher(password).matches()) {
            return "Debe tener exactamente una mayúscula";
        }

        return "Valido";
    }
}