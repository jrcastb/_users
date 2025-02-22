package com.bci.infrastructure.config


import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JasyptEncryptorConfig {

    // Constantes para la configuración
    private static final String ENCRYPTION_PASSWORD = System.getenv("JASYPT_ENCRYPTION_PASSWORD") ?: "defaultPassword"
    private static final String ALGORITHM = "PBEWithMD5AndDES"
    private static final String KEY_OBTENTION_ITERATIONS = "1000"
    private static final String POOL_SIZE = "1"
    private static final String PROVIDER_NAME = "SunJCE"
    private static final String SALT_GENERATOR = "org.jasypt.salt.RandomSaltGenerator"
    private static final String OUTPUT_TYPE = "base64"

    /**
     * Crea y configura un encriptador de cadenas utilizando Jasypt.
     *
     * @return Un StringEncryptor configurado.
     * @throws IllegalArgumentException Si la configuración es inválida.
     */
    static PooledPBEStringEncryptor passwordEncryptor() {
        try {
            PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor()
            SimpleStringPBEConfig config = new SimpleStringPBEConfig()

            // Configuración del encriptador
            config.setPassword(ENCRYPTION_PASSWORD)  // Clave de encriptación (obtenida desde variables de entorno)
            config.setAlgorithm(ALGORITHM)
            config.setKeyObtentionIterations(KEY_OBTENTION_ITERATIONS)
            config.setPoolSize(POOL_SIZE)
            config.setProviderName(PROVIDER_NAME)
            config.setSaltGeneratorClassName(SALT_GENERATOR)
            config.setStringOutputType(OUTPUT_TYPE)

            // Validar configuración antes de asignarla
            validateConfig(config)

            encryptor.setConfig(config)
            return encryptor
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al configurar el encriptador Jasypt", e)
        }
    }

    /**
     * Valida la configuración del encriptador.
     *
     * @param config La configuración a validar.
     * @throws IllegalArgumentException Si la configuración es inválida.
     */
    private static void validateConfig(SimpleStringPBEConfig config) {
        if (config.getPassword() == null || config.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La clave de encriptación no puede estar vacía")
        }
        if (config.getAlgorithm() == null || config.getAlgorithm().isEmpty()) {
            throw new IllegalArgumentException("El algoritmo de encriptación no puede estar vacío")
        }
    }
}
