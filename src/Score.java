import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {
    final int BOARD_WIDTH = 240;
    final int BOARD_HEIGHT = 75;
    final int BOARD_Y = 20;
    private int curScore = 0;
    Score() {
        this.setLayout(null);
        this.setOpaque(true);
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.setFont(new Font("Monospaced", Font.BOLD, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("score: " + curScore, (BOARD_WIDTH - metrics.stringWidth("score: " + curScore)) / 2,
                ((BOARD_HEIGHT+BOARD_Y) / 2 ));

    }
    public int getScore(){
        return curScore;
    }
    public void addScore(){
        curScore+=10;
        repaint();
    }
}
