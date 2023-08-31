package frc.robot.lib.controller;

import java.util.HashSet;
import java.util.Set;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class SmartJoystick extends Joystick {

    private Set<Integer> boundButtons = new HashSet<>();
    
    public SmartJoystick(final int port) {
        super(port);
    }

    private void addOrThrow(int button) throws IllegalArgumentException {
        if (boundButtons.contains(button)) throw new IllegalArgumentException();
        boundButtons.add(button);
    }

    public Trigger onTrue(int button, Command command) {
        addOrThrow(button);
        return new JoystickButton(this, button).onTrue(command);
    }

    public Trigger onFalse(int button, Command command) {
        addOrThrow(button);
        return new JoystickButton(this, button).onFalse(command);
    }

    /**
     * Starts the given command when the condition changes to `true` and cancels it when the condition
     * changes to `false`.
     *
     * <p>Doesn't re-start the command if it ends while the condition is still `true`. If the command
     * should restart, see {@link edu.wpi.first.wpilibj2.command.RepeatCommand}.
     */
    public Trigger whileTrue(int button, Command command) {
        addOrThrow(button);
        return new JoystickButton(this, button).whileTrue(command);
    }

    public Trigger whileFalse(int button, Command command) {
        addOrThrow(button);
        return new JoystickButton(this, button).whileFalse(command);
    }

    public Trigger toggleOnTrue(int button, Command command) {
        addOrThrow(button);
        return new JoystickButton(this, button).toggleOnTrue(command);
    }

    public Trigger toggleOnFalse(int button, Command command) {
        addOrThrow(button);
        return new JoystickButton(this, button).toggleOnFalse(command);
    }

}
