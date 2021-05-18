package prak4;

import robocode.*;

import java.awt.event.KeyEvent;

public class WechselBot extends AdvancedRobot {
    Strategy strategy = null;
    Strategy[] strategies = new Strategy[3];
    int strategyPos = 0;

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

    @Override
    public void onHitByBullet(HitByBulletEvent hbbe) {
        strategy.reactEvent(hbbe);
    }

    @Override
    public void onHitRobot(HitRobotEvent hre) {
        strategy.reactEvent(hre);
    }

    @Override
    public void onHitWall(HitWallEvent hwe) {
        strategy.reactEvent(hwe);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent sre) {
        strategy.fire(sre);
    }

    private synchronized void toggleStrategies() {
        strategyPos += 1;
        if (strategyPos >= strategies.length) {
            strategyPos = 0;
        }
        System.out.println("Strategiewechsel auf " + strategyPos);
        strategy = strategies[strategyPos];
    }
}
