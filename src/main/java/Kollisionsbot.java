import robocode.*;

public class Kollisionsbot extends AdvancedRobot {

    String lockedOnName;
    boolean lockedOn = false;

    public void run() {

        while (true) {
            turnRadarRight(100);
            execute();
            scan();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        System.out.println("Found "+ e.getName() + " " +lockedOnName + " "+ " "+ lockedOn);
        if (!lockedOn) {
            lockedOnName = e.getName();
            lockedOn = true;
            System.out.println("Locked on "+ lockedOnName);
        } else {
            if (e.getName().equals(lockedOnName)) {
                double bearing = e.getBearing();
                double dist = e.getDistance();

                if (bearing > 0) {
                    turnRight(bearing);
                    setAhead(dist);
                } else if (bearing < 0) {
                    turnLeft(-bearing);
                    setAhead(dist);
                } else {
                    setAhead(dist);
                }
                fire(1);
                execute();
                scan();
                if (e.getEnergy() <= 0) {
                    lockedOn = false;
                }
                System.out.println(e.getName() + " "+ e.getEnergy());
            }
        }
    }

    public void onBulletHit(BulletHitEvent event) {
        if (event.getName().equals(lockedOnName) && event.getEnergy()<=0){
            System.out.println("Eliminatet Target: "+lockedOnName);
            lockedOn = false;
        }
    }
}
