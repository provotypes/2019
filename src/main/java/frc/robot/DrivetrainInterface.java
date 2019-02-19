package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import easypath.EasyPathDriveTrain;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public interface DrivetrainInterface extends EasyPathDriveTrain {

	static DrivetrainInterface getDefaultDrivetrain(){
		CANSparkMax front_left = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
		CANSparkMax rear_left = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
		CANSparkMax front_right = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
		CANSparkMax rear_right = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);

		return new Drivetrain(front_left, 
							  rear_left,
							  front_right,
							  rear_right,
							  new SpeedControllerGroup(front_left, rear_left),
							  new SpeedControllerGroup(front_right, rear_right),
							  new ADXRS450_Gyro()
		);
	}

	/**
	 * @param left  power to which left motors will be set
	 * @param right power to which right motors will be set
	 */
	void setArcadeDriveSpeed(double speed, double turn);

	double getLeftEncoderDistance();

	double getRightEncoderDistance();

	void setBrake();

	void setCoast();

	void calibrateGyro();
}