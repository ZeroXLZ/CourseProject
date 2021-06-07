package GUI;

import AdditionalP.TextAreaRenderer;
import Logic.DataBase;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

public class Points_List_F extends JFrame {

    protected static Points_List_F frame;
    private static JPanel panel;
    protected static JTable table;
    protected static JButton update;
    protected static JScrollPane scrollPane;
    private static String[] columnNames = {"№", "Full name", "Position", "Points"};
    private static String[][] rowData;

    public static void GUI() {
        frame = new Points_List_F();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setTitle("List of staff points");
        panel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        panel.requestFocusInWindow();
        frame.setVisible(true);
    }

    private static void panel() {
        panel = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        rowData = DataBase.getWorkers();
        table = new JTable(rowData, columnNames);
        table.setBorder(border);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setDefaultRenderer(Object.class, new TextAreaRenderer());
        scrollPane = new JScrollPane(table);
        table.setTableHeader(table.getTableHeader());
        table.setAutoCreateRowSorter(true);
        update = new JButton("Update");
        UpdateListener listener = new UpdateListener();
        update.addActionListener(listener);

        GroupLayout layout = new GroupLayout(panel);
        panel.setBackground(Settings_F.color);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(scrollPane)
                .addComponent(update)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addComponent(update)
        );
    }
}

class UpdateListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        Points_List_F.frame.dispose();
        Points_List_F.GUI();
    }
}
