package prak3;

import robocode.*;

/**
 * Ein Roboter, der andere Roboter verfolgt
 */
public class Kollisionsbot extends AdvancedRobot {

    String lockedOnName;
    boolean lockedOn = false;

    /**
     * Hauptmethode des Roboter
     * Dreht das Radar permanent und scant die Umgebung nach anderen Robotern ab
     */
    public void run() {
        while (true) {
            turnRadarRight(100);
            execute();
            scan();
        }
    }


    /**
     * Wird beim erfolgreichen scan() aufgerufen
     * Locked den Kollisionsbot auf einen anderen Roboter und verfolgt diesen bis der
     * andere Roboter eliminiert wurde
     * @param e - ScanRobotEvent mit den Daten ueber den gescannten Roboter
     */
    @Override
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

    /**
     * Ueberprueft, ob der Kollisionsbot einen anderen Roboter eliminiert hat
     * @param event - Wenn der Kollisionsbot einen Robot trifft
     */
    public void onBulletHit(BulletHitEvent event) {
        if (event.getEnergy()<=0){
            System.out.println("Eliminatet Target: "+lockedOnName);
            lockedOn = false;
        }
    }

    /**
     * Ueberprueft, ob der LockOn-Robot noch lebt
     * @param event - Wenn ein Roboter eliminiert wird
     */
    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        if (event.getName().equals(lockedOnName)){
            lockedOn = false;
            lockedOnName = "";
        }
    }
}
