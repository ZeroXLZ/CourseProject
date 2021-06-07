package GUI;

import AdditionalP.TextAreaRenderer;
import Logic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class Main_F extends JFrame {

    private static JPanel panel;
    protected static Main_F frame;
    protected static JMenuBar menuBar;
    private static JLabel fullName;
    protected static JButton complete, settings, staff_list, update;
    protected static JTable table;
    private static JScrollPane scrollPane;
    private static String[] columnNames = {"№", "Task", "Deadline", "Points"};
    protected static String[][] rowsData;
    protected static Worker worker;
    private static mainListener listener = new mainListener();

    public static void GUI() {
        frame = new Main_F();
        frame.setTitle("Main menu");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        panel();
        frame.setJMenuBar(menuBar);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        panel.requestFocusInWindow();
        frame.setVisible(true);
    }

    private static void panel() {
        panel = new JPanel();
        fullName = new JLabel(worker.getName());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        worker.setTasks(DataBase.getWTasks(worker));
        rowsData = worker.getTasks();
        table = new JTable(rowsData, columnNames);
        table.setBorder(border);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new TextAreaRenderer());
        scrollPane = new JScrollPane(table);
        table.setTableHeader(table.getTableHeader());
        table.setAutoCreateRowSorter(true);
        complete = new JButton("Complete Task");
        complete.addActionListener(listener);
        settings = new JButton("Settings");
        settings.addActionListener(listener);
        staff_list = new JButton("Points table");
        staff_list.addActionListener(listener);
        update = new JButton("Update table");
        update.addActionListener(listener);

        GroupLayout layout = new GroupLayout(panel);
        panel.setBackground(Settings_F.color);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(fullName)
                .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(complete)
                        .addComponent(update)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(fullName)
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup()
                        .addComponent(complete)
                        .addComponent(update)
                )
        );
        menuBar.add(staff_list);
        menuBar.add(settings);
        if (worker.getPosition().equals("Administrator")) {
            menuBar.add(adminMenu());
        }
    }

    private static JMenu adminMenu() {
        JMenu admin = new JMenu("Admin menu");
        admin.setOpaque(true);
        admin.setBackground(Color.LIGHT_GRAY);
        JMenuItem staff = new JMenuItem("Staff");
        staff.addActionListener(listener);
        JMenuItem positions = new JMenuItem("Positions");
        positions.addActionListener(listener);
        JMenuItem tasks = new JMenuItem("Tasks");
        tasks.addActionListener(listener);
        admin.add(staff);
        admin.addSeparator();
        admin.add(positions);
        admin.addSeparator();
        admin.add(tasks);
        return admin;
    }
}

class mainListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Complete Task":
                Complete_F.GUI();
                break;
            case "Settings":
                Settings_F.GUI();
                Main_F.frame.dispose();
                break;
            case "Points table":
                Points_List_F.GUI();
                break;
            case "Staff":
                Staff_F.GUI();
                break;
            case "Positions":
                Positions_F.GUI();
                break;
            case "Tasks":
                Tasks_F.GUI();
                break;
            case "Update table":
                Main_F.frame.dispose();
                Main_F.GUI();
                break;
        }
    }
}
