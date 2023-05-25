package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {

    private DoubleSupplier joystickSpeed;
    private DoubleSupplier joystickTurn;
    private DoubleSupplier joystickTrim;
    private DriveSubsystem driveSubsystem;

    public DriveCommand(DoubleSupplier joystickSpeed, DoubleSupplier joystickTurn, DoubleSupplier joystickTrim, DriveSubsystem driveSubsystem) {
        this.joystickSpeed = joystickSpeed;
        this.joystickTurn = joystickTurn;
        this.joystickTrim = joystickTrim;
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        double speed = joystickSpeed.getAsDouble();
        double turn = joystickTurn.getAsDouble();
        double trim = joystickTrim.getAsDouble();

        // Deadband
        speed = processDeadband(speed);
        turn = processDeadband(turn); 
        trim = processDeadband(trim); 
        // Slew Rate Limiting and Turn Adjusting
        speed *= Drive.speedAdjustment;
        turn *= Drive.turnAdjustment;
        // trim *= Drive.trimAdjustment;
        // Constant Curvature, WPILib DifferentialDrive#curvatureDriveIK
        // turn = turn * speed + trim; 

        double powerFactor = findSpeed((speed - turn), (speed + turn));

        double leftSpeed = (speed - turn) * powerFactor;
        double rightSpeed = (speed + turn) * powerFactor;
        driveSubsystem.setPower(leftSpeed, rightSpeed);
    }

    /**
     * {@link} https://www.chiefdelphi.com/uploads/default/original/3X/b/a/ba7ccfd90bac0934e374dd4459d813cee2903942.pdf
     */
    private double processDeadband(double val) {
        double newVal = val;
        if(Math.abs(val) < Drive.joystickDeadband) {
            newVal = 0;
        }
        else {
            // Point slope form Y = M(X-X0)+Y0.
            newVal = /*M*/ (1 / (1 - Drive.joystickDeadband)) * /*X-X0*/(val + (-Math.signum(val) * Drive.joystickDeadband));
        }
        return newVal;
    }

    private double findSpeed(double left, double right){
        double p = 1;

        if(left > 1){
            p = 1/left;
        } 
        else if(right > 1){
            p = 1/right;
        }
        return p;
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }

}
