public class GameThread extends Thread{
    GameZone ga;
    public GameThread(GameZone gameZone){
            ga = gameZone;
    }

    @Override
    public void run() {
        while(true) {
            while (!ga.endgame && ga.running && ga.checkLast()) {
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
