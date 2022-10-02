import javax.naming.ldap.SortKey;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class LeaderBoard extends JFrame implements ActionListener {
    final int MAX_ROWS = 10;
    JTable table;
    DefaultTableModel model;
    private TableRowSorter<TableModel> sorter;
    String Headers[] = {"Player", "Score"};
    String fileName = "leader_board";
    JButton MainMenu;
    JPanel panel;
    Font tableFont;
    LeaderBoard() {
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        MainMenu = new JButton("Main Menu");
        MainMenu.addActionListener(this);
        MainMenu.setFocusable(false);
        MainMenu.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        MainMenu.setBackground(Color.WHITE);
        MainMenu.setForeground(Color.gray);
        MainMenu.setFont(new Font("Monospaced", Font.BOLD, 35));
        MainMenu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                MainMenu.setBackground(Color.LIGHT_GRAY);
            }
            public void mouseExited(MouseEvent evt) {
                MainMenu.setBackground(Color.WHITE);
            }
        });

        panel.add(MainMenu);
        MainMenu.setBounds(150, 0, 300, 50);
        table = new JTable(new DefaultTableModel(Headers, 0));
        tableFont = new Font("Ink Free", Font.BOLD, 25);
        table.setFont(tableFont);
        table.getTableHeader().setFont(tableFont);
        table.setRowHeight(30);
        model = (DefaultTableModel) table.getModel();
        initTableData();
        initSorter();
        this.setLayout(new BorderLayout());
        this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setBackground(Color.gray);
    }

    public void addName(String PlayerName, int score) {
        if(model.getRowCount()==MAX_ROWS){
            int lowest = (int)model.getValueAt(MAX_ROWS-1, 1);
            if(lowest<score)
                model.removeRow(MAX_ROWS-1);
            else return;
        }
        model.addRow(new Object[]{PlayerName, score});
        sorter.sort();
        saveLdrBrd();
    }
    private void initTableData(){
        Vector ci = new Vector();
        ci.add(Headers[0]);
        ci.add(Headers[1]);
        try {
            FileInputStream FI = new FileInputStream(fileName);
            ObjectInputStream OI = new ObjectInputStream(FI);
            model.setDataVector((Vector<? extends Vector>) OI.readObject(), ci);
        }
        catch(Exception e){}
    }
    private void saveLdrBrd(){
        try {
            FileOutputStream FS = new FileOutputStream(fileName);
            ObjectOutputStream OS = new ObjectOutputStream(FS);
            OS.writeObject(model.getDataVector());
            OS.close();
            FS.close();
        }
        catch(Exception e){}

    }
    private void initSorter(){
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==MainMenu){
            Main.MainPage();
        }
    }
}
