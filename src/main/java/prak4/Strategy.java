package prak4;

import robocode.AdvancedRobot;
import robocode.Event;
import java.awt.event.KeyEvent;
import robocode.ScannedRobotEvent;

public interface Strategy {
        void identify(AdvancedRobot r);
        void move();
        void reactEvent(Event e);
        void reactKey(KeyEvent e);
        void fire(ScannedRobotEvent e);
}
