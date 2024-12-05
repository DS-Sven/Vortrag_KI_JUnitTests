package LoginMaskProject.test;

import LoginMaskProject.main.LoginMask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginMaskTest {

    private LoginMask loginMask;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private LoginMask.LoginActionListener loginActionListener;

    @BeforeEach
    void setUp() {
        loginMask = new LoginMask();
        usernameField = loginMask.usernameField;
        passwordField = loginMask.passwordField;
        statusLabel = loginMask.statusLabel;
        loginActionListener = loginMask.new LoginActionListener();
    }

    @ParameterizedTest(name = "[{index}] Benutzername: {0}, Passwort: {1}, Erwartet: {2}")
    @CsvFileSource(resources = "resources/test-login-data.csv", numLinesToSkip = 1)
    void testLoginValidation(String username, String password, String expectedMessage) {
        usernameField.setText(username);
        passwordField.setText(password);
        loginActionListener.actionPerformed(null);
        assertEquals(expectedMessage, statusLabel.getText());
    }
}