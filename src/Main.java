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
              LB = new LeaderBoard();
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
        try {
            GF = new GameFrame();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GF.start();
        System.out.println(Thread.activeCount());

    }
    public static void endGame(String name, int score){
        showLeaderBoard();
        LB.addName(name, score);
    }

    public static void showLeaderBoard(){
        SG.setVisible(false);
        GF.dispose();
        LB.setVisible(true);
    }

    public static void MainPage(){
        LB.setVisible(false);
        GF.dispose();
        SG.setVisible(true);
    }
}
