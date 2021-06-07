package GUI;

import AdditionalP.GhostTextClass.GhostText;
import AdditionalP.IsNullOrEmpty;
import AdditionalP.TextAreaRenderer;
import Logic.DataBase;
import Logic.Position;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Positions_F extends JFrame {

    protected static Positions_F frame;
    private static JPanel panel;
    protected static JTable table;
    protected static JLabel label;
    protected static JButton add, delete, change;
    protected static JTextField name, payment, salary;
    protected static JScrollPane scrollPane;
    private final static String[] columnNames = {"Name", "Payment type", "Salary"};
    protected static String[][] rowData;

    public static void GUI() {
        frame = new Positions_F();
        frame.setTitle("Positions page");
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
        rowData = DataBase.getPositions();
        table = new JTable(rowData, columnNames);
        table.setBorder(border);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new TextAreaRenderer());
        table.setSelectionMode(0);
        scrollPane = new JScrollPane(table);
        table.setTableHeader(table.getTableHeader());
        table.setAutoCreateRowSorter(true);
        PositionsListener listener = new PositionsListener();
        add = new JButton("Add");
        add.addActionListener(listener);
        change = new JButton("Change");
        change.addActionListener(listener);
        delete = new JButton("Delete");
        delete.addActionListener(listener);

        name = new JTextField(10);
        new GhostText(name, "Name");
        payment = new JTextField(10);
        new GhostText(payment, "Type of payment");
        salary = new JTextField(10);
        new GhostText(salary, "Salary");

        ListSelectionModel selModel = table.getSelectionModel();

        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                name.setText(table.getValueAt(selectedRow, 0).toString());
                name.setForeground(Color.black);
                payment.setText(table.getValueAt(selectedRow, 1).toString());
                payment.setForeground(Color.black);
                salary.setText(table.getValueAt(selectedRow, 2).toString());
                salary.setForeground(Color.black);
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
                        .addComponent(name)
                        .addComponent(payment)
                        .addComponent(salary)
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
                        .addComponent(name)
                        .addComponent(payment)
                        .addComponent(salary)
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

class PositionsListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add":
                if (IsNullOrEmpty.isNullOrEmpty(Positions_F.name, Positions_F.payment, Positions_F.salary)) {
                    Positions_F.label.setText("Fill in all fields");
                } else if (!Positions_F.salary.getText().matches("[-+]?\\d+")) {
                    Positions_F.label.setText("Incorrect input salary");
                } else if (DataBase.checkPosition(Positions_F.name.getText())) {
                    Positions_F.label.setText("This position already exists");
                } else {
                    DataBase.addPosition(new Position(Positions_F.name.getText(), Positions_F.payment.getText(), Positions_F.salary.getText()));
                    Positions_F.frame.dispose();
                    Positions_F.GUI();
                }
                break;
            case "Change":
                if (IsNullOrEmpty.isNullOrEmpty(Positions_F.name, Positions_F.payment, Positions_F.salary)) {
                    Positions_F.label.setText("Fill in all fields");
                } else if (!Positions_F.salary.getText().matches("[-+]?\\d+")) {
                    Positions_F.label.setText("Incorrect input salary");
                } else if (!DataBase.checkPosition(Positions_F.name.getText())) {
                    Positions_F.label.setText("This position not exists");
                } else {
                    DataBase.changePosition(new Position(Positions_F.name.getText(), Positions_F.payment.getText(), Positions_F.salary.getText()));
                    Positions_F.frame.dispose();
                    Positions_F.GUI();
                }
                break;
            case "Delete":
                if (IsNullOrEmpty.isNullOrEmpty(Positions_F.name) || !DataBase.checkPosition(Positions_F.name.getText())) {
                    Positions_F.label.setText("Incorrect position input");
                } else if (!DataBase.checkDeletePosition(Positions_F.name.getText())) {
                    DataBase.deletePosition(Positions_F.name.getText());
                    Positions_F.frame.dispose();
                    Positions_F.GUI();
                } else {
                    Positions_F.label.setText("Position is used");
                }
                break;
        }
    }
}
