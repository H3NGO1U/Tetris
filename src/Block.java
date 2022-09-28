import java.awt.*;

public class Block {
    final int UNIT_SIZE = 30;
    private Point[][] shape;
    private Color color;
    private int id;
    private int rows;
    private int cols;


    public Block(int id){
        this.id= id;
        switch(this.id){
            case (0):
                this.color = Color.RED;
                this.rows = 2;
                this.cols = 2;
                break;

            case (1):
                this.rows = 2;
                this.cols = 3;
                this.color = Color.YELLOW;
                break;

            case (2):
                rows = 2;
                cols = 3;
                color = Color.magenta;
                break;

            case (3):
                rows = 2;
                cols = 3;
                color = Color.blue;
                break;
            case (4):
                rows = 1;
                cols = 4;
                color = Color.orange;
                break;

            case (5):
                rows = 2;
                cols = 3;
                color = Color.green;
                break;
            default:
                rows = 2;
                cols = 3;
                color = Color.cyan;
        }
    }
    public void posShape(int X, int Y){
        switch(id){
            case (0): {
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++)
                        shape[i][j] = new Point(X + j * UNIT_SIZE,
                                Y+ i * UNIT_SIZE);
                break;
            }
            case (1): {
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        if (i == 1 && j == 0 || i == 1 && j == 2) continue;
                        shape[i][j] = new Point(X + j * UNIT_SIZE,
                                Y + i * UNIT_SIZE);
                    }

                break;
            }
            case (2): {
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        if (i == 1 && j == 0 || i == 1 && j == 1) continue;
                        shape[i][j] = new Point(X + j * UNIT_SIZE,
                                Y + i * UNIT_SIZE);

                    }
                break;
            }
            case (3):
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        if (i == 1 && j == 1 || i == 1 && j == 2) continue;
                        shape[i][j] = new Point(X + j * UNIT_SIZE,
                                Y + i * UNIT_SIZE);
                    }
                break;
            case (4):
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        shape[i][j] = new Point(X  + j * UNIT_SIZE,
                                Y +i * UNIT_SIZE);
                    }
                break;

            case (5):
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        if (i == 0 && j == 2 || i == 1 && j == 0) continue;
                        shape[i][j] = new Point(X + j * UNIT_SIZE,
                                Y + i * UNIT_SIZE);
                    }
                break;
            default:
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        if (i == 0 && j == 0 || i == 1 && j == 2) continue;
                        shape[i][j] = new Point(X + j * UNIT_SIZE,
                                Y +i * UNIT_SIZE);
                    }
        }
    }
    public int getX(int i,int j){
        if(i>=rows||j>=cols || shape[i][j]==null) return -1;
        return (int)shape[i][j].getX();
    }

    public int getY(int i,int j){
        if(i>=rows||j>=cols || shape[i][j]==null) return -1;
        return (int)shape[i][j].getY();
    }
    public boolean checkNull(int i, int j){
        if(i>=rows||j>=cols||i<0||j<0) return false;
        return shape[i][j]!=null;
    }
    public void setLocation(int i, int j, int x, int y){
        if(i>=rows||j>=cols||i<0||j<0) return;
        shape[i][j].setLocation(x,y);
    }
    public int length(){
        return cols;
    }
    public int rows(){
        return rows;
    }

    public Color getColor() {
        return color;
    }
}
