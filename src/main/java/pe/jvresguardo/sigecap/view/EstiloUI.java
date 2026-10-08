package pe.jvresguardo.sigecap.view;

import java.awt.Color;
import java.awt.Font;

/**
 * Constantes visuales compartidas por las vistas de SIGECAP J&V.
 * Ver CLAUDE.md, seccion "Sistema visual de SIGECAP J&V".
 */
public final class EstiloUI {

    public static final Color COLOR_OSCURO = new Color(33, 41, 54);
    public static final Color COLOR_ACTIVO = new Color(51, 65, 85);
    public static final Color COLOR_FONDO = new Color(245, 246, 248);
    public static final Color COLOR_ACENTO = new Color(37, 99, 235);
    public static final Color COLOR_TEXTO_PRINCIPAL = new Color(31, 41, 55);
    public static final Color COLOR_TEXTO_SECUNDARIO = new Color(107, 114, 128);
    public static final Color COLOR_TEXTO_CLARO = Color.WHITE;

    public static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FUENTE_TEXTO = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FUENTE_TEXTO_SECUNDARIO = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FUENTE_MENU = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FUENTE_BOTON = new Font("SansSerif", Font.BOLD, 13);

    private EstiloUI() {
    }
}
