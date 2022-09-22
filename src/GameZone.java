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
    final int MIDDLE = BOARD_WIDTH/2;
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
    Score score = new Score();
    GameZone() {

        random = new Random();
        this.setLayout(null);
        this.setOpaque(true);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
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
        mode = 1;
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
/*check all the rows if they are full*/
    public void checkRow() {
        int check = 0;
        for (int row = 0; row < BOARD_HEIGHT; row += UNIT_SIZE) {
            for (int i = 0; i < BOARD_WIDTH; i += UNIT_SIZE) {
                if (colors[i / UNIT_SIZE][row / UNIT_SIZE] != null) check++;
            }
            if (check == GAME_UNITS_X){
                cleanRow(row / UNIT_SIZE);
                score.addScore();
            }
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
        updatePoint(0,1);
        switch(curShape){
            case(0): //red
                break;
            case(1): //yellow
                switch(mode){
                        case(1):
                            updatePoint(0,1); //the rightest point
                            if(curX==0 || colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null){ //there's a shape there
                                mode = 4; //return to previous state
                                break;
                            }
                            //else
                            shape.setLocation(1,1, curX, curY+UNIT_SIZE);
                            shape.setLocation(0,0, curX-UNIT_SIZE, curY);
                            shape.setLocation(0,2, curX+UNIT_SIZE, curY);
                            break;
                        case (2):
                            if(curY==BOARD_HEIGHT || colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null){ //
                                mode = 1;
                                break;
                            }
                            //else
                            shape.setLocation(1,1, curX-UNIT_SIZE, curY);
                            shape.setLocation(0,0, curX, curY-UNIT_SIZE);
                            shape.setLocation(0,2, curX, curY+UNIT_SIZE);
                            break;

                    case(3):
                        if(curX==BOARD_WIDTH-UNIT_SIZE || colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null){
                            mode = 2;
                            break;
                        }
                        //else
                        shape.setLocation(1,1,curX,curY-UNIT_SIZE);
                        shape.setLocation(0,0, curX+UNIT_SIZE, curY);
                        shape.setLocation(0,2, curX-UNIT_SIZE, curY);
                        break;
                    case(4):
                        if(curY==BOARD_HEIGHT-UNIT_SIZE || colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null){
                            mode = 3;
                            break;
                        }
                        //else
                        shape.setLocation(1,1, curX+UNIT_SIZE, curY);
                        shape.setLocation(0,0, curX, curY+UNIT_SIZE);
                        shape.setLocation(0,2, curX, curY-UNIT_SIZE);
                        break;
                }
            break;
            case(2): //purple
                switch(mode){
                    case(1):
                        if(curX==0 || colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null
                                ||colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null
                                ||colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null){ //check space from left
                            mode = 4;
                            break;
                        }
                        //else
                        shape.setLocation(0,0, curX-UNIT_SIZE, curY);
                        shape.setLocation(0,2, curX+UNIT_SIZE, curY);
                        shape.setLocation(1,2, curX+UNIT_SIZE, curY+UNIT_SIZE);
                        break;
                    case(2):
                        if(curY==0|| colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null
                                ||colors[(curX)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null
                                ||colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null) {//check one space from top and two spaces bottom-left
                            mode= 1;
                            break;
                        }
                        shape.setLocation(0,0, curX, curY-UNIT_SIZE);
                        shape.setLocation(0,2, curX, curY+UNIT_SIZE);
                        shape.setLocation(1,2, curX-UNIT_SIZE, curY+UNIT_SIZE);
                        break;
                    case(3):
                        if(curX>=BOARD_WIDTH-UNIT_SIZE || colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null
                                ||colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null
                                ||colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null){ //check two spaces from left
                            mode = 2;
                            break;
                        }
                        //else
                        shape.setLocation(0,0, curX+UNIT_SIZE, curY);
                        shape.setLocation(0,2, curX-UNIT_SIZE, curY);
                        shape.setLocation(1,2, curX-UNIT_SIZE, curY-UNIT_SIZE);
                        break;
                    case(4):
                        if(curY>=BOARD_HEIGHT-UNIT_SIZE|| colors[(curX)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null
                                ||colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null
                                ||colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null) {//check two spaces from top)
                            mode= 3;
                            break;
                        }
                        shape.setLocation(0,0, curX, curY+UNIT_SIZE);
                        shape.setLocation(0,2, curX, curY-UNIT_SIZE);
                        shape.setLocation(1,2, curX+UNIT_SIZE, curY-UNIT_SIZE);
                        break;
                }
            break;
            case(3)://blue
                switch(mode){
                        case(1):
                            if(curX==0 || colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null//(0,0)
                                    ||colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null//(0,2)
                                    ||colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null){ //(1,0)
                                mode = 4;
                                break;
                            }
                            //else
                            shape.setLocation(0,0, curX-UNIT_SIZE, curY);
                            shape.setLocation(0,2, curX+UNIT_SIZE, curY);
                            shape.setLocation(1,0, curX-UNIT_SIZE, curY+UNIT_SIZE);
                            break;
                        case(2):
                            if(curY==0|| colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null
                                    ||colors[(curX)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null
                                    ||colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null) {//check one space from top and two spaces bottom-left)
                                mode= 1;
                                break;
                            }
                            shape.setLocation(0,0, curX, curY-UNIT_SIZE);
                            shape.setLocation(0,2, curX, curY+UNIT_SIZE);
                            shape.setLocation(1,0, curX-UNIT_SIZE, curY-UNIT_SIZE);
                            break;
                        case(3):
                            if(curX>=BOARD_WIDTH-UNIT_SIZE || colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null
                                    ||colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null
                                    ||colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null){ //check two spaces from left
                                mode = 2;
                                break;
                            }
                            //else
                            shape.setLocation(0,0, curX+UNIT_SIZE, curY);
                            shape.setLocation(0,2, curX-UNIT_SIZE, curY);
                            shape.setLocation(1,0, curX+UNIT_SIZE, curY-UNIT_SIZE);
                            break;
                        case(4):
                            if(curY>=BOARD_HEIGHT-UNIT_SIZE|| colors[(curX)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null
                                    ||colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null
                                    ||colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null) {//check two spaces from top)
                                mode= 3;
                                break;
                            }
                            shape.setLocation(0,0, curX, curY+UNIT_SIZE);
                            shape.setLocation(0,2, curX, curY-UNIT_SIZE);
                            shape.setLocation(1,0, curX+UNIT_SIZE, curY+UNIT_SIZE);
                            break;
                    }
                    break;
            case(4):
                switch(mode){
                    case(1):
                    case(3):
                        if(curX>=BOARD_WIDTH-2*UNIT_SIZE||curX<=UNIT_SIZE|| colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null //(0,0)
                                ||colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null//(0,2)
                                ||colors[(curX+2*UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null) {//(0,3)
                            mode= 3;
                            break;
                        }
                        //else
                        shape.setLocation(0,0, curX-UNIT_SIZE, curY);
                        shape.setLocation(0,2, curX+UNIT_SIZE, curY);
                        shape.setLocation(0,3, curX+2*UNIT_SIZE, curY);
                    break;

                    case(2):
                    case(4):
                        if(curY>=BOARD_HEIGHT-2*UNIT_SIZE||curY<=UNIT_SIZE|| colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null //(0,0)
                                ||colors[(curX)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null//(0,2)
                                ||colors[(curX)/UNIT_SIZE][(curY+2*UNIT_SIZE)/UNIT_SIZE]!=null) {//(0,3)
                            mode= 3;
                            break;
                        }
                        //else
                        shape.setLocation(0,0, curX, curY-UNIT_SIZE);
                        shape.setLocation(0,2, curX, curY+UNIT_SIZE);
                        shape.setLocation(0,3, curX, curY+2*UNIT_SIZE);
                        break;
                }
                break;
            case(5)://green
                switch(mode){
                    case(1):
                    case(3):
                        if(curX>=BOARD_WIDTH-UNIT_SIZE||
                        colors[(curX)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null||
                        colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null){
                            mode = 3;
                            break;
                        }
                        //else
                        shape.setLocation(0,0, curX-UNIT_SIZE, curY);
                        shape.setLocation(1,1, curX, curY+UNIT_SIZE);
                        shape.setLocation(1,2, curX+UNIT_SIZE, curY+UNIT_SIZE);
                        break;
                    case(2):
                    case(4):
                        if(curY == 0||
                                colors[(curX)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null||
                                colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY+UNIT_SIZE)/UNIT_SIZE]!=null){
                            mode = 2;
                            break;
                        }
                        //else
                        shape.setLocation(0,0, curX, curY-UNIT_SIZE);
                        shape.setLocation(1,1, curX-UNIT_SIZE, curY);
                        shape.setLocation(1,2, curX-UNIT_SIZE, curY+UNIT_SIZE);
                        break;
                }
            break;
            case(6):
                switch(mode){
                    case(1):
                    case(3):
                        if(curX>=BOARD_WIDTH-UNIT_SIZE||
                                colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null||
                                colors[(curX+UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null){
                            mode = 3;
                            break;
                        }
                        //else
                        shape.setLocation(0,2, curX+UNIT_SIZE, curY);
                        shape.setLocation(1,1, curX, curY+UNIT_SIZE);
                        shape.setLocation(1,0, curX-UNIT_SIZE, curY+UNIT_SIZE);
                        break;
                    case(2):
                    case(4):
                        if(curY == 0||
                                colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY)/UNIT_SIZE]!=null|| //(1,1)
                                colors[(curX-UNIT_SIZE)/UNIT_SIZE][(curY-UNIT_SIZE)/UNIT_SIZE]!=null){//(1,0)
                            mode = 2;
                            break;
                        }
                        //else
                        shape.setLocation(0,2, curX, curY+UNIT_SIZE);
                        shape.setLocation(1,1, curX-UNIT_SIZE, curY);
                        shape.setLocation(1,0, curX-UNIT_SIZE, curY-UNIT_SIZE);
                        break;
                }
            }
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
                                        if (shape.getX(i, j) == 0 || colors[(shape.getX(i,j)-UNIT_SIZE) / UNIT_SIZE][shape.getY(i,j)/ UNIT_SIZE] != null) {
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
                                                || colors[(shape.getX(i, j)+UNIT_SIZE) / UNIT_SIZE][shape.getY(i, j) / UNIT_SIZE] != null)
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
                            boolean moveDown = true;
                            for (int i = 0; i < shape.rows() && moveDown; i++)
                                for (int j = 0; j < shape.length() && moveDown; j++)
                                    if (shape.checkNull(i, j) && shape.getY(i,j)>=BOARD_HEIGHT-UNIT_SIZE
                                            || colors[(shape.getX(i, j)) / UNIT_SIZE][(shape.getY(i, j)+UNIT_SIZE) / UNIT_SIZE]!=null)
                                        moveDown = false;
                            for (int i = 0; i < shape.rows() && moveDown; i++)
                                for (int j = 0; j < shape.length() && moveDown; j++)
                                    if (shape.checkNull(i, j)){
                                        shape.setLocation(i, j, shape.getX(i, j),
                                                shape.getY(i, j) + UNIT_SIZE);
                    }               }
                }
            }
        

        private void updatePoint (int i, int j) {
            curY = shape.getY(i,j);
            curX = shape.getX(i,j);
        }

}
