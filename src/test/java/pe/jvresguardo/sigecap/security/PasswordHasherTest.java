package pe.jvresguardo.sigecap.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordHasherTest {

    @Test
    void verificaCorrectamenteUnPasswordCorrecto() {
        String hash = PasswordHasher.hash("clave123");

        assertTrue(PasswordHasher.verificar("clave123", hash));
    }

    @Test
    void rechazaUnPasswordIncorrecto() {
        String hash = PasswordHasher.hash("clave123");

        assertFalse(PasswordHasher.verificar("otra-clave", hash));
    }

    @Test
    void dosHashesDelMismoPasswordSonDiferentesPorElSalt() {
        String hash1 = PasswordHasher.hash("clave123");
        String hash2 = PasswordHasher.hash("clave123");

        assertNotEquals(hash1, hash2);
    }

    @Test
    void noAlmacenaElPasswordEnTextoPlano() {
        String hash = PasswordHasher.hash("clave123");

        assertFalse(hash.contains("clave123"));
    }
}
