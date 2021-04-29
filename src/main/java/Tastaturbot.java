import robocode.*;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

public class Tastaturbot extends AdvancedRobot {

    boolean moveForward = false;
    boolean moveBackward = false;

    public void run() {
        while (true) {
            if(moveBackward){
                setBack(20);
            }else if(moveForward){
                setAhead(20);
            }
            execute();
        }
    }

    public void onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_H:      //nach links drehen
                setTurnLeft(45);
                break;
            case VK_J:      //nach rechts drehen
                setTurnRight(45);
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
                fire(1);
                break;
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }
}
