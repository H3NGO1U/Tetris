public class GameThread extends Thread{
    GameZone ga;
    public GameThread(GameZone gameZone){
            ga = gameZone;
    }

    @Override
    public void run() {
        while(true) {
            while (ga.checkLast() && ga.running) {
                ga.move();
                try {
                    Thread.sleep(ga.speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
