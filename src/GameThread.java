import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class GameThread extends Thread{
    GameZone ga;
    public GameThread(GameZone gameZone){
            ga = gameZone;
    }

    @Override
    public void run() {
        while(true) {
            while (true) {
                try {
                    if (!(!ga.endgame && ga.running && ga.checkLast() && !ga.animation)) break;
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ga.move();
                try {
                    Thread.sleep(ga.speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("");
        }
    }
}
