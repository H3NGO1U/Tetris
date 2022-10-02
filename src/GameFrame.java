import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame implements ActionListener {
    final int SIDE_ELM_WIDTH = 240;
    final int SIDE_ELM_HEIGHT = 75;
    final int SIDE_ELM_X_POS = 325;
    final int SIZE_FRAME = 600;
    final int pausePosY = 170;
    final int mainMenuPosY = 245;
    GameZone gameZone;
  
    JLabel numberL;
    File startFile = new File("threeTwoOne.wav");
    AudioInputStream audio = AudioSystem.getAudioInputStream(startFile);
    Clip clip = AudioSystem.getClip();
    JButton buttons[];
    JButton pause;
    JButton MainMenu;
    GameFrame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(SIZE_FRAME, SIZE_FRAME);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.gray);

        //game zone
        gameZone = new GameZone();
        this.add(gameZone, BorderLayout.CENTER);
        gameZone.setBounds(20,20, gameZone.BOARD_WIDTH, gameZone.BOARD_HEIGHT);
        this.add(gameZone.score);
        gameZone.score.setBounds(SIDE_ELM_X_POS,gameZone.score.BOARD_Y, SIDE_ELM_WIDTH, SIDE_ELM_HEIGHT);
        this.add(gameZone.level);
        gameZone.level.setBounds(SIDE_ELM_X_POS,gameZone.level.BOARD_Y, SIDE_ELM_WIDTH, SIDE_ELM_HEIGHT);
        this.add(gameZone.nextShape);
        gameZone.nextShape.setBounds(SIDE_ELM_X_POS, gameZone.nextShape.BOARD_Y, SIDE_ELM_WIDTH, gameZone.nextShape.SIZE);
        //buttons
        buttons = new JButton[2];
        pause = new JButton("Pause");
        MainMenu = new JButton("Main Menu");
        buttons[0] = pause;
        buttons[1] = MainMenu;
        for(int i= 0; i<buttons.length; i++){
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
       
        pause.setBounds(SIDE_ELM_X_POS, pausePosY, SIDE_ELM_WIDTH, SIDE_ELM_HEIGHT);
        MainMenu.setBounds(SIDE_ELM_X_POS, mainMenuPosY, SIDE_ELM_WIDTH, SIDE_ELM_HEIGHT);
      //3.. 2.. 1..
        numberL = new JLabel();
        numberL.setFont(new Font("Monospaced", Font.BOLD, 70));
        numberL.setForeground(Color.GRAY);
        numberL.setVerticalTextPosition(JLabel.BOTTOM);
        numberL.setHorizontalTextPosition(JLabel.CENTER);
        numberL.setVerticalAlignment(JLabel.CENTER);
        numberL.setHorizontalAlignment(JLabel.CENTER);
        gameZone.add(numberL);
        numberL.setVisible(true);
        clip.open(audio);
    }
    public void start(){
        this.setVisible(true);
        SwingUtilities.invokeLater(()->{
            try {
                startAnimation();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    public void startAnimation() throws InterruptedException {
        gameZone.animation = true;
        clip.start();
        pause.setText("Pause");
        gameZone.endgame = false;
        Thread.sleep(3000);
        gameZone.animation = false;
        gameZone.running = true;
        new GameThread(gameZone).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource()==pause) {
                if(gameZone.endgame || gameZone.animation) //if game ended OR animation is on
                    return;
                gameZone.running = !gameZone.running;
                if(gameZone.running)

                    pause.setText("Pause");

                else pause.setText("Resume");
            }
            if(e.getSource()==MainMenu){
                gameZone.endgame = true;
                this.dispose();
                Main.MainPage();
            }
    }
}
