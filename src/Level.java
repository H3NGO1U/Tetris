import javax.swing.*;
import java.awt.*;

public class Level extends JPanel {
    final int BOARD_WIDTH = 240;
    final int BOARD_HEIGHT = 75;
    final int BOARD_Y = 95;
    int curLevel = 1;
    Level(){
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
        g.drawString("level: " + curLevel, (BOARD_WIDTH - metrics.stringWidth("score: " + curLevel)) / 2,
                ((BOARD_HEIGHT+20) / 2 ));

    }

    public void addLevel(){
        curLevel+=1;
        repaint();
    }
}
