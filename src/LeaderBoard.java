import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LeaderBoard extends JFrame {
    JTable table;
    DefaultTableModel model;
    String Headers[] = {"Player", "Score"};
    LeaderBoard() {
        table = new JTable(new DefaultTableModel(Headers, 0));
        model = (DefaultTableModel) table.getModel();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setLayout(null);
        this.setBackground(Color.gray);
        this.setLayout(new BorderLayout());
        this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void addName(String PlayerName, int score) {
        model.addRow(new Object[]{PlayerName, score});
    }


}
