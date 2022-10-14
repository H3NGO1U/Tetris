import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {
    static StartGame SG;
    static GameFrame GF;
    static LeaderBoard LB;
    static File file = new File("clack.wav");
    static AudioInputStream audio;
    static Clip clip;
    public static void main(String[] args){
        try {
            audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
              SG = new StartGame();
            try {
                LB = new LeaderBoard();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
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

    public static void playButton(){
        clip.start();
        clip.setMicrosecondPosition(0);
    }
}
