package GUI;

import AdditionalP.GhostTextClass.GhostText;
import AdditionalP.IsNullOrEmpty;
import AdditionalP.TextAreaRenderer;
import Logic.DataBase;
import Logic.Worker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Staff_F extends JFrame {

    protected static Staff_F frame;
    private static JPanel panel;
    protected static JTable table;
    protected static JLabel label;
    protected static JButton add, delete, change, change_points, export;
    protected static JTextField log, pass, name, phone, points, com_tasks, exp_num;
    protected static JComboBox pos;
    protected static JScrollPane scrollPane;
    private final static String[] columnNames = {"Login", "Password", "Full name", "Mobile phone", "Position", "Points", "Completed tasks"};
    private static String[][] rowData;

    public static void GUI() {
        frame = new Staff_F();
        frame.setTitle("Staff page");
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        panel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(Main_F.frame);
        panel.requestFocusInWindow();
        frame.setVisible(true);
    }

    private static void panel() {
        panel = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        rowData = DataBase.getFullWorkersInfo();
        table = new JTable(rowData, columnNames);
        table.setBorder(border);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new TextAreaRenderer());
        table.setSelectionMode(0);
        scrollPane = new JScrollPane(table);
        table.setTableHeader(table.getTableHeader());
        table.setAutoCreateRowSorter(true);
        StaffListener listener = new StaffListener();
        add = new JButton("Add");
        add.addActionListener(listener);
        change = new JButton("Change");
        change.addActionListener(listener);
        delete = new JButton("Delete");
        delete.addActionListener(listener);
        change_points = new JButton("Change points");
        change_points.addActionListener(listener);
        export = new JButton("Export most useful");
        export.addActionListener(listener);

        log = new JTextField(10);
        new GhostText(log, "Login (10 ch.)");
        pass = new JTextField(10);
        new GhostText(pass, "Password (10 ch.)");
        name = new JTextField(20);
        new GhostText(name, "Full name");
        pos = new JComboBox(DataBase.getPositionsList());
        phone = new JTextField(10);
        phone.setColumns(10);
        new GhostText(phone, "Phone");
        points = new JTextField(4);
        new GhostText(points, "Points");
        com_tasks = new JTextField();
        new GhostText(com_tasks, "Number of completed tasks");
        exp_num = new JTextField();
        new GhostText(exp_num, "Number of employee to export");

        ListSelectionModel selModel = table.getSelectionModel();

        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                log.setText(table.getValueAt(selectedRow, 0).toString());
                log.setForeground(Color.black);
                pass.setText(table.getValueAt(selectedRow, 1).toString());
                pass.setForeground(Color.black);
                name.setText(table.getValueAt(selectedRow, 2).toString());
                name.setForeground(Color.black);
                phone.setText(table.getValueAt(selectedRow, 3).toString());
                phone.setForeground(Color.black);
                points.setText(table.getValueAt(selectedRow, 5).toString());
                points.setForeground(Color.black);
                com_tasks.setText(table.getValueAt(selectedRow, 6).toString());
                com_tasks.setForeground(Color.black);
                for (int i = 0; i < pos.getItemCount(); i++) {
                    pos.setSelectedIndex(i);
                    if (pos.getSelectedItem().toString().equals(table.getValueAt(selectedRow, 4).toString())) {
                        break;
                    }
                }
            }
        });

        label = new JLabel(" ");
        label.setForeground(Color.red);

        GroupLayout layout = new GroupLayout(panel);
        panel.setBackground(Settings_F.color);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPane))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(log)
                        .addComponent(pass)
                        .addComponent(name)
                        .addComponent(pos)
                        .addComponent(phone)
                        .addComponent(points)
                        .addComponent(com_tasks)
                        .addComponent(exp_num)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(label)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(add)
                        .addComponent(change)
                        .addComponent(delete)
                        .addComponent(change_points)
                        .addComponent(export)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(scrollPane))
                .addGroup(layout.createParallelGroup()
                        .addComponent(log)
                        .addComponent(pass)
                        .addComponent(name)
                        .addComponent(pos)
                        .addComponent(phone)
                        .addComponent(points)
                        .addComponent(com_tasks)
                        .addComponent(exp_num)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(label)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(add)
                        .addComponent(change)
                        .addComponent(delete)
                        .addComponent(change_points)
                        .addComponent(export)
                )
        );
    }

    protected static String[][] getData() {
        return rowData;
    }
}

class StaffListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add":
                if (IsNullOrEmpty.isNullOrEmpty(Staff_F.log, Staff_F.pass, Staff_F.name, Staff_F.phone, Staff_F.points)) {
                    Staff_F.label.setText("Fill in all fields");
                } else if (Staff_F.log.getText().length() != 10 || Staff_F.pass.getText().length() != 10
                        || !Staff_F.phone.getText().matches("[-+]?\\d+") || !Staff_F.points.getText().matches("[-+]?\\d+") || !Staff_F.com_tasks.getText().matches("[-+]?\\d+")) {
                    Staff_F.label.setText("Incorrect input data");
                } else if (DataBase.checkWorker(Staff_F.log.getText())) {
                    Staff_F.label.setText("This worker already exists");
                } else {
                    DataBase.addWorker(new Worker(Staff_F.log.getText(), Staff_F.pass.getText(), Staff_F.name.getText(), Staff_F.phone.getText(),
                            Staff_F.pos.getSelectedItem().toString(), Integer.parseInt(Staff_F.points.getText()), Integer.parseInt(Staff_F.com_tasks.getText())));
                    Staff_F.frame.dispose();
                    Staff_F.GUI();
                }
                break;
            case "Change":
                if (IsNullOrEmpty.isNullOrEmpty(Staff_F.log, Staff_F.pass, Staff_F.name, Staff_F.phone, Staff_F.points)) {
                    Staff_F.label.setText("Fill in all fields");
                } else if (Staff_F.log.getText().length() != 10 || Staff_F.pass.getText().length() != 10
                        || !Staff_F.phone.getText().matches("[-+]?\\d+") || !Staff_F.points.getText().matches("[-+]?\\d+") || !Staff_F.com_tasks.getText().matches("[-+]?\\d+")) {
                    Staff_F.label.setText("Incorrect input data");
                } else if (!DataBase.checkWorker(Staff_F.log.getText())) {
                    Staff_F.label.setText("This worker not exists");
                } else {
                    DataBase.changeWorker(new Worker(Staff_F.log.getText(), Staff_F.pass.getText(), Staff_F.name.getText(), Staff_F.phone.getText(),
                            Staff_F.pos.getSelectedItem().toString(), Integer.parseInt(Staff_F.points.getText()), Integer.parseInt(Staff_F.com_tasks.getText())));
                    Staff_F.frame.dispose();
                    Staff_F.GUI();
                }
                break;
            case "Delete":
                if (IsNullOrEmpty.isNullOrEmpty(Staff_F.log) || !DataBase.checkWorker(Staff_F.log.getText())) {
                    Staff_F.label.setText("Incorrect login input");
                } else if (!DataBase.checkDeleteWorker(Staff_F.log.getText())) {
                    DataBase.deleteWorker(Staff_F.log.getText());
                    Staff_F.frame.dispose();
                    Staff_F.GUI();
                } else {
                    Staff_F.label.setText("Worker is used");
                }
                break;
            case "Change points":
                if (!IsNullOrEmpty.isNullOrEmpty(Staff_F.points) && Staff_F.points.getText().matches("[-+]?\\d+")) {
                    DataBase.changePoints(Integer.parseInt(Staff_F.points.getText()));
                    Staff_F.frame.dispose();
                    Staff_F.GUI();
                } else {
                    Staff_F.label.setText("Incorrect points number");
                }
                break;
            case "Export most useful":
                String[][] data = Staff_F.getData();
                if (!IsNullOrEmpty.isNullOrEmpty(Staff_F.exp_num) && Staff_F.exp_num.getText().matches("[+]?\\d+") && Integer.parseInt(Staff_F.exp_num.getText()) <= data.length) {
                    ArrayList<String[]> useful_data = new ArrayList();
                    for (int i = 0; i < Integer.parseInt(Staff_F.exp_num.getText()); i++) {
                        String[] temp = new String[4];
                        temp[0] = data[i][2];
                        temp[1] = data[i][4];
                        temp[2] = data[i][5];
                        temp[3] = data[i][6];
                        useful_data.add(temp);
                    }
                    AdditionalP.ExcelWorker.Export("The best staff", "Top_staff_" + LocalDate.now().toString() + ".xls",
                            new String[]{"Full name", "Position", "Points", "Completed tasks"}, useful_data);
                } else {
                    Staff_F.label.setText("Incorrect staff number");
                }
                break;
            default:
                break;
        }
    }
}
