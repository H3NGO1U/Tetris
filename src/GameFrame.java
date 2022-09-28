import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    final int SIDE_ELM_WIDTH = 240;
    final int SIDE_ELM_HEIGHT = 75;
    final int SIDE_ELM_X_POS = 325;
    final int pausePosY = 170;
    GameZone gameZone;
    JButton pause;
    GameFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.gray);
        gameZone = new GameZone();
        this.add(gameZone);
        gameZone.setBounds(20,20, gameZone.BOARD_WIDTH, gameZone.BOARD_HEIGHT);
        this.add(gameZone.score);
        gameZone.score.setBounds(SIDE_ELM_X_POS,gameZone.score.BOARD_Y, SIDE_ELM_WIDTH, SIDE_ELM_HEIGHT);
        this.add(gameZone.level);
        gameZone.level.setBounds(SIDE_ELM_X_POS,gameZone.level.BOARD_Y, SIDE_ELM_WIDTH, SIDE_ELM_HEIGHT);
        this.add(gameZone.nextShape);
        gameZone.nextShape.setBounds(SIDE_ELM_X_POS, gameZone.nextShape.BOARD_Y, SIDE_ELM_WIDTH, gameZone.nextShape.SIZE);
        //pause button
        pause = new JButton();
        pause.addActionListener(this);
        pause.setFocusable(false);
        pause.setText("Pause");
        pause.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        pause.setBackground(Color.WHITE);
        pause.setForeground(Color.gray);
        pause.setFont(new Font("Monospaced", Font.BOLD, 35));
        this.add(pause);
        pause.setBounds(SIDE_ELM_X_POS, pausePosY, SIDE_ELM_WIDTH, SIDE_ELM_HEIGHT);

        //start game
        new GameThread(gameZone).start();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==pause) {
                if(gameZone.endgame) //if game ended
                    return;
                gameZone.running = !gameZone.running;
                if(gameZone.running == true)
                    pause.setText("Pause");
                else pause.setText("Resume");
            }
    }
}
