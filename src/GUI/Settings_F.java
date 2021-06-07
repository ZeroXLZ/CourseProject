package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Settings_F extends JFrame {

    private static JPanel panel;
    protected static Settings_F frame;
    protected static JButton theme, unlog;
    protected static Color color = Color.LIGHT_GRAY;

    public static void GUI() {
        frame = new Settings_F();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                onExit();
            }
        });
        panel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        panel.requestFocusInWindow();
        frame.setVisible(true);
    }

    private static void onExit() {
        Main_F.GUI();
    }

    private static void panel() {
        panel = new JPanel();
        settingsListener setListener = new settingsListener();
        theme = new JButton("Change theme color");
        theme.addActionListener(setListener);
        unlog = new JButton("Log out");
        unlog.addActionListener(setListener);

        GroupLayout layout = new GroupLayout(panel);
        panel.setBackground(Settings_F.color);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(theme)
                .addComponent(unlog)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(theme)
                .addComponent(unlog)
        );
    }
}

class settingsListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Change theme color":
                if (Settings_F.color == Color.GRAY) {
                    Settings_F.color = Color.LIGHT_GRAY;
                } else if (Settings_F.color == Color.LIGHT_GRAY) {
                    Settings_F.color = Color.GRAY;
                }
                Settings_F.frame.dispose();
                Settings_F.GUI();
                break;
            case "Log out":
                Settings_F.frame.dispose();
                Log_F.GUI();
                break;
        }
    }
}
