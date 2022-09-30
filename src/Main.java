import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Main {
    static StartGame SG;
    static GameFrame GF;
    static LeaderBoard LB;
    public static void main(String[] args){
          SwingUtilities.invokeLater(() -> {
              SG = new StartGame();
              try {
                  GF = new GameFrame();
              } catch (UnsupportedAudioFileException e) {
                  e.printStackTrace();
              } catch (LineUnavailableException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }
             SG.setVisible(true);
          });

    }

    public static void Start(){
        GF.start();
    }
    public static void endGame(String name, int score){
        SG.setVisible(false);
        GF.setVisible(false);
        LB = new LeaderBoard();
        LB.addName(name, score);
    }
}
