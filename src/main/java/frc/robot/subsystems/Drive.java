package frc.robot.subsystems;

import java.util.Set;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveK;

public class Drive extends SubsystemBase {
    
    private TalonSRX talonFR = new TalonSRX(DriveK.talonFRID);
    private TalonSRX talonFL = new TalonSRX(DriveK.talonFLID);    
    private TalonSRX talonBR = new TalonSRX(DriveK.talonBRID);    
    private TalonSRX talonBL = new TalonSRX(DriveK.talonBLID);  
    private Set<TalonSRX> talons = Set.of(talonFR, talonFL, talonBR, talonBL);
    
    public Drive() {
        configMotors();
    }

    private void configMotors() {
        for (var talon : talons) {
            talon.configFactoryDefault();
        }
        talonFR.setInverted(true);
    }

    public void stop() {
        talons.forEach((talon) -> talon.neutralOutput());
    }

    public void setPercentOutput(double left, double right) {
        talonFL.set(TalonSRXControlMode.PercentOutput, left);
        talonBL.set(TalonSRXControlMode.PercentOutput, left);
        talonFR.set(TalonSRXControlMode.PercentOutput, right);
        talonBR.set(TalonSRXControlMode.PercentOutput, right);
    }

}
