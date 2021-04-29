import robocode.*;

public class Umrandungsbot extends AdvancedRobot {

    boolean moveForward = true;
    boolean moveBackward = false;
    boolean first = true;
    int turnDirection = -1; //1 equals clockwise and -1 equals counterclockwise

    public void run() {
        setTurnLeft(getHeading()%90);
        setTurnGunLeft(90);
        setAhead(2000);

        while (true) {
            if (getHeading()%90 != 0){
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

    public void onHitWall(HitWallEvent event) {
        if (turnDirection==1){
            turnRight(90);
        }else if (turnDirection==-1){
            turnLeft(90);
        }
        first = false;
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }

}
