package pe.jvresguardo.sigecap.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * HU-JVR-013
 * Hashing y verificacion de contraseñas con PBKDF2 (API estandar de Java).
 * Formato almacenado: iteraciones:salt:hash (salt y hash en Base64).
 */
public final class PasswordHasher {

    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final int ITERACIONES = 120_000;
    private static final int LONGITUD_SALT = 16;
    private static final int LONGITUD_HASH_BITS = 256;

    private PasswordHasher() {
    }

    public static String hash(String password) {
        byte[] salt = generarSalt();
        byte[] hash = derivar(password.toCharArray(), salt, ITERACIONES);
        return ITERACIONES + ":" + codificar(salt) + ":" + codificar(hash);
    }

    public static boolean verificar(String password, String valorAlmacenado) {
        String[] partes = valorAlmacenado.split(":");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato de hash invalido");
        }

        int iteraciones = Integer.parseInt(partes[0]);
        byte[] salt = Base64.getDecoder().decode(partes[1]);
        byte[] hashEsperado = Base64.getDecoder().decode(partes[2]);

        byte[] hashCalculado = derivar(password.toCharArray(), salt, iteraciones);
        return MessageDigest.isEqual(hashCalculado, hashEsperado);
    }

    private static byte[] derivar(char[] password, byte[] salt, int iteraciones) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iteraciones, LONGITUD_HASH_BITS);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITMO);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("No se pudo calcular el hash de la contraseña", e);
        }
    }

    private static byte[] generarSalt() {
        byte[] salt = new byte[LONGITUD_SALT];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private static String codificar(byte[] datos) {
        return Base64.getEncoder().encodeToString(datos);
    }
}
