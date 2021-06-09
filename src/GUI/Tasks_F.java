package GUI;

import AdditionalP.GhostTextClass.GhostText;
import AdditionalP.IsNullOrEmpty;
import AdditionalP.TextAreaRenderer;
import Logic.DataBase;
import Logic.Task;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Tasks_F extends JFrame {

    protected static Tasks_F frame;
    private static JPanel panel;
    protected static JTable table;
    protected static JLabel label;
    protected static JButton add, delete, change;
    protected static JTextField id, task, deadline, points;
    protected static JComboBox log, completement;
    protected static JScrollPane scrollPane;
    private final static String[] columnNames = {"ID", "Task", "Staff login", "Staff full name", "Deadline", "Points", "Completement"};
    protected static String[][] rowData;

    public static void GUI() {
        frame = new Tasks_F();
        frame.setTitle("Tasks page");
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
        rowData = DataBase.getTasks();
        table = new JTable(rowData, columnNames);
        table.setBorder(border);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new TextAreaRenderer());
        table.setSelectionMode(0);
        scrollPane = new JScrollPane(table);
        table.setTableHeader(table.getTableHeader());
        table.setAutoCreateRowSorter(true);
        TasksListener listener = new TasksListener();
        add = new JButton("Add");
        add.addActionListener(listener);
        change = new JButton("Change");
        change.addActionListener(listener);
        delete = new JButton("Delete");
        delete.addActionListener(listener);

        id = new JTextField(5);
        new GhostText(id, "ID");
        task = new JTextField(10);
        new GhostText(task, "Task");
        deadline = new JTextField(10);
        new GhostText(deadline, "Deadline");
        points = new JTextField(5);
        new GhostText(points, "Points");
        completement = new JComboBox(new String[]{"0", "1"});
        log = new JComboBox(DataBase.getWorkersList());

        ListSelectionModel selModel = table.getSelectionModel();

        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                id.setText(table.getValueAt(selectedRow, 0).toString());
                id.setForeground(Color.black);
                task.setText(table.getValueAt(selectedRow, 1).toString());
                task.setForeground(Color.black);
                for (int i = 0; i < log.getItemCount(); i++) {
                    log.setSelectedIndex(i);
                    if (log.getSelectedItem().toString().equals(table.getValueAt(selectedRow, 2).toString())) {
                        break;
                    }
                }
                deadline.setText(table.getValueAt(selectedRow, 4).toString());
                deadline.setForeground(Color.black);
                points.setText(table.getValueAt(selectedRow, 5).toString());
                points.setForeground(Color.black);
                for (int i = 0; i < completement.getItemCount(); i++) {
                    completement.setSelectedIndex(i);
                    if (completement.getSelectedItem().toString().equals(table.getValueAt(selectedRow, 6).toString())) {
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
                        .addComponent(id)
                        .addComponent(task)
                        .addComponent(log)
                        .addComponent(deadline)
                        .addComponent(points)
                        .addComponent(completement)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(label)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(add)
                        .addComponent(change)
                        .addComponent(delete)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(scrollPane))
                .addGroup(layout.createParallelGroup()
                        .addComponent(id)
                        .addComponent(task)
                        .addComponent(log)
                        .addComponent(deadline)
                        .addComponent(points)
                        .addComponent(completement)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(label)
                )
                .addGroup(layout.createParallelGroup()
                        .addComponent(add)
                        .addComponent(change)
                        .addComponent(delete)
                )
        );
    }
}

class TasksListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add":
                if (IsNullOrEmpty.isNullOrEmpty(Tasks_F.task, Tasks_F.deadline, Tasks_F.points)) {
                    Tasks_F.label.setText("Fill in all fields");
                } else if (!Tasks_F.points.getText().matches("[-+]?\\d+") || !Tasks_F.deadline.getText().matches("\\d{4}.\\d\\d.\\d\\d")) {
                    Tasks_F.label.setText("Incorrect input data");
                } else if (DataBase.checkTaskDesc(Tasks_F.task.getText())) {
                    Tasks_F.label.setText("This task already exists");
                } else {
                    DataBase.addTask(new Task(Tasks_F.task.getText(), Tasks_F.log.getSelectedItem().toString(),
                            DataBase.getWorkerName(Tasks_F.log.getSelectedItem().toString()), Tasks_F.deadline.getText(),
                            Integer.parseInt(Tasks_F.points.getText()), Integer.parseInt(Tasks_F.completement.getSelectedItem().toString())));
                    Tasks_F.frame.dispose();
                    Tasks_F.GUI();
                }
                break;
            case "Change":
                if (IsNullOrEmpty.isNullOrEmpty(Tasks_F.task, Tasks_F.deadline, Tasks_F.points)) {
                    Tasks_F.label.setText("Fill in all fields");
                } else if (!Tasks_F.points.getText().matches("[-+]?\\d+") || !Tasks_F.deadline.getText().matches("\\d{4}.\\d\\d.\\d\\d")) {
                    Tasks_F.label.setText("Incorrect input points");
                } else if (!DataBase.checkTask(Tasks_F.id.getText())) {
                    Tasks_F.label.setText("This task not exists");
                } else if (DataBase.checkTaskDesc(Tasks_F.task.getText())) {
                    Tasks_F.label.setText("This task already exists");
                } else {
                    DataBase.changeTask(new Task(Integer.parseInt(Tasks_F.id.getText()), Tasks_F.task.getText(), Tasks_F.log.getSelectedItem().toString(),
                            DataBase.getWorkerName(Tasks_F.log.getSelectedItem().toString()), Tasks_F.deadline.getText(),
                            Integer.parseInt(Tasks_F.points.getText()), Integer.parseInt(Tasks_F.completement.getSelectedItem().toString())));
                    Tasks_F.frame.dispose();
                    Tasks_F.GUI();
                }
                break;
            case "Delete":
                if (IsNullOrEmpty.isNullOrEmpty(Tasks_F.id) || !DataBase.checkTask(Tasks_F.id.getText())) {
                    Tasks_F.label.setText("Incorrect id input");
                } else {
                    DataBase.deleteTask(Integer.parseInt(Tasks_F.id.getText()));
                    Tasks_F.frame.dispose();
                    Tasks_F.GUI();
                }
                break;
        }
    }
}
