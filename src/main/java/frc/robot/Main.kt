package frc.robot

import edu.wpi.first.wpilibj.RobotBase

object Main {

    @JvmStatic
    fun main() {
        RobotBase.startRobot<Robot> { Robot() }
    }
}
