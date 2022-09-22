import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {
    final int BOARD_WIDTH = 200;
    final int BOARD_HEIGHT = 75;
    int curScore = 0;
    Score() {
        this.setLayout(null);
        this.setOpaque(true);
        this.setFocusable(true);
        this.setBorder(BorderFactory.createLineBorder(Color.pink, 3));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ink Free", Font.ITALIC, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("score: " + curScore, (BOARD_WIDTH - metrics.stringWidth("score: " + curScore)) / 2,
                (BOARD_HEIGHT / 2 ));

    }

    public void addScore(){
        curScore+=10;
        repaint();
    }
}
