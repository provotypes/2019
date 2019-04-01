/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autotasks;

import frc.robot.HatchPanelMechanismInterface;

/**
 * Add your docs here.
 */
public class EmptyTask implements TaskInterface {

    HatchPanelMechanismInterface panel;

    public EmptyTask(HatchPanelMechanismInterface p) {
        panel = p;
    }

    @Override
    public void start() {
        panel.setMode(HatchPanelMechanismInterface.HatchPanelMode.holdPanel);
        panel.periodic();
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end() {

    }
}
