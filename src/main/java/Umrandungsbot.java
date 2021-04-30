import robocode.*;

/**
 * Ein Roboter, der am Rande des Spielfelds lang fährt und in die Mitte schießt
 */
public class Umrandungsbot extends AdvancedRobot {

    boolean moveForward = true;
    boolean moveBackward = false;
    boolean first = true;
    int turnDirection = -1; //1 equals clockwise and -1 equals counterclockwise

    /**
     * Hauptmethode des Roboters
     */
    public void run() {
        setTurnLeft(getHeading()%90);
        setTurnGunLeft(90);
        setAhead(getBattleFieldHeight()+getBattleFieldWidth());

        while (true) {
            if (getHeading()%90 != 0){      //Korrigiert die Fahrtrichtung auf einen rechten Winkel
                setTurnLeft(getHeading()%90);
            }
            if (moveBackward) {
                setBack(20);
            } else if (moveForward) {
                setAhead(20);
            }
            execute();
        }
    }

    /**
     * Wenn der Roboter von einer Kugel getroffen wurde, dreht er seine Fahrtrichtung um
     * @param event - HitByBulletEvent
     */
    public void onHitByBullet(HitByBulletEvent event) {
        if (!first) {
            stop();
            if (turnDirection==1){
                turnDirection=-1;
                turnLeft(180);
                turnGunRight(180);
            }else if (turnDirection==-1){
                turnDirection=1;
                turnLeft(180);
                turnGunRight(180);
            }
            resume();
        }
    }

    /**
     * Wenn der Roboter eine Wand trifft, dreht er sich und seine Kanone
     * @param event
     */
    public void onHitWall(HitWallEvent event) {
        if (turnDirection==1){
            turnRight(90);
        }else if (turnDirection==-1){
            turnLeft(90);
        }
        first = false;
    }

    /**
     * Wenn der Roboter einen Roboter sieht schießt er
     * @param e
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }

}
