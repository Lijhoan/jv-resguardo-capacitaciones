package pe.jvresguardo.sigecap.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class SigecapApplicationTest {

    @Test
    void mainConfirmsApplicationStartup() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SigecapApplication.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("SIGECAP J&V iniciado correctamente." + System.lineSeparator(),
                output.toString(StandardCharsets.UTF_8));
    }
}