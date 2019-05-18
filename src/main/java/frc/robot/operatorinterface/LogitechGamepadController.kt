package frc.robot.operatorinterface

/**
 * Describes a Logitech Gamepad Controller.  This type of controller is used
 * for driver control, and it simplifies and abstracts the raw joystick.
 *
 * @author Brennon Brimhall <brennonbrimhall@provotypes.org
 */
class LogitechGamepadController(pos: Int) : BindableJoystick(pos) {

    val aButton: Boolean
        get() = super.getRawButton(A_BUTTON)

    val bButton: Boolean
        get() = super.getRawButton(B_BUTTON)

    val xButton: Boolean
        get() = super.getRawButton(X_BUTTON)

    val yButton: Boolean
        get() = super.getRawButton(Y_BUTTON)

    val leftBumper: Boolean
        get() = super.getRawButton(LEFT_BUMPER)

    val rightBumper: Boolean
        get() = super.getRawButton(RIGHT_BUMPER)

    val start: Boolean
        get() = super.getRawButton(START_BUTTON)

    val leftX: Double
        get() = super.getRawAxis(LEFT_X_AXIS)

    val leftY: Double
        get() = super.getRawAxis(LEFT_Y_AXIS)

    val leftTrigger: Double
        get() = super.getRawAxis(LEFT_TRIGGER)

    val rightX: Double
        get() = super.getRawAxis(RIGHT_X_AXIS)

    val rightY: Double
        get() = super.getRawAxis(RIGHT_Y_AXIS)

    val rightTrigger: Double
        get() = super.getRawAxis(RIGHT_TRIGGER)

    /**
     * Buttons and axes for this controller.
     */
    companion object {

        //Buttons
        val A_BUTTON = 1
        val B_BUTTON = 2
        val X_BUTTON = 3
        val Y_BUTTON = 4
        val LEFT_BUMPER = 5
        val RIGHT_BUMPER = 6
        val START_BUTTON = 8
        val LEFT_STICK_IN = 9

        //Axes
        val LEFT_X_AXIS = 0
        val LEFT_Y_AXIS = 1
        val LEFT_TRIGGER = 2
        val RIGHT_X_AXIS = 4
        val RIGHT_Y_AXIS = 5
        val RIGHT_TRIGGER = 3
    }
}
