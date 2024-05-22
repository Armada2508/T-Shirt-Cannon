package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LinearActuator extends SubsystemBase {
    
	private final Relay linearActuator = new Relay(Constants.linearActuatorRelayID);

    public Command raiseCannon() {
        return startEnd(() -> linearActuator.set(Value.kForward), this::stop);
    }

    public Command lowerCannon() {
        return startEnd(() -> linearActuator.set(Value.kReverse), this::stop);
    }

    public void stop() {
        linearActuator.set(Value.kOff);
    }

}
