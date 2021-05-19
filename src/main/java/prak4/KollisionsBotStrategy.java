package prak4;

import robocode.*;

import java.awt.event.KeyEvent;

/**
 * Strategy-Implementation zu dem Kollisionsbot
 * @see prak3.Kollisionsbot
 */
public class KollisionsBotStrategy implements Strategy {

    private String lockedOnName;
    private boolean lockedOn = false;
    private AdvancedRobot advancedRobot;

    /**
     * Wird beim Ändern der Strategy ausgeführt und übergibt den AdvancedRobot, der die Strategy ändern soll
     * @param r - AdvancedRobot der die Strategy ändern soll
     */
    @Override
    public void identify(AdvancedRobot r) {
        advancedRobot = r;
    }

    /**
     * Wird solange die Strategy aktiv ist in einer while-Schleife ausgeführt
     */
    @Override
    public void move() {
        advancedRobot.turnRadarRight(100);
        advancedRobot.execute();
        advancedRobot.scan();
    }

    /**
     * EventHandler auf verschiedene Events
     * @param e event
     */
    @Override
    public void reactEvent(Event e) {
        if (e.getClass().equals(BulletHitEvent.class)){
            BulletHitEvent bulletHitEvent = (BulletHitEvent) e;
            if (bulletHitEvent.getEnergy()<=0){
                System.out.println("Eliminatet Target: "+lockedOnName);
                lockedOn = false;
            }
        }else if(e.getClass().equals(RobotDeathEvent.class)){
            RobotDeathEvent robotDeathEvent = (RobotDeathEvent) e;
            if (robotDeathEvent.getName().equals(lockedOnName)){
                lockedOn = false;
                lockedOnName = "";
            }
        }
    }

    /**
     * Reaktion auf ein Key-Event
     * @param e KeyEvent
     */
    @Override
    public void reactKey(KeyEvent e) {
    }


    /**
     * Wenn ein anderer Roboter gescannt wurde
     * @param e ScannedRobot
     */
    @Override
    public void fire(ScannedRobotEvent e) {
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
                    advancedRobot.turnRight(bearing);
                    advancedRobot.setAhead(dist);
                } else if (bearing < 0) {
                    advancedRobot.turnLeft(-bearing);
                    advancedRobot.setAhead(dist);
                } else {
                    advancedRobot.setAhead(dist);
                }
                advancedRobot.fire(1);
                advancedRobot.execute();
                advancedRobot.scan();
                if (e.getEnergy() <= 0) {
                    lockedOn = false;
                }
                System.out.println(e.getName() + " "+ e.getEnergy());
            }
        }
    }
}
