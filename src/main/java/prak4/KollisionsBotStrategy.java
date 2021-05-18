package prak4;

import robocode.*;

import java.awt.event.KeyEvent;

public class KollisionsBotStrategy implements Strategy {

    String lockedOnName;
    boolean lockedOn = false;
    private AdvancedRobot advancedRobot;

    @Override
    public void identify(AdvancedRobot r) {
        advancedRobot = r;
    }

    @Override
    public void move() {
        advancedRobot.turnRadarRight(100);
        advancedRobot.execute();
        advancedRobot.scan();
    }

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

    @Override
    public void reactKey(KeyEvent e) {
    }


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
