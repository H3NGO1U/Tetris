import java.awt.*;

public class Block {
    final int MIDDLE = 120;
    final int UNIT_SIZE = 20;
    private Point[][] shape;
    private Color color;
    private int rows;
    private int cols;


    public Block(int id){
        switch(id){
            case (0): {
                color = Color.RED;
                rows = 2;
                cols = 2;
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++)
                        shape[i][j] = new Point(MIDDLE + j * UNIT_SIZE,
                                i * UNIT_SIZE);
                break;
            }
            case (1): {
                rows = 2;
                cols = 3;
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        color = Color.YELLOW;
                        if (i == 0 && j == 0 || i == 0 && j == 2) continue;
                        shape[i][j] = new Point(MIDDLE + j * UNIT_SIZE,
                                 i * UNIT_SIZE);
                    }

                break;
            }
            case (2): {

                rows = 2;
                cols = 3;
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        color = Color.magenta;
                        if (i == 1 && j == 0 || i == 1 && j == 1) continue;
                        shape[i][j] = new Point(MIDDLE + j * UNIT_SIZE,
                                 i * UNIT_SIZE);

                    }
                break;
            }
            case (3):
                rows = 2;
                cols = 3;
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        color = Color.blue;
                        if (i == 1 && j == 1 || i == 1 && j == 2) continue;
                        shape[i][j] = new Point(MIDDLE + j * UNIT_SIZE,
                                 i * UNIT_SIZE);
                    }
                break;
            case (4):
                rows = 1;
                cols = 4;
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        color = Color.orange;
                        shape[i][j] = new Point(MIDDLE + j * UNIT_SIZE,
                                  i * UNIT_SIZE);
                    }
                break;

            case (5):
                rows = 2;
                cols = 3;
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        color = Color.green;
                        if (i == 0 && j == 2 || i == 1 && j == 0) continue;
                        shape[i][j] = new Point(MIDDLE + j * UNIT_SIZE,
                                 i * UNIT_SIZE);
                    }
                break;
            default:
                rows = 2;
                cols = 3;
                shape = new Point[rows][cols];
                for (int i = 0; i < rows; i++)
                    for (int j = 0; j < cols; j++) {
                        color = Color.cyan;
                        if (i == 0 && j == 0 || i == 1 && j == 2) continue;
                        shape[i][j] = new Point(MIDDLE + j * UNIT_SIZE,
                                 i * UNIT_SIZE);
                    }
        }

    }
    public int getX(int i,int j){
        if(i>=rows||j>=cols) return -1;
        return (int)shape[i][j].getX();
    }

    public int getY(int i,int j){
        if(i>=rows||j>=cols) return -1;
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
    public int raws(){
        return rows;
    }

    public Color getColor() {
        return color;
    }
}
