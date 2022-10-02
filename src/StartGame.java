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
    JButton LdrBrd;
    JButton buttons[];
    public StartGame(){
        //header
        tetrisL = new JLabel("Tetris");
        tetrisL.setFont(new Font("Monospaced", Font.BOLD, 100));
        tetrisL.setForeground(Color.MAGENTA);
        tetrisL.setBounds(100,20, 600,200);
        this.add(tetrisL);

        //buttons
        strBtn = new JButton("Start");
        LdrBrd = new JButton("Leader Board");
        buttons = new JButton[2];
        buttons[0] = strBtn;
        buttons[1] = LdrBrd;
        for(int i = 0; i< buttons.length; i++){
            buttons[i].addActionListener(this);
            buttons[i].setFocusable(false);
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setForeground(Color.gray);
            buttons[i].setFont(new Font("Monospaced", Font.BOLD, 35));
            final int cur = i;
            buttons[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    buttons[cur].setBackground(Color.LIGHT_GRAY);
                }
                public void mouseExited(MouseEvent evt) {
                    buttons[cur].setBackground(Color.WHITE);
                }
            });
            this.add(buttons[i]);
        }

        strBtn.setBounds(125, 325, 300, 100);
        LdrBrd.setBounds(125, 220, 300, 100);

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
        if(e.getSource()==LdrBrd){
            SwingUtilities.invokeLater(Main::showLeaderBoard);
        }
    }
}
