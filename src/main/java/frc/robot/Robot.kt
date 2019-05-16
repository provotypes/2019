package frc.robot

import edu.wpi.first.wpilibj.Joystick

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.robot.operatorinterface.Extreme3DProJoystick
import frc.robot.operatorinterface.LogitechGamepadController
import frc.robot.subsystems.CargoMechanismInterface
import frc.robot.subsystems.DrivetrainInterface
import frc.robot.subsystems.HatchPanelMechanismInterface


class Robot : TimedRobot() {

    private var drivetrain = DrivetrainInterface.getDefaultDrivetrain()
    private var cargo = CargoMechanismInterface.getDefaulCargoMechanism()
    private var panel = HatchPanelMechanismInterface.getDefaultHatchPanelMechanism()

    private var teleController = defaultTeleopController()
    private var cameras = DriverCameras.getInstance()

    private fun defaultTeleopController(): TeleopController {
        return TeleopController(Extreme3DProJoystick(0),
                LogitechGamepadController(1),
                drivetrain,
                panel,
                cargo,
                cameras,
                { SmartDashboard.getNumber("rotate multiplier", 0.5) },
                { SmartDashboard.getNumber("speed multiplier", 0.85) }
        )
    }

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    override fun robotInit() {
        drivetrain.calibrateGyro()
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    override fun robotPeriodic() {
        SmartDashboard.putNumber("gyro angle", drivetrain.currentAngle)
        SmartDashboard.putNumber("inches", drivetrain.inchesTraveled)

        SmartDashboard.putNumber("Left encoder", drivetrain.leftEncoderDistance)
        SmartDashboard.putNumber("Right encoder", drivetrain.rightEncoderDistance)
    }

    override fun disabledInit() {
        drivetrain.setCoast()
    }

    override fun autonomousInit() {
        drivetrain.setBrake()
        teleController.autoControlsInit()
    }

    /**
     * This function is called periodically during autonomous.
     */
    override fun autonomousPeriodic() {
        teleController.run()
    }

    override fun teleopInit() {
        teleController.endAuto()
        drivetrain.setBrake()
    }

    /**
     * This function is called periodically during operator control.
     */
    override fun teleopPeriodic() {
        teleController.run()
    }


    override fun testInit() {
        drivetrain.setBrake()

        panel.startingConfig()
        panel.periodic()

        cargo.idle()
        cargo.periodic()

        drivetrain.resetEncodersAndGyro()

        teleController.startCompressor()
    }

    /**
     * This function is called periodically during test mode.
     */
    override fun testPeriodic() {}
}
