// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.wpilibj2.command.Commands.runOnce;
import static edu.wpi.first.wpilibj2.command.Commands.sequence;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.Constants.JoysticksK;
import frc.robot.Constants.PneumaticsK;
import frc.robot.commands.DriveCommands;
import frc.robot.lib.controller.Logitech3DPro;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LinearActuator;
import frc.robot.subsystems.Pneumatics;

public class Robot extends TimedRobot {

    private final Logitech3DPro joystick = new Logitech3DPro(JoysticksK.joystickPort);
    private final Drive drive = new Drive();
    private final Pneumatics pneumatics = new Pneumatics();
    private final LinearActuator linearActuator = new LinearActuator();

    public Robot() {
        DriverStation.silenceJoystickConnectionWarning(true);
        addPeriodic(() -> CommandScheduler.getInstance().run(), kDefaultPeriod);
        addPeriodic(pneumatics::flashLight, PneumaticsK.lightFlashPeriod.in(Seconds)); //* Don't need to call .in once 2025
        drive.setDefaultCommand(DriveCommands.drive(joystick::getYInverted, joystick::getZInverted, drive));
        configureBindings();
    }

    private void configureBindings() {
        joystick.b1().onTrue(pneumatics.fireCannon());
        joystick.b2().onTrue(pneumatics.closeSolenoid());
        joystick.b3().onTrue(pneumatics.disableCompressors());
        joystick.b5().onTrue(pneumatics.enableCompressors());
        joystick.b11().whileTrue(linearActuator.lowerCannon());
        joystick.b12().whileTrue(linearActuator.raiseCannon());
        RobotModeTriggers.disabled().onTrue(sequence(
            runOnce(() -> CommandScheduler.getInstance().cancelAll()),
            runOnce(drive::stop),
            runOnce(linearActuator::stop)
        ));
    }

}
