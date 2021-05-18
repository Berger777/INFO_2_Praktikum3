package prak3;

import robocode.*;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

/**
 * Ein Roboter, der vom User gesteuert werden kann
 * Der Roboter kann:
 * mit H - nach links fahren
 * mit J - nach rechts fahren
 * mit K - die Richtung auf geradeaus setzen
 * mit L - die Richtung auf rueckwaerts setzen
 * mit Space - die Kanone feuern
 */
public class Tastaturbot extends AdvancedRobot {

    boolean moveForward = false;
    boolean moveBackward = false;

    /**
     * Hauptfunktion des Roboters
     */
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

    /**
     * Handelt, wenn eine Taste auf der Tastatur gedrueckt wird die Verarbeitung des Roboter
     * @param e - TastaturEvent
     */
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

    /**
     * Schiesst, wenn sich ein Roboter direkt vor der Kanone steht
     * @param e - ScanEvent
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }
}
