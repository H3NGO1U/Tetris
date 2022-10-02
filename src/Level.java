import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Level extends JPanel {
    final int BOARD_WIDTH = 240;
    final int BOARD_HEIGHT = 75;
    final int BOARD_Y = 95;
    private int curLevel = 1;
    File file = new File("nextLevel.wav");
    AudioInputStream audio = AudioSystem.getAudioInputStream(file);
    Clip clip = AudioSystem.getClip();
    Level() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.setLayout(null);
        this.setOpaque(true);
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        clip.open(audio);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.setFont(new Font("Monospaced", Font.BOLD, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("level: " + curLevel, (BOARD_WIDTH - metrics.stringWidth("score: " + curLevel)) / 2,
                ((BOARD_HEIGHT+20) / 2 ));

    }

    public void addLevel() {
        clip.setMicrosecondPosition(0);
        curLevel+=1;
        for(int i = 0; i<2; i++) {
            this.setBackground(Color.RED);
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setBackground(Color.WHITE);
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clip.start();
        }


    }

    public int getLevel() {
        return curLevel;
    }
}
