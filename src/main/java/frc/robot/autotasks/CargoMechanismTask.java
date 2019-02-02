package frc.robot.autotasks;

import frc.robot.CargoMechanismInterface;

public class CargoMechanismTask implements TaskInterface {

    private CargoMechanismInterface mechanism;
    private boolean isShooting;
    private boolean isFinished;
    private int numTicks;
    private int duration;
    
    //TODO: make boolean isShooting into enum typeOfTask
    //TODO: make unit of duration variable seconds instead of ticks

    public CargoMechanismTask(CargoMechanismInterface c, boolean isShooting, int duration){
        this.mechanism = c;
        this.isShooting = isShooting;
        this.duration = duration;
    }

    @Override
    public void start() {
        numTicks = 0;
    }

    @Override
    public void execute() {

        if (numTicks < duration){
            if (isShooting){
                mechanism.launchBall();
            } else {
                mechanism.intakeBall();
            }
        } else {
            end();
        }
        numTicks++;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end() {
        if (isShooting){
            mechanism.launchBallOff();
        } else {
            mechanism.intakeBallOff();
        }
        isFinished = true;
    }
}