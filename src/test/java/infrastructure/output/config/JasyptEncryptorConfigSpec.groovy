package infrastructure.output.config

import com.bci.config.JasyptEncryptorConfig
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import spock.lang.Specification

class JasyptEncryptorConfigSpec extends Specification {

    // Sujeto de prueba (la clase que estamos probando)
    def jasyptEncryptorConfig = new JasyptEncryptorConfig()

    // Prueba para el metodo passwordEncryptor - éxito
    def "test passwordEncryptor - éxito"() {
        given: "Una configuración válida para el encriptador"
        def originalText = "textoParaEncriptar"

        when: "Se llama al método passwordEncryptor"
        def encryptor = jasyptEncryptorConfig.passwordEncryptor()

        then: "Se obtiene un encriptador configurado correctamente"
        encryptor != null
        encryptor instanceof PooledPBEStringEncryptor

        and: "El encriptador puede encriptar y desencriptar texto"
        def encryptedText = encryptor.encrypt(originalText)
        def decryptedText = encryptor.decrypt(encryptedText)
        decryptedText == originalText
    }
}
