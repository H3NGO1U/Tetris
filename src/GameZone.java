import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
enum SHAPES{
    RED, YELLOW, PURPLE, BLUE, ORANGE, GREEN, CYAN
}
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
        colors = new Color[GAME_UNITS_X][GAME_UNITS_Y];
        curLast = new int[GAME_UNITS_X];

        for (int i = 0; i < GAME_UNITS_X; i++) {
            curLast[i] = BOARD_HEIGHT;
        }
System.out.println(colors.length+" "+colors[0].length);
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
                if (colors[xCor][yCor] != null) {
                    g.setColor(colors[xCor][yCor]);
                    g.fillRect(m, k, UNIT_SIZE, UNIT_SIZE);
                    g2D.setStroke(new BasicStroke(2));
                    g.setColor(Color.black);
                    g.drawRect(m, k,
                            UNIT_SIZE, UNIT_SIZE);
                }
            }
        }


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
public void printer()
{
    for(int i = 0; i< colors.length; i++) {
        System.out.println();
        for (int j = 0; j < colors[0].length; j++) {
            if (colors[i][j] != null)
                System.out.print("* ");
            else System.out.print("- ");
        }
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
/* check if the shape has reached the bottom*/
    public boolean checkLast() {

        for (int i = 0; i < shape.rows(); i++)
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
        for (int i = 0; i < shape.rows(); i++)
            for (int j = 0; j < shape.length(); j++)
                if (shape.checkNull(i, j)) {
                    colors[shape.getX(i, j) / UNIT_SIZE][shape.getY(i, j) / UNIT_SIZE] = shape.getColor();
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
                if (colors[i / UNIT_SIZE][row / UNIT_SIZE] != null) check++;
            }
            if (check == GAME_UNITS_X) cleanRow(row / UNIT_SIZE);
            check = 0;
        }
    }

    public void cleanRow(int row) {
        for (int i = 0; i < GAME_UNITS_X; i++)
            colors[i][row] = null;
        int counter=0;
        for (int j = row; j > 0 && counter < GAME_UNITS_X; j--) {
            counter = 0;
            for (int k = 0; k < GAME_UNITS_X; k++) {
                if (colors[k][j - 1] == null) counter++;
                colors[k][j] = colors[k][j - 1];
            }
        }
        for (int i = 0; i < GAME_UNITS_X; i++)
            curLast[i] += UNIT_SIZE;
    }

    //rotate the shape
    public void changeMode() {
        switch(curShape){
            case(0): //red
                break;
            case(1): //yellow
                switch(mode){
                        case(1):
                            updatePoint(1,1); //the rightest point
                            if(curX==0) curX+=UNIT_SIZE;
                            else if(colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null){ //there's a shape there
                                mode = 4; //return to previous state
                                break;
                            }
                            //else
                            shape.setLocation(1,1, curX, curY);
                            shape.setLocation(0,1, curX, curY+UNIT_SIZE);
                            shape.setLocation(1,0, curX-UNIT_SIZE, curY);
                            shape.setLocation(1,2, curX+UNIT_SIZE, curY);
                            break;
                        case (2):
                            updatePoint(1,1);
                            if(curY==BOARD_HEIGHT || colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null){ //can't perform - too low or occuiped
                                mode = 1;
                                break;
                            }
                            //else
                            shape.setLocation(0,1, curX-UNIT_SIZE, curY);
                            shape.setLocation(1,0, curX, curY-UNIT_SIZE);
                            shape.setLocation(1,2, curX, curY+UNIT_SIZE);
                            break;

                    case(3):
                        updatePoint(1,1);
                        if(curX==BOARD_WIDTH-UNIT_SIZE) { //if touches the wall
                            curX -= UNIT_SIZE;
                        }
                        else if(colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null){
                            mode = 2;
                            break;
                        }
                        //else
                        shape.setLocation(1,1,curX,curY);
                        shape.setLocation(0,1, curX, curY-UNIT_SIZE);
                        shape.setLocation(1,0, curX+UNIT_SIZE, curY);
                        shape.setLocation(1,2, curX-UNIT_SIZE, curY);
                        break;
                    case(4):
                        updatePoint(1,1);
                        if(colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null){
                            mode = 3;
                            break;
                        }
                        //else
                        shape.setLocation(0,1, curX+UNIT_SIZE, curY);
                        shape.setLocation(1,0, curX, curY+UNIT_SIZE);
                        shape.setLocation(1,2, curX, curY-UNIT_SIZE);
                        break;
                }
            break;

        }

        /*
        boolean pivotIsMoved = false; //the pivot should not be null
        if (shape.checkNull(0, 0))
            updatePoint(0, 0);
        else
            updatePoint(0, 1);
        if(curShape == 1 || curShape == 6)
            pivotIsMoved = true;
                switch(mode){
                    case 1:
                        if(pivotIsMoved && curX!=0)
                            curX -= UNIT_SIZE;
                        curY-=(shape.raws()-1)*UNIT_SIZE;
                        break;
                    case 2:
                        if(pivotIsMoved)
                            curY -= UNIT_SIZE;
                        break;
                    case 3:
                        if(pivotIsMoved && curX!=BOARD_WIDTH)
                            curX += UNIT_SIZE;

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
                */
        }


                public void move () {
                for (int i = 0; i < shape.rows(); i++)
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

                            for (int i = 0; i < shape.rows() && moveLeft; i++)
                                for (int j = 0; j < shape.length() && moveLeft; j++) {
                                    if (shape.checkNull(i, j)) {

                                        if (shape.getX(i, j) == 0 || colors[shape.getX(i, j) / UNIT_SIZE][shape.getY(i, j) / UNIT_SIZE] != null
                                                && !shape.checkNull(i, j - 1)) {
                                            moveLeft = false;
                                        }
                                    }

                                }
                            if (moveLeft)
                                for (int i = 0; i < shape.rows(); i++)
                                    for (int j = 0; j < shape.length(); j++) {
                                        if (shape.checkNull(i, j))
                                            shape.setLocation(i, j, shape.getX(i, j) - UNIT_SIZE,
                                                    shape.getY(i, j));
                                    }
                            break;

                        case KeyEvent.VK_RIGHT:
                            moveRight = true;
                            for (int i = 0; i < shape.rows() && moveRight; i++)
                                for (int j = 0; j < shape.length() && moveRight; j++) {
                                    if (shape.checkNull(i, j)) {
                                        if (shape.getX(i, j) + UNIT_SIZE == BOARD_WIDTH
                                                || colors[shape.getX(i, j) / UNIT_SIZE][shape.getY(i, j) / UNIT_SIZE] != null
                                                && !shape.checkNull(i, j + 1))
                                            moveRight = false;
                                    }
                                }
                            if (moveRight)
                                for (int i = 0; i < shape.rows(); i++)
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
                            for (int i = 0; i < shape.rows(); i++)
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
