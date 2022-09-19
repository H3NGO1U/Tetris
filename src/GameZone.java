import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameZone extends JPanel {
    final int BOARD_WIDTH = 300;
    final int BOARD_HEIGHT = 540;
    final int UNIT_SIZE = 20;
    final int GAME_UNITS_X = BOARD_WIDTH / UNIT_SIZE;
    final int GAME_UNITS_Y = BOARD_HEIGHT / UNIT_SIZE;
    final int MIDDLE = 120;

    Random random;


    int curY = 0;
    int curX = MIDDLE;
    int curShape;
    int mode = 1;
    boolean moveLeft = true;
    boolean moveRight = true;
    Block shape;
    int[] curLast;
    Color[][] colors;

    GameZone() {
        random = new Random();
        this.setLayout(null);
        this.setOpaque(true);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());


        chooseShape();
        createShape(curShape);
        colors = new Color[GAME_UNITS_Y][GAME_UNITS_X];
        curLast = new int[GAME_UNITS_X];

        for (int i = 0; i < GAME_UNITS_X; i++) {
            curLast[i] = BOARD_HEIGHT;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        for (int k = 0; k < BOARD_HEIGHT; k += UNIT_SIZE) {
            for (int m = 0; m < BOARD_WIDTH; m += UNIT_SIZE) {
                int yCor = k / UNIT_SIZE, xCor = m / UNIT_SIZE;
                if (colors[yCor][xCor] != null) {
                    g.setColor(colors[yCor][xCor]);
                    g.fillRect(m, k, UNIT_SIZE, UNIT_SIZE);
                    g2D.setStroke(new BasicStroke(2));
                    g.setColor(Color.black);
                    g.drawRect(m, k,
                            UNIT_SIZE, UNIT_SIZE);
                }
            }
        }


        for (int i = 0; i < shape.raws(); i++)
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


    public void createShape(int curShape) {
        shape = new Block(curShape);
        repaint();
    }

    public void chooseShape() {
        curShape = random.nextInt(7);
    }

    public boolean endGame() {
        for (int i = 0; i < GAME_UNITS_X; i++)
            if (colors[0][i] != null) return false;
        return true;
    }

    public boolean checkLast() {

        for (int i = 0; i < shape.raws(); i++)
            for (int j = 0; j < shape.length(); j++)
                if (shape.checkNull(i, j))
                    if (curLast[shape.getX(i, j) / UNIT_SIZE] - UNIT_SIZE == shape.getY(i, j)) {
                        build();
                        checkRow();
                        return false;
                    }
        return true;
    }

    public void build() {
        for (int i = 0; i < shape.raws(); i++)
            for (int j = 0; j < shape.length(); j++)
                if (shape.checkNull(i, j)) {
                    colors[shape.getY(i, j) / UNIT_SIZE][shape.getX(i, j) / UNIT_SIZE] = shape.getColor();
                    if (shape.getY(i, j) < curLast[shape.getX(i, j) / UNIT_SIZE])
                        curLast[shape.getX(i, j) / UNIT_SIZE] = shape.getY(i, j);
                }
        chooseShape();
        createShape(curShape);


    }

    public void checkRow() {
        int check = 0;
        for (int row = 0; row < BOARD_HEIGHT; row += UNIT_SIZE) {
            for (int i = 0; i < BOARD_WIDTH; i += UNIT_SIZE) {
                if (colors[row / UNIT_SIZE][i / UNIT_SIZE] != null) check++;
            }
            if (check == GAME_UNITS_X) cleanRow(row / UNIT_SIZE);
            check = 0;
        }
    }

    public void cleanRow(int row) {
        for (int i = 0; i < GAME_UNITS_X; i++)
            colors[row][i] = null;
        int counter = 0;
        for (int j = row; j > 0 && counter < GAME_UNITS_X; j--) {
            counter = 0;
            for (int k = 0; k < GAME_UNITS_X; k++) {
                if (colors[j - 1][k] == null) counter++;
                colors[j][k] = colors[j - 1][k];
            }
        }
        for (int i = 0; i < GAME_UNITS_X; i++)
            curLast[i] += UNIT_SIZE;
    }

    public void changeMode() {
        boolean pivotIsMoved = false;
        if (shape.checkNull(0, 0))
            updatePoint(0, 0);
        else
            updatePoint(0, 1);
        if(curShape == 1 || curShape == 6)
            pivotIsMoved = true;
                switch(mode){
                    case 1:
                        if(pivotIsMoved) curX -= UNIT_SIZE;

                        curY-=(shape.raws()-1)*UNIT_SIZE;
                        break;
                    case 2: if(pivotIsMoved) curY -= UNIT_SIZE;
                        break;
                    case 3: if(pivotIsMoved) curX += UNIT_SIZE;

                        curY+= (shape.length()-1)*UNIT_SIZE;
                        break;
                    default: if(pivotIsMoved) curY += UNIT_SIZE;

                        curY-=(shape.raws()-1)*UNIT_SIZE;
                }


                switch (mode) {
                    case 1:
                        for (int i = 0; i < shape.raws(); i++)
                            for (int j = 0; j < shape.length(); j++) {
                                if (shape.checkNull(i, j)) {
                                    shape.setLocation(i, j, curX + j * UNIT_SIZE, curY + i * UNIT_SIZE);
                                }
                            }
                        break;
                    case 2:
                        for (int i = 0; i < shape.raws(); i++)
                            for (int j = 0; j < shape.length(); j++) {
                                if (shape.checkNull(i, j)) {
                                    shape.setLocation(i, j, curX - i * UNIT_SIZE, curY + j * UNIT_SIZE);
                                }
                            }
                        break;
                    case 3:

                        for (int i = 0; i < shape.raws(); i++)
                            for (int j = 0; j < shape.length(); j++) {
                                if (shape.checkNull(i, j)) {
                                    shape.setLocation(i, j, curX - j * UNIT_SIZE, curY - i * UNIT_SIZE);
                                }
                            }
                        break;
                    case 4:

                        for (int i = 0; i < shape.raws(); i++)
                            for (int j = 0; j < shape.length(); j++) {
                                if (shape.checkNull(i, j)) {
                                    shape.setLocation(i, j, curX + i * UNIT_SIZE, curY - j * UNIT_SIZE);
                                }
                            }
                        break;
                }
        }


                public void move () {
                for (int i = 0; i < shape.raws(); i++)
                    for (int j = 0; j < shape.length(); j++)
                        if (shape.checkNull(i, j))
                            shape.setLocation(i, j, shape.getX(i, j),
                                    shape.getY(i, j) + UNIT_SIZE);


                repaint();


            }

            public class MyKeyAdapter extends KeyAdapter {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            moveLeft = true;
                            for (int i = 0; i < shape.raws() && moveLeft; i++)
                                for (int j = 0; j < shape.length() && moveLeft; j++) {
                                    if (shape.checkNull(i, j)) {
                                        if (shape.getX(i, j) == 0 || colors[shape.getX(i, j) / UNIT_SIZE][shape.getY(i, j) / UNIT_SIZE] != null
                                                && !shape.checkNull(i, j - 1)) {
                                            moveLeft = false;
                                        }

                                    }

                                }
                            if (moveLeft)
                                for (int i = 0; i < shape.raws(); i++)
                                    for (int j = 0; j < shape.length(); j++) {
                                        if (shape.checkNull(i, j))
                                            shape.setLocation(i, j, shape.getX(i, j) - UNIT_SIZE,
                                                    shape.getY(i, j));
                                    }
                            break;

                        case KeyEvent.VK_RIGHT:
                            moveRight = true;
                            for (int i = 0; i < shape.raws() && moveRight; i++)
                                for (int j = 0; j < shape.length() && moveRight; j++) {
                                    if (shape.checkNull(i, j)) {
                                        if (shape.getX(i, j) + UNIT_SIZE == BOARD_WIDTH
                                                || colors[shape.getX(i, j) / UNIT_SIZE][shape.getY(i, j) / UNIT_SIZE] != null
                                                && !shape.checkNull(i, j + 1))
                                            moveRight = false;
                                    }
                                }
                            if (moveRight)
                                for (int i = 0; i < shape.raws(); i++)
                                    for (int j = 0; j < shape.length(); j++)
                                        if (shape.checkNull(i, j))
                                            shape.setLocation(i, j, shape.getX(i, j) + UNIT_SIZE,
                                                    shape.getY(i, j));
                            break;
                        case KeyEvent.VK_UP:
                            switch (mode) {
                                case 1:
                                    mode = 2;
                                    break;
                                case 2:
                                    mode = 3;
                                    break;
                                case 3:
                                    mode = 4;
                                    break;
                                case 4:
                                    mode = 1;
                                    break;
                            }
                            changeMode();
                            break;
                        case KeyEvent.VK_DOWN:
                            for (int i = 0; i < shape.raws(); i++)
                                for (int j = 0; j < shape.length(); j++)
                                    if (shape.checkNull(i, j))
                                        shape.setLocation(i, j, shape.getX(i, j),
                                                shape.getY(i, j) + UNIT_SIZE);
                    }
                }
            }
        

        private void updatePoint (int i, int j) {
            curY = shape.getY(i,j);
            curX = shape.getX(i,j);
        }

}
