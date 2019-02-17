package frc.robot;

import easypath.EasyPath;
import easypath.EasyPathConfig;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotInit {
	//SendableChooser for Auto
	//TODO: populate with auto options
	public static final String kDefaultAuto = "Default";
	public static final String kCustomAuto = "Custom Auto";
	private static final SendableChooser<String> autoChooser = new SendableChooser<>();

	public static void init(DrivetrainInterface drivetrain) {

		autoChooser.setDefaultOption("Default Auto", kDefaultAuto);
		autoChooser.addOption("My Auto", kCustomAuto);
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

