package prak4;

import robocode.*;

import java.awt.event.KeyEvent;

/**
 * Strategy-Implementation zu dem Umrandungsbot
 * @see prak3.Umrandungsbot
 */
public class UmrandungsBotStrategy implements Strategy {

    private boolean first = true;
    private int turnDirection = -1; //1 equals clockwise and -1 equals counterclockwise
    private AdvancedRobot advancedRobot;

    /**
     * Wird beim Ändern der Strategy ausgeführt und übergibt den AdvancedRobot, der die Strategy ändern soll
     * @param r - AdvancedRobot der die Strategy ändern soll
     */
    @Override
    public void identify(AdvancedRobot r) {
        advancedRobot = r;
        advancedRobot.setTurnLeft(advancedRobot.getHeading()%90);
        advancedRobot.setTurnGunLeft(90);
        advancedRobot.setAhead(advancedRobot.getBattleFieldHeight()+advancedRobot.getBattleFieldWidth());
    }

    /**
     * Wird solange die Strategy aktiv ist in einer while-Schleife ausgeführt
     */
    @Override
    public void move() {
            if (advancedRobot.getHeading()%90 != 0){      //Korrigiert die Fahrtrichtung auf einen rechten Winkel
                advancedRobot.setTurnLeft(advancedRobot.getHeading()%90);
            }
        advancedRobot.setAhead(20);
        advancedRobot.execute();
    }

    /**
     * EventHandler auf verschiedene Events
     * @param e event
     */
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
        advancedRobot.fire(1);
    }
}
