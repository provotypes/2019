package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * This class gives added functionality to the base WPILib Joystick and allows us to bind actions to buttons.  You will
 * need to call run() on each periodic to get the actions to run.
 *
 * The intended use is something like this:
 *
 * @code {
 * LogitechGamepadController operatorController = new LogitechGamepadController(0);
 * driverController.bindButton(LogitechGamepadController.A_BUTTON, () -> shooter.shoot());
 * driverController.bindButton(LogitechGamepadController.B_BUTTON, () -> intake.intake());
 * }
 *
 * Note how much cleaner this is than a bunch of if statements.
 */
public abstract class ProvotypesJoystick extends Joystick {

	private List<Pair<BooleanSupplier, Runnable>> boundFunctions = new ArrayList<>();

	/**
	 * Defer to parent constructor.
	 * @param port
	 */
	public ProvotypesJoystick(int port) {
		super(port);
	}

	/**
	 * Bind an action to a button.  Whenever the button is pressed, the action will be run.
	 * @param button
	 * @param action
	 */
	public void bindButton(int button, Runnable action) {
		bind(() -> super.getRawButton(button), action);
	}

	/**
	 * A more generic way of binding an action to a button.  While this allows someone to bind non-controller
	 * functionality to run an action, this is discouraged.
	 * @param condition
	 * @param action
	 */
	public void bind(BooleanSupplier condition, Runnable action) {
		boundFunctions.add(new Pair(condition, action));
	}

	/**
	 * Iterate through and run actions if their conditions are met.  This function must be called to run actions.
	 */
	public void run() {
		for (Pair<BooleanSupplier, Runnable> pair : boundFunctions) {
			if (pair.getKey().getAsBoolean()) {
				pair.getValue().run();
			}
		}
	}
}
