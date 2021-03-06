
    
package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerImpl implements TrainController {

    private static final Logger LOGGER = Logger.getLogger("TrainControllerImpl");
    private int step = 0;
    private int referenceSpeed = 0;
    private int speedLimit = 0;
    private Timer timer;

    public TrainControllerImpl() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                followSpeed();
            }
        }, 2000,2000);
    }

    @Override
    public void followSpeed() {
        if (referenceSpeed < 0) {
            referenceSpeed = 0;
        } else {
            if (referenceSpeed + step > 0) {
                referenceSpeed += step;
            } else {
                referenceSpeed = 0;
            }
        }

        enforceSpeedLimit();
    }

    @Override
    public int getReferenceSpeed() {
        return referenceSpeed;
    }

    @Override
    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
        enforceSpeedLimit();

    }

    private void enforceSpeedLimit() {
        if (referenceSpeed > speedLimit) {
            referenceSpeed = speedLimit;
        }
    }

    @Override
    public void setJoystickPosition(int joystickPosition) {
        this.step = joystickPosition;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }

    }

    public void doubleSpeedLimit() {
        speedLimit *= 2;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

}

