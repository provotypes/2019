package frc.robot.operatorinterface

import edu.wpi.first.wpilibj.Joystick

/**
 * This class gives added functionality to the base WPILib Joystick and allows us to bind actions to buttons and axes.
 * You will need to call `run()` on each `periodic()` to get the actions to run.
 *
 * @author Brennon Brimhall <brennonbrimhall@provotypes.org
 * @param port The controller identifier on the Driver Station.
 */
abstract class BindableJoystick(port: Int) : Joystick(port) {

    private val boundActions = mutableListOf<BindableAction>()

    /**
     * @param condition This supplier must answer the question "should we run this action?"  Ideally, this is a call
     * to a method on the joystick.
     * @param action    What action should I call when the condition is true?"  This probably should be an instance
     * method of a subsystem.
     */
    internal inner class BindableAction(val condition: () -> Boolean, val action: () -> Unit)

    /**
     * Bind an action to a button.  Whenever the button is held, the action will be run.
     * @param button The button number for the Joystick in use.
     * @param action An action (function) to execute when and while the button is pressed (whenever
     * `getRawButton()` is true).
     */
    fun bindButton(button: Int, action: () -> Unit) {
        bind({ super.getRawButton(button) }, action)
    }

    /**
     * Bind an action to a button press.  Whenever the button is pressed, the action will be run.
     * This is diffrent from `bindButton()` in that it only runs once when the button is pressed,
     * not continually.  See `bindButtonRelease()`.
     * @param button The button number for the Joystick in use.
     * @param action An action (function) to execute when the button is pressed (whenever
     * `getRawButtonPressed()` is true).
     */
    fun bindButtonPress(button: Int, action: () -> Unit) {
        bind({ super.getRawButtonPressed(button) }, action)
    }

    /**
     * Bind an action to a button release.  Whenever the button is released, the action will be run.
     * This is different from the `bindButton()` in that it only runs once when the button is pressed,
     * not continually.  See `bindButtonPress()`.
     * @param button
     * @param action
     */
    fun bindButtonRelease(button: Int, action: () -> Unit) {
        bind({ super.getRawButtonReleased(button) }, action)
    }

    /**
     * Bind two actions to a button press and release; typically used for toggling.  This will run only once
     * when pressed and only once when released, not continually.
     * @param button
     * @param actionOnPress
     * @param actionOnRelease
     */
    fun bindButtonToggle(button: Int, actionOnPress: () -> Unit, actionOnRelease: () -> Unit) {
        bindButtonPress(button, actionOnPress)
        bindButtonRelease(button, actionOnRelease)
    }

    /**
     * Bind two axes to an action that requires two doubles.  Main use case here is for driving.  This action will
     * always be run when `run()` is called.  Be sure that you correctly order axis1 and axis2.
     *
     * This action will always be run when run() is called.
     * @param axis1 The first double for the action comes from this axis.
     * @param axis2 The second double for the action comes from this axis.
     * @param action The action to run.  Must take in only two doubles.
     */
    fun bindAxes(axis1: Int, axis2: Int, action: (Double, Double) -> Unit) {
        bind({ action.invoke(super.getRawAxis(axis1), super.getRawAxis(axis2)) })
    }

    /**
     * Bind an axis to an action that requires a double.  This action will always be run when `run()` is called.
     * @param axis The double for the action comes from this axis.
     * @param action The action to run.  Must take in only one double.
     */
    fun bindAxis(axis: Int, action: (Double) -> Unit) {
        bind({ action.invoke(super.getRawAxis(axis)) })
    }

    /**
     * Bind an action to the joystick which will always occur when `run()` is called.  While this allows someone
     * to bind non-controller functionality to run an arbitrary action, this is discouraged.
     * @param action An action to run each time the joystick is called.
     */
    fun bind(action: () -> Unit) {
        boundActions.add(BindableAction({ true }, action))
    }

    /**
     * Bind an action to the joystick which will run when the condition is satisfied.  While this allows someone to bind
     * non-controller functionality to run an arbitrary action, this is discouraged.
     * @param condition A generic condition upon which the action is conditioned.
     * @param action A function that is executed when the condition is true.
     */
    fun bind(condition: () -> Boolean, action: () -> Unit) {
        boundActions.add(BindableAction(condition, action))
    }

    /**
     * Iterate through and run actions if their conditions are met.  This function must be called to run actions.
     */
    fun run() {
        for (bindableAction in boundActions) {
            if (bindableAction.condition.invoke()) {
                bindableAction.action.invoke()
            }
        }
    }

    /**
     * Clears all actions from the list of bound actions.
     */
    fun clear() {
        boundActions.clear()
    }
}
