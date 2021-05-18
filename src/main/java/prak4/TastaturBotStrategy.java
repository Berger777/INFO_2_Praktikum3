package prak4;

import robocode.AdvancedRobot;
import robocode.Event;
import robocode.ScannedRobotEvent;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

public class TastaturBotStrategy implements Strategy {

    boolean moveForward = false;
    boolean moveBackward = false;
    private AdvancedRobot advancedRobot;

    @Override
    public void identify(AdvancedRobot r) {
        advancedRobot = r;
    }

    @Override
    public void move() {
        if(moveBackward){
            advancedRobot.setBack(20);
        }else if(moveForward){
            advancedRobot.setAhead(20);
        }
        advancedRobot.execute();
    }

    @Override
    public void reactEvent(Event e) {

    }

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


    @Override
    public void fire(ScannedRobotEvent e) {
        advancedRobot.fire(1);
    }
}
