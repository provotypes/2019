package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//------------------------------------------------------------------------------------------

public class Drivetrain extends DifferentialDrive implements DrivetrainInterface {

                                                        // there are like a million MotorType objects
    static CANSparkMax front_left = new CANSparkMax(1 , CANSparkMaxLowLevel.MotorType.kBrushless);
    static CANSparkMax rear_left = new CANSparkMax(2 , CANSparkMaxLowLevel.MotorType.kBrushless);

    static SpeedControllerGroup left_a = new SpeedControllerGroup(front_left , rear_left);

    static CANSparkMax front_right = new CANSparkMax(3 , CANSparkMaxLowLevel.MotorType.kBrushless);
    static CANSparkMax rear_right = new CANSparkMax(4 , CANSparkMaxLowLevel.MotorType.kBrushless);

    static SpeedControllerGroup right_a = new SpeedControllerGroup(front_right , rear_right);

    ADXRS450_Gyro gyro;
    
    Encoder encoderLeft, encoderRight;

    public static final double TICKS_PER_INCH = 2048.0d / (6.0d * Math.PI); //FIXME calibrate this
    public static final double DISTANCE_PER_PULSE = 1.0d / TICKS_PER_INCH;

    public Drivetrain() {
        super(left_a , right_a);

        gyro = new ADXRS450_Gyro();

        encoderLeft = new Encoder(2, 3);
        encoderRight = new Encoder(4, 5);

        encoderLeft.setDistancePerPulse(DISTANCE_PER_PULSE);
        encoderRight.setDistancePerPulse(DISTANCE_PER_PULSE);
    }

    @Override
    public void setLeftRightDriveSpeed(double left, double right){
        tankDrive(left, right);
    }

    @Override
    public void setArcadeDriveSpeed(double speed, double turn){
        arcadeDrive(speed, turn);
    }

    @Override
    public double getInchesTraveled(){
        // average
        return ((getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0d);
    }

    @Override
    public double getCurrentAngle(){
        return gyro.getAngle();
    }

    @Override
    public void resetEncodersAndGyro(){
        encoderLeft.reset();
        encoderRight.reset();
        gyro.reset();
    }

    public double getLeftEncoderDistance() {
		return encoderLeft.getDistance();
	}

	public double getRightEncoderDistance() {
		return encoderRight.getDistance();
    }

}