import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class StartGame extends JFrame implements ActionListener {

    JLabel tetrisL;
    JButton strBtn;
    public StartGame(){
        tetrisL = new JLabel("Tetris");
        tetrisL.setFont(new Font("Monospaced", Font.BOLD, 100));
        tetrisL.setForeground(Color.MAGENTA);
        tetrisL.setBounds(100,20, 600,200);
        
        strBtn = new JButton("Start");
        strBtn.addActionListener(this);
        strBtn.setFocusable(false);
        strBtn.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        strBtn.setBackground(Color.WHITE);
        strBtn.setForeground(Color.gray);
        strBtn.setFont(new Font("Monospaced", Font.BOLD, 35));
        strBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                strBtn.setBackground(Color.LIGHT_GRAY);
            }
            public void mouseExited(MouseEvent evt) {
                strBtn.setBackground(Color.WHITE);
            }
        });
        this.add(strBtn);
        strBtn.setBounds(125, 325, 300, 100);
        
        
        this.add(tetrisL);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setLayout(null);
        this.setBackground(Color.gray);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==strBtn){
            this.dispose();
            SwingUtilities.invokeLater(Main::Start);
        }
    }
}
