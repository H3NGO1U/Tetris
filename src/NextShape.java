
import javax.swing.*;
import java.awt.*;

import java.util.Random;
public class NextShape extends JPanel {
    final int BOARD_Y = 320;
    final int SIZE = 240;
    final int UNIT_SIZE = 30;
    Block shape;
    int curShape;
    int nextShapeN;
    int middleX;
    int middleY;
    Random random;


    NextShape(){
        this.setLayout(null);
        this.setOpaque(true);
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        random = new Random();
        nextShapeN = random.nextInt(7);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        /*draw the next shape*/
        Graphics2D g2D = (Graphics2D) g;
        for (int i = 0; i < shape.rows(); i++)
            for (int j = 0; j < shape.length(); j++)
                if (shape.checkNull(i, j)) {
                    g.setColor(shape.getColor());
                    g.fillRect((shape.getX(i, j)), shape.getY(i, j),
                            UNIT_SIZE, UNIT_SIZE);
                    g2D.setStroke(new BasicStroke(2));
                    g.setColor(Color.black);
                    g.drawRect((shape.getX(i, j)), shape.getY(i, j),
                            UNIT_SIZE, UNIT_SIZE);
                }
    }
    /*set current shape to equal current, and select next shape*/
    public void chooseShape() {
        curShape = nextShapeN;
        nextShapeN = random.nextInt(7);
    }
    public void createShape(){
        shape = new Block(nextShapeN);
        middleX = (SIZE-shape.length()*UNIT_SIZE) / 2;
        middleY = (SIZE-shape.rows()*UNIT_SIZE) / 2;
        shape.posShape(middleX, middleY);
        repaint();
    }
}

