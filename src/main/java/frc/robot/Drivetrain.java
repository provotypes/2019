package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends DifferentialDrive implements DrivetrainInterface {
	public static final double DISTANCE_PER_ROTATION = 1.0d/8.45d * 6.0d * Math.PI; // inches
	public static final double RAMP_PERIOD = 0.40;

	CANSparkMax front_left;
	CANSparkMax rear_left;
	CANSparkMax front_right;
	CANSparkMax rear_right;

	SpeedControllerGroup left_a;
	SpeedControllerGroup right_a;

	ADXRS450_Gyro gyro;
	CANEncoder encoderFrontLeft, encoderRearLeft, encoderFrontRight, encoderRearRight;

	public Drivetrain(CANSparkMax front_l,
					  CANSparkMax rear_l,
					  CANSparkMax front_r, 
					  CANSparkMax rear_r,
					  SpeedControllerGroup left,
					  SpeedControllerGroup right,
					  ADXRS450_Gyro gyro
					){
		super(left, right);

		this.front_left = front_l;
		this.rear_left = rear_l;
		this.front_right = front_r;
		this.rear_right = rear_r;

		front_left.setIdleMode(IdleMode.kCoast);
		rear_left.setIdleMode(IdleMode.kBrake);
		front_right.setIdleMode(IdleMode.kCoast);
		rear_right.setIdleMode(IdleMode.kBrake);

		front_left.setOpenLoopRampRate(RAMP_PERIOD);
		rear_left.setOpenLoopRampRate(RAMP_PERIOD);
		front_right.setOpenLoopRampRate(RAMP_PERIOD);
		rear_right.setOpenLoopRampRate(RAMP_PERIOD);

		this.left_a = left;
		this.right_a = right;

		left_a.setInverted(true);
		right_a.setInverted(true);

		this.gyro = gyro;

		this.encoderFrontLeft = front_left.getEncoder();
		this.encoderRearLeft = rear_left.getEncoder();
		this.encoderFrontRight = front_right.getEncoder();
		this.encoderRearRight = rear_right.getEncoder();
	}

	@Override
	public void setLeftRightDriveSpeed(double left, double right) {
		tankDrive(left, right);
	}

	@Override
	public void setArcadeDriveSpeed(double speed, double turn) {
		arcadeDrive(speed, turn, true);
	}

	@Override
	public double getInchesTraveled() {
		// average
		return ((getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0d);
	}

	@Override
	public double getCurrentAngle() {
		return gyro.getAngle();
	}

	@Override
	public void resetEncodersAndGyro() {
		encoderFrontLeft.setPosition(0.0d);
		encoderRearLeft.setPosition(0.0d);
		encoderFrontRight.setPosition(0.0d);
		encoderRearRight.setPosition(0.0d);
		gyro.reset();
	}

	@Override
	public void setBrake() {
		front_left.setIdleMode(IdleMode.kCoast);
		rear_left.setIdleMode(IdleMode.kBrake);
		front_right.setIdleMode(IdleMode.kCoast);
		rear_right.setIdleMode(IdleMode.kBrake);
	}

	@Override
	public void setCoast() {
		front_left.setIdleMode(IdleMode.kCoast);
		rear_left.setIdleMode(IdleMode.kCoast);
		front_right.setIdleMode(IdleMode.kCoast);
		rear_right.setIdleMode(IdleMode.kCoast);
	}

	public double getLeftEncoderDistance() {
		return (((encoderFrontLeft.getPosition() + encoderRearLeft.getPosition()) / 2.0d) * -DISTANCE_PER_ROTATION);
	}

	public double getRightEncoderDistance() {
		return (((encoderFrontRight.getPosition() + encoderRearRight.getPosition()) / 2.0d) * DISTANCE_PER_ROTATION);
	}

	@Override
	public void calibrateGyro() {
		gyro.calibrate();
	}

}