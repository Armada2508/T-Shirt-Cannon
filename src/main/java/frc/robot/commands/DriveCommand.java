package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveK;
import frc.robot.subsystems.Drive;

public class DriveCommand extends Command {

    private DoubleSupplier joystickSpeed;
    private DoubleSupplier joystickTurn;
    private Drive driveSubsystem;

    public DriveCommand(DoubleSupplier joystickSpeed, DoubleSupplier joystickTurn, Drive driveSubsystem) {
        this.joystickSpeed = joystickSpeed;
        this.joystickTurn = joystickTurn;
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        double speed = joystickSpeed.getAsDouble();
        double turn = joystickTurn.getAsDouble();

        // Deadband
        speed = processDeadband(speed);
        turn = processDeadband(turn); 
        // Speed Adjusting
        speed *= DriveK.speedAdjustment;
        turn *= DriveK.turnAdjustment;
        
        double powerFactor = findSpeed((speed - turn), (speed + turn));

        double leftSpeed = (speed - turn) * powerFactor;
        double rightSpeed = (speed + turn) * powerFactor;
        driveSubsystem.setPercentOutput(leftSpeed, rightSpeed);
    }

    /**
     * {@link} https://www.chiefdelphi.com/uploads/default/original/3X/b/a/ba7ccfd90bac0934e374dd4459d813cee2903942.pdf
     */
    private double processDeadband(double val) {
        double newVal = val;
        if (Math.abs(val) < DriveK.joystickDeadband) {
            newVal = 0;
        }
        else {
            // Point slope form Y = M(X-X0)+Y0.
            newVal = /*M*/ (1 / (1 - DriveK.joystickDeadband)) * /*X-X0*/(val + (-Math.signum(val) * DriveK.joystickDeadband));
        }
        return newVal;
    }

    private double findSpeed(double left, double right){
        double p = 1;
        if (left > 1) {
            p = 1/left;
        } 
        else if (right > 1) {
            p = 1/right;
        }
        return p;
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }

}
