public class GameThread extends Thread{
    GameZone ga;
    public GameThread(GameZone gameZone){
            ga = gameZone;
    }

    @Override
    public void run() {
        while(true) {
            while (ga.checkLast()) {
                ga.move();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
