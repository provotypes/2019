package frc.robot.operatorinterface

/**
 * Bindable Joystic Class for the Extreme 3D Pro Joystick that comes in the Kit of Parts.
 *
 * @author Brennon Brimhall <brennonbrimhall@provotypes.org
 */
class Extreme3DProJoystick(port: Int) : BindableJoystick(port) {

    /**
     * Buttons for this controller.
     */
    companion object {
        val TRIGGER = 1
        val THUMB_BUTTON = 2

        val TOP_LEFT_TOP_BUTTON = 5
        val TOP_RIGHT_TOP_BUTTON = 6
        val BOTTOM_LEFT_TOP_BUTTON = 3
        val BOTTOM_RIGHT_TOP_BUTTON = 4

        val TOP_LEFT_BASE_BUTTON = 7
        val TOP_RIGHT_BASE_BUTTON = 8
        val MIDDLE_LEFT_BASE_BUTTON = 9
        val MIDDLE_RIGHT_BASE_BUTTON = 10
        val BOTTOM_LEFT_BASE_BUTTON = 11
        val BOTTOM_RIGHT_BASE_BUTTON = 12
    }
}
