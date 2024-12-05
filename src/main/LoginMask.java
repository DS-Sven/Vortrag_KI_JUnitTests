
package LoginMaskProject.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginMask extends JFrame {
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JLabel statusLabel;
    private int loginAttempts = 0;

    public LoginMask() {
        setTitle("Login Maske");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Benutzername
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Benutzername:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        // Passwort
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Passwort:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Passwort anzeigen
        gbc.gridx = 2;
        JButton showPasswordButton = new JButton("👁");
        add(showPasswordButton, gbc);
        showPasswordButton.addActionListener(_ -> passwordField.setEchoChar(passwordField.getEchoChar() == '•' ? (char) 0 : '•'));

        // Statusanzeige
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        add(statusLabel, gbc);

        // Login Button
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JButton loginButton = new JButton("Login");
        add(loginButton, gbc);
        loginButton.addActionListener(new LoginActionListener());

        // Abbrechen Button
        gbc.gridx = 1;
        JButton cancelButton = new JButton("Abbrechen");
        add(cancelButton, gbc);
        cancelButton.addActionListener(_ -> {
            usernameField.setText("");
            passwordField.setText("");
            statusLabel.setText(" ");
        });

        setLocationRelativeTo(null); // Zentriert das Fenster
    }

    public class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty()) {
                statusLabel.setText("Benutzername darf nicht leer sein.");
                return;
            }

            if (!isValidPassword(password)) {
                statusLabel.setText("Passwort ungültig: Mind. 8 Zeichen, 1 Zahl, 1 Sonderzeichen.");
                return;
            }

            if (loginAttempts >= 3) {
                statusLabel.setText("Zu viele fehlgeschlagene Versuche. Zugang gesperrt.");
                return;
            }

            if (authenticate(username, password)) {
                statusLabel.setForeground(Color.GREEN);
                statusLabel.setText("Login erfolgreich!");
            } else {
                loginAttempts++;
                statusLabel.setText("Login fehlgeschlagen. Versuche: " + loginAttempts);
            }
        }

        private boolean isValidPassword(String password) {
            return password.length() >= 8 &&
                    password.matches(".*\\d.*") &&
                    password.matches(".*\\W.*");
        }

        private boolean authenticate(String username, String password) {
            // Hier sollte die Authentifizierung gegen eine Datenbank oder ein anderes System erfolgen
            // für das Beispiel: Benutzername = "admin", Passwort = "admin123!"
            return "admin".equals(username) && "admin123!".equals(password);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginMask loginMask = new LoginMask();
            loginMask.setVisible(true);
        });
    }
}
