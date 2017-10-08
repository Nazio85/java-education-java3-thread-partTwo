public class Car implements Runnable {
    public static final int MAIN_THREAD_IS_FIRST = 1;
    private static int CARS_COUNT;
    private static int place = 0;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.startRace.await();
            Thread.sleep(MAIN_THREAD_IS_FIRST);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            if (i == race.getStages().size()-1) {
                MainClass.endRace.countDown();
                if (++place == 1) System.out.println(this.name + " WIN");
                else System.out.println(this.name + " знанял " + place + " место");
            }

        }
    }
}
