package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain extends DifferentialDrive implements DrivetrainInterface {
	public static final double TICKS_PER_INCH = 2048.0d / (6.0d * Math.PI); //FIXME calibrate this
	public static final double DISTANCE_PER_PULSE = 1.0d / TICKS_PER_INCH;
	public static final double RAMP_PERIOD = 0.5;

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

		this.left_a = left;
		this.right_a = right;

		front_left.setRampRate(RAMP_PERIOD);
		rear_left.setRampRate(RAMP_PERIOD);

		front_right.setRampRate(RAMP_PERIOD);
		rear_right.setRampRate(RAMP_PERIOD);

		this.left_a.setInverted(true);
		this.right_a.setInverted(true);

		this.gyro = gyro;

		this.encoderFrontLeft = front_left.getEncoder();
		this.encoderRearLeft = rear_left.getEncoder();
		this.encoderFrontRight = front_right.getEncoder();
		this.encoderRearRight = rear_right.getEncoder();

		encoderFrontLeft.setPositionConversionFactor(DISTANCE_PER_PULSE);
		encoderRearLeft.setPositionConversionFactor(DISTANCE_PER_PULSE);
		encoderFrontRight.setPositionConversionFactor(DISTANCE_PER_PULSE);
		encoderRearRight.setPositionConversionFactor(DISTANCE_PER_PULSE);
	}

	@Override
	public void setLeftRightDriveSpeed(double left, double right) {
		tankDrive(left, right);
	}

	@Override
	public void setArcadeDriveSpeed(double speed, double turn) {
		arcadeDrive(speed, turn);
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
		encoderRight.reset();
		gyro.reset();
	}

	public double getLeftEncoderDistance() {
		return encoderLeft.getDistance();
	}

	public double getRightEncoderDistance() {
		return encoderRight.getDistance();
	}

	@Override
	public void calibrateGyro() {
		gyro.calibrate();
	}

}