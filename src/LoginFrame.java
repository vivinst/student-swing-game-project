import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private PlayerService  playerService;

    public LoginFrame() {
        playerService = new PlayerService();

        setTitle("Login");
        setSize(340, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel(""));
        btnLogin = new JButton("Login");
        panel.add(btnLogin);

        add(panel);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your username and password!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Player player = playerService.login(username, password);

            if (player != null) {
                MainMenuFrame menuFrame = new MainMenuFrame(player);
                menuFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        txtPassword.addActionListener(e -> btnLogin.doClick());
    }
}