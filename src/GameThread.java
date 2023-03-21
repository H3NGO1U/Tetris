import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class GameThread extends Thread{
    GameZone ga;
    public GameThread(GameZone gameZone){
            ga = gameZone;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean runner = true;
        while(runner) {
            while (runner) {
                if(ga.endgame){
                    runner = false;
                }

                try {
                    if (!(runner && ga.running && ga.checkLast() && !ga.animation)) break;
                } catch (LineUnavailableException e) {
                   return;
                } catch (IOException e) {
                   return;
                }

                ga.move();
                try {
                    Thread.sleep(ga.speed);
                } catch (InterruptedException e) {
                   return;
                }
            }
            System.out.print("");
        }
    }
}
