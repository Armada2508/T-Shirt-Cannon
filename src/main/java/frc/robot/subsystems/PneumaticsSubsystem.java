package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Pneumatics;
import frc.robot.lib.pneumatics.CurrentLimitedCompressor;

public class PneumaticsSubsystem extends SubsystemBase {

    private CurrentLimitedCompressor compressorL = new CurrentLimitedCompressor(Pneumatics.compressorLID, PneumaticsModuleType.CTREPCM, Pneumatics.maxAmps, Pneumatics.maxCurrentTimeSeconds);
    private CurrentLimitedCompressor compressorR = new CurrentLimitedCompressor(Pneumatics.compressorRID, PneumaticsModuleType.CTREPCM, Pneumatics.maxAmps, Pneumatics.maxCurrentTimeSeconds);
    private Solenoid solenoid = new Solenoid(Pneumatics.compressorLID, PneumaticsModuleType.CTREPCM, Pneumatics.solenoidID);

    public PneumaticsSubsystem() {
        disableCompressors();
        closeSolenoid();
    }

    @Override
    public void periodic() {
        compressorL.check(0.05);
        compressorR.check(0.05);
    }

    public void enableCompressors() {
        compressorL.enableDigital();
        compressorR.enableDigital();
    }

    public void disableCompressors() {
        compressorL.disable();
        compressorR.disable();
    }

    public void openSolenoid() {
        solenoid.set(true);
    }

    public void closeSolenoid() {
        solenoid.set(false);
    }

    public Command fireCannon() {
        return new SequentialCommandGroup(
            runOnce(this::disableCompressors),
            runOnce(this::openSolenoid),
            Commands.waitSeconds(0.75),
            runOnce(this::closeSolenoid)
        );
    }

}