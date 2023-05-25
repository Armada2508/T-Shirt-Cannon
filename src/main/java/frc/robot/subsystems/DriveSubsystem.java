package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drive;

public class DriveSubsystem extends SubsystemBase {
    
    private TalonSRX talonFR = new TalonSRX(Drive.talonFRID);
    private TalonSRX talonFL = new TalonSRX(Drive.talonFLID);    
    private TalonSRX talonBR = new TalonSRX(Drive.talonBRID);    
    private TalonSRX talonBL = new TalonSRX(Drive.talonBLID);  
    private List<TalonSRX> talons = new ArrayList<>();
    
    public DriveSubsystem() {
        talons.add(talonFR);
        talons.add(talonFL);
        talons.add(talonBR);
        talons.add(talonBL);
        configMotors();
    }

    private void configMotors() {
        stop();
        for (TalonSRX talon : talons) {
            talon.configFactoryDefault();
            talon.setNeutralMode(NeutralMode.Brake);
        }
    }

    public void stop() {
        talons.forEach((t) -> t.neutralOutput());
    }

    public void setPower(double powerLeft, double powerRight) {
        talonFL.set(TalonSRXControlMode.PercentOutput, powerLeft);
        talonBL.set(TalonSRXControlMode.PercentOutput, powerLeft);
        talonFR.set(TalonSRXControlMode.PercentOutput, powerRight);
        talonBR.set(TalonSRXControlMode.PercentOutput, powerRight);
    }

    public void setVoltage(double voltsL, double voltsR) {}

    public void setVelocity(double velocityL, double velocityR) {}

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return null;
    }

    public Pose2d getPose() {
        return null;
    }

}
