package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LinearActuatorSubsystem extends SubsystemBase {
    
	private final Relay linearActuator = new Relay(Constants.linearActuatorRelayID);

    public LinearActuatorSubsystem() {
        stopActuator();
    }

    public Command raiseCannon() {
        return startEnd(() -> linearActuator.set(Value.kForward), this::stopActuator);
    }

    public Command lowerCannon() {
        return startEnd(() -> linearActuator.set(Value.kReverse), this::stopActuator);
    }

    public void stopActuator() {
        linearActuator.set(Value.kOff);
    }

}
