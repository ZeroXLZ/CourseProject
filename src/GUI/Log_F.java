package GUI;

import AdditionalP.GhostTextClass.GhostText;
import AdditionalP.IsNullOrEmpty;
import Logic.DataBase;
import Logic.Worker;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Log_F extends JFrame {

    private static JPanel panel;
    protected static Log_F frame;
    protected static JLabel label, err_l;
    protected static JTextField login;
    protected static JPasswordField password;
    protected static JButton log;

    public static void GUI() {
        frame = new Log_F();
        frame.setTitle("Sign in");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        panel.requestFocusInWindow();
        frame.setVisible(true);
    }

    private static void panel() {
        Font font = new Font("Palatino Linotype", Font.BOLD, 20);
        Font font2 = new Font("Times New Roman", Font.PLAIN, 15);
        panel = new JPanel();
        label = new JLabel("Fill in login and password");
        err_l = new JLabel(" ");
        err_l.setForeground(Color.RED);
        login = new JTextField("adminadmin");
        password = new JPasswordField("adminadmin");
        LoginListener listen = new LoginListener();
        log = new JButton("Sign in");
        log.addActionListener(listen);

        GroupLayout layout = new GroupLayout(panel);
        panel.setBackground(Settings_F.color);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(label)
                .addComponent(login)
                .addComponent(password)
                .addComponent(err_l)
                .addComponent(log));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addComponent(login)
                .addComponent(password)
                .addComponent(err_l)
                .addComponent(log));

        label.setFont(font);

        login.setFont(font2);
        new GhostText(login, "Login");

        password.setFont(font2);
        new GhostText(password, "Password");
    }
}

class LoginListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        if (!IsNullOrEmpty.isNullOrEmpty(Log_F.login, Log_F.password) && DataBase.checkWorker(Log_F.login.getText(), new String(Log_F.password.getPassword()))) {
            Worker worker = DataBase.getWorker(Log_F.login.getText(), new String(Log_F.password.getPassword()));
            Main_F.worker = worker;
            Main_F.GUI();
            Log_F.frame.dispose();
        } else {
            Log_F.err_l.setText("Incorrent login or password");
        }
    }
}