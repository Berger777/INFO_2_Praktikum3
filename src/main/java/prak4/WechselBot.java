package prak4;

import robocode.*;

import java.awt.event.KeyEvent;

/**
 * Ein Roboter, der verschiedene Strategien ausführen kann
 */
public class WechselBot extends AdvancedRobot {
    Strategy strategy = null;
    Strategy[] strategies = new Strategy[3];
    int strategyPos = 0;

    /**
     * Hauptmethode wird beim Start aufgerufen
     */
    public void run() {
        strategies[0] = new KollisionsBotStrategy();
        strategies[0].identify(this);
        strategies[1] = new TastaturBotStrategy();
        strategies[1].identify(this);
        strategies[2] = new UmrandungsBotStrategy();
        strategies[2].identify(this);
        strategy = strategies[0];
        while (true) {
            strategy.move();
        }
    }

    /**
     * Event wenn eine Taste auf der Tastatur gedrückt wird
     * @param e KeyEvent
     */
    @Override
    public void onKeyPressed(KeyEvent e) {
        System.out.println("taste" + e.getKeyChar());
        if (e.getKeyChar() == 'c') {
            toggleStrategies();
            System.out.println("change!");
        } else {
            strategy.reactKey(e);
        }
    }

    /**
     * Wenn der Roboter von einem Bullet getroffen wird
     * @param hbbe BulletHitEvent
     */
    @Override
    public void onHitByBullet(HitByBulletEvent hbbe) {
        strategy.reactEvent(hbbe);
    }

    /**
     * Wenn der Roboter einen anderen Roboter trifft
     * @param hre HitRobotEvent
     */
    @Override
    public void onHitRobot(HitRobotEvent hre) {
        strategy.reactEvent(hre);
    }

    /**
     * Wenn der Roboter eine Wand berührt
     * @param hwe HitWallEvent
     */
    @Override
    public void onHitWall(HitWallEvent hwe) {
        strategy.reactEvent(hwe);
    }

    /**
     * Wenn das Radar des Roboters einen anderen Roboter scannt
     * @param sre OnScannedRobotEvent
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent sre) {
        strategy.fire(sre);
    }

    /**
     * Ändert die Strategie des Roboters auf die nächste
     */
    private synchronized void toggleStrategies() {
        strategyPos += 1;
        if (strategyPos >= strategies.length) {
            strategyPos = 0;
        }
        System.out.println("Strategiewechsel auf " + strategyPos);
        strategy = strategies[strategyPos];
    }
}
