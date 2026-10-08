package pe.jvresguardo.sigecap.app;

import javax.swing.SwingUtilities;

import pe.jvresguardo.sigecap.view.LoginView;

public final class SigecapApplication {

    private SigecapApplication() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}