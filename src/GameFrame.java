import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameFrame(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.gray);
        GameZone gameZone = new GameZone();
        this.add(gameZone);
        gameZone.setBounds(20,20,300,540);
        new GameThread(gameZone).start();
        this.setVisible(true);
    }
}
