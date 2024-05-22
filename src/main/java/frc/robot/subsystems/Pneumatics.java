package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PneumaticsK;
import frc.robot.Robot;
import frc.robot.lib.pneumatics.CurrentLimitedCompressor;

public class Pneumatics extends SubsystemBase {

    private CurrentLimitedCompressor compressorL = new CurrentLimitedCompressor(PneumaticsK.compressorLID, PneumaticsModuleType.CTREPCM, PneumaticsK.maxAmps, PneumaticsK.maxCurrentTimeSeconds);
    private CurrentLimitedCompressor compressorR = new CurrentLimitedCompressor(PneumaticsK.compressorRID, PneumaticsModuleType.CTREPCM, PneumaticsK.maxAmps, PneumaticsK.maxCurrentTimeSeconds);
    private Solenoid solenoid = new Solenoid(PneumaticsK.compressorLID, PneumaticsModuleType.CTREPCM, PneumaticsK.solenoidID);
    private Relay light = new Relay(PneumaticsK.lightRelayID, Direction.kForward);

    public Pneumatics() {
        solenoid.set(false);
    }

    @Override
    public void periodic() {
        compressorL.check(Robot.kDefaultPeriod);
        compressorR.check(Robot.kDefaultPeriod);
        if (!compressorL.getPressureSwitchValue()) {
            light.set(Value.kOff);
        }
    }

    public Command enableCompressors() {
        return Commands.runOnce(() -> { // Don't want to require anything
            compressorL.enableDigital();
            compressorR.enableDigital();
        });
    }

    public Command disableCompressors() {
        return Commands.runOnce(() -> { // Don't want to require anything
            compressorL.disable();
            compressorR.disable();
        });
    }

    public Command openSolenoid() {
        return runOnce(() -> solenoid.set(true));
    }

    public Command closeSolenoid() {
        return runOnce(() -> solenoid.set(false));
    }

    public Command fireCannon() {
        return Commands.sequence(
            disableCompressors(),
            openSolenoid(),
            Commands.waitSeconds(PneumaticsK.timeToFire),
            closeSolenoid()
        );
    }

    public void flashLight() {
        if (compressorL.getPressureSwitchValue()) {
            if (light.get() == Value.kOn) {
                light.set(Value.kOff);
            }
            else {
                light.set(Value.kOn);
            }
        } 
    }

}
