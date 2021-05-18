package prak4;

import robocode.*;

import java.awt.event.KeyEvent;

public class UmrandungsBotStrategy implements Strategy {

    boolean moveForward = true;
    boolean moveBackward = false;
    boolean first = true;
    int turnDirection = -1; //1 equals clockwise and -1 equals counterclockwise
    private AdvancedRobot advancedRobot;

    @Override
    public void identify(AdvancedRobot r) {
        advancedRobot = r;
        advancedRobot.setTurnLeft(advancedRobot.getHeading()%90);
        advancedRobot.setTurnGunLeft(90);
        advancedRobot.setAhead(advancedRobot.getBattleFieldHeight()+advancedRobot.getBattleFieldWidth());
    }

    @Override
    public void move() {
            if (advancedRobot.getHeading()%90 != 0){      //Korrigiert die Fahrtrichtung auf einen rechten Winkel
                advancedRobot.setTurnLeft(advancedRobot.getHeading()%90);
            }
            if (moveBackward) {
                advancedRobot.setBack(20);
            } else if (moveForward) {
                advancedRobot.setAhead(20);
            }
            advancedRobot.execute();
    }

    @Override
    public void reactEvent(Event e) {
        if (e.getClass().equals(HitByBulletEvent.class)) {
            if (!first) {
                advancedRobot.stop();
                if (turnDirection == 1) {
                    turnDirection = -1;
                    advancedRobot.turnLeft(180);
                    advancedRobot.turnGunRight(180);
                } else if (turnDirection == -1) {
                    turnDirection = 1;
                    advancedRobot.turnLeft(180);
                    advancedRobot.turnGunRight(180);
                }
                advancedRobot.resume();
            }
        }else if (e.getClass().equals(HitWallEvent.class)){
            if (turnDirection==1){
                advancedRobot.turnRight(90);
            }else if (turnDirection==-1){
                advancedRobot.turnLeft(90);
            }
            first = false;
        }
    }

    @Override
    public void reactKey(KeyEvent e) {
    }


    @Override
    public void fire(ScannedRobotEvent e) {
        advancedRobot.fire(1);
    }
}
