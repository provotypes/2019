package frc.robot;

import easypath.EasyPath;
import easypath.EasyPathConfig;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotInit {
	//SendableChooser for Auto

	public static final String kDoNothing = "Do Nothing / Human Controlled";
	public static final String kCenterBayNoVision = "Center Bay No Vision";
	public static final String kRightStartCloseRightBay = "Right Start Close Right Bay";
	public static final String kRightStartMiddleRightBay = "Right Start Middle Right Bay";
	public static final String kRightStartFarRightBay = "Right Start Far Right Bay";
	public static final String kMiddleStartRightBay = "Middle Start Right Bay";
	public static final String kMiddleStartLeftBay = "Middle Start Left Bay";
	public static final String kLeftStartCloserLeftBay = "Left Start Closer Left Bay";
	public static final String kLeftStartMiddleLeftBay = "Left Start Middle Left Bay";
	public static final String kLeftStartFarLeftBay = "Left Start Far Left Bay";

	private static final SendableChooser<String> autoChooser = new SendableChooser<>();

	public static void init(DrivetrainInterface drivetrain) {

		autoChooser.setDefaultOption("Human Controlled", kDoNothing);
		autoChooser.addOption("Center Bay No Vision",  kCenterBayNoVision);
		autoChooser.addOption("Right Start Close Right Bay",  kRightStartCloseRightBay);
		autoChooser.addOption("Right Start Middle Right Bay", kRightStartMiddleRightBay);
		autoChooser.addOption("Right Start Far Right Bay",    kRightStartFarRightBay);
		autoChooser.addOption("Middle Start Right Bay",       kMiddleStartRightBay);
		autoChooser.addOption("Middle Start Left Bay",        kMiddleStartLeftBay);
		autoChooser.addOption("Left Start Closer Left Bay",   kLeftStartCloserLeftBay);
		autoChooser.addOption("Left Start Middle Left Bay",   kLeftStartMiddleLeftBay);
		autoChooser.addOption("Left Start Far Left Bay",      kLeftStartFarLeftBay);
		

		SmartDashboard.putData("Auto Choice", autoChooser);

		EasyPathConfig pathConfig = new EasyPathConfig(
				drivetrain::setLeftRightDriveSpeed,
				drivetrain::getInchesTraveled,
				drivetrain::getCurrentAngle,
				drivetrain::resetEncodersAndGyro,
				0.05
		);
		EasyPath.configure(pathConfig);
	}

	public static SendableChooser<String> getAutoChooser() {
		return autoChooser;
	}
}
