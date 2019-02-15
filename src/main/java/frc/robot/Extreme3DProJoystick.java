package frc.robot;

/**
 * Bindable Joystic Class for the Extreme 3D Pro Joystick that comes in the Kit of Parts.
 */
public class Extreme3DProJoystick extends BindableJoystick {

	// Buttons
	public static final int TRIGGER = 1;
	public static final int THUMB_BUTTON = 2;

	public static final int TOP_LEFT_TOP_BUTTON = 5;
	public static final int TOP_RIGHT_TOP_BUTTON = 6;
	public static final int BOTTOM_LEFT_TOP_BUTTON = 3;
	public static final int BOTTOM_RIGHT_TOP_BUTTON = 4;

	public static final int TOP_LEFT_BASE_BUTTON = 7;
	public static final int TOP_RIGHT_BASE_BUTTON = 8;
	public static final int MIDDLE_LEFT_BASE_BUTTON = 9;
	public static final int MIDDLE_RIGHT_BASE_BUTTON = 10;
	public static final int BOTTOM_LEFT_BASE_BUTTON = 11;
	public static final int BOTTOM_RIGHT_BASE_BUTTON = 12;

	public Extreme3DProJoystick(int port) {
		super(port);
	}
}
