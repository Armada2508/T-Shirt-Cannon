package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Pneumatics;

public class PneumaticsSubsystem extends SubsystemBase {

    private Compressor compressorL = new Compressor(Pneumatics.compressorLID, PneumaticsModuleType.CTREPCM);
    private Compressor compressorR = new Compressor(Pneumatics.compressorRID, PneumaticsModuleType.CTREPCM);
    private Solenoid solenoid = new Solenoid(Pneumatics.compressorRID, PneumaticsModuleType.CTREPCM, Pneumatics.solenoidID);

    public PneumaticsSubsystem() {
        disableCompressors();
        closeCannon();
    }

    public void enableCompressors() {
        compressorL.enableDigital();
        compressorR.enableDigital();
    }

    public void disableCompressors() {
        compressorL.disable();
        compressorR.disable();
    }

    public void fireCannon() {
        solenoid.set(true);
    }

    public void closeCannon() {
        solenoid.set(false);
    }

}