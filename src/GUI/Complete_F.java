package GUI;

import AdditionalP.GhostTextClass.GhostText;
import Logic.DataBase;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;

public class Complete_F extends JFrame {

    private static JPanel panel;
    protected static Complete_F frame;
    protected static JLabel label, err_l;
    protected static JTextField number;
    private static JButton complete;

    public static void GUI() {
        frame = new Complete_F();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(Main_F.frame);
        panel.requestFocusInWindow();
        frame.setVisible(true);
    }

    private static void panel() {
        Font font = new Font("Palatino Linotype", Font.BOLD, 20);
        Font font2 = new Font("Times New Roman", Font.PLAIN, 15);

        panel = new JPanel();

        label = new JLabel("Input task number");
        err_l = new JLabel(" ");
        err_l.setForeground(Color.red);
        label.setFont(font);

        number = new JTextField();
        number.setFont(font2);
        new GhostText(number, "Number");

        completeListener comListen = new completeListener();
        complete = new JButton("Complete");
        complete.addActionListener(comListen);

        GroupLayout layout = new GroupLayout(panel);
        panel.setBackground(Settings_F.color);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(label)
                .addComponent(number)
                .addComponent(err_l)
                .addComponent(complete)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addComponent(number)
                .addComponent(err_l)
                .addComponent(complete)
        );
    }
}

class completeListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        boolean comp = false;
        for (int i = 0; i < Main_F.table.getRowCount(); i++) {
            if (Main_F.table.getValueAt(i, 0).equals(Complete_F.number.getText())) {
                
                if (LocalDate.now().compareTo(LocalDate.parse(Main_F.table.getValueAt(i, 2).toString())) <= 0) {
                    Main_F.worker.setPoints(Main_F.worker.getPoints() + Integer.parseInt(Main_F.table.getValueAt(i, 3).toString()));
                } else {
                    Main_F.worker.setPoints(Main_F.worker.getPoints() - Integer.parseInt(Main_F.table.getValueAt(i, 3).toString()));
                }
                DataBase.completeTask(Main_F.table.getValueAt(i, 1).toString());          
                DataBase.changeWorker(Main_F.worker);
                comp = true;
                Main_F.frame.dispose();
                Main_F.GUI();
                Complete_F.frame.dispose();
                break;
            }
        }
        if (comp == false) {
            Complete_F.err_l.setText("There is no this task");
        }
    }
}