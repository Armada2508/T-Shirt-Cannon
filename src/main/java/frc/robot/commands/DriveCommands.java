package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.DriveK;
import frc.robot.lib.util.DriveUtil;
import frc.robot.subsystems.Drive;

public class DriveCommands {

    public static Command drive(DoubleSupplier joystickSpeed, DoubleSupplier joystickTurn, Drive drive) {
        // SlewRateLimiter speedLimiter = new SlewRateLimiter(0.25);
        // SlewRateLimiter turnLimiter = new SlewRateLimiter(0.25);
        return Commands.run(() -> {
            double speed = joystickSpeed.getAsDouble();
            double turn = joystickTurn.getAsDouble();
            // speed = speedLimiter.calculate(speed);
            // turn = turnLimiter.calculate(turn);

            // speed = DriveUtil.processDeadband(speed, DriveK.joystickDeadband, DriveK.deadbandSmoothing);
            // turn =  DriveUtil.processDeadband(speed, DriveK.joystickDeadband, DriveK.deadbandSmoothing);
            if (Math.abs(turn) < DriveK.joystickDeadband) {
                turn = 0;
            }

            speed *= DriveK.speedAdjustment;
            turn *= DriveK.turnAdjustment;

            var speeds = DriveUtil.normalizeValues(speed - turn, speed + turn);
            double leftSpeed = speeds.getFirst();
            double rightSpeed = speeds.getSecond();
            drive.setPercentOutput(leftSpeed, rightSpeed);
        }, drive).finallyDo(drive::stop);
    }

}
