package prak4;

import robocode.AdvancedRobot;
import robocode.Event;
import robocode.ScannedRobotEvent;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

/**
 * Strategy-Implementation zu dem Tastaturbot
 * @see prak3.Tastaturbot
 */
public class TastaturBotStrategy implements Strategy {

    private boolean moveForward = false;
    private boolean moveBackward = false;
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
        if(moveBackward){
            advancedRobot.setBack(20);
        }else if(moveForward){
            advancedRobot.setAhead(20);
        }
        advancedRobot.execute();
    }

    /**
     * EventHandler auf verschiedene Events
     * @param e event
     */
    @Override
    public void reactEvent(Event e) {

    }

    /**
     * Reaktion auf ein Key-Event
     * @param e KeyEvent
     */
    @Override
    public void reactKey(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_H:      //nach links drehen
                advancedRobot.setTurnLeft(45);
                break;
            case VK_J:      //nach rechts drehen
                advancedRobot.setTurnRight(45);
                break;
            case VK_K:      //geradeaus bewegen
                moveForward = true;
                moveBackward = false;
                break;
            case VK_L:      //nach hinten bewegen
                moveBackward = true;
                moveForward = false;
                break;
            case VK_SPACE:  //Kanone verwenden
                advancedRobot.fire(1);
                break;
        }
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
