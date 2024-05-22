// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.JoysticksK;
import frc.robot.Constants.PneumaticsK;
import frc.robot.commands.DriveCommand;
import frc.robot.lib.controller.Logitech3DPro;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LinearActuator;
import frc.robot.subsystems.Pneumatics;

public class Robot extends TimedRobot {

	private final Logitech3DPro joystick = new Logitech3DPro(JoysticksK.joystickPort);
	private final Drive driveSubsystem = new Drive();
	private final Pneumatics pneumaticsSubsystem = new Pneumatics();
	private final LinearActuator actuatorSubsystem = new LinearActuator();
	
	@Override
	public void robotInit() {
		var scheduler = CommandScheduler.getInstance();
		addPeriodic(scheduler::run, kDefaultPeriod);
		addPeriodic(pneumaticsSubsystem::flashLight, PneumaticsK.lightFlashPeriod);
		driveSubsystem.setDefaultCommand(new DriveCommand(joystick::getYInverted, joystick::getZInverted, driveSubsystem));
		configureBindings();
	}
	
	private void configureBindings() {
		joystick.b1().onTrue(pneumaticsSubsystem.fireCannon());
		joystick.b2().onTrue(pneumaticsSubsystem.runOnce(pneumaticsSubsystem::closeSolenoid));
		joystick.b3().onTrue(pneumaticsSubsystem.runOnce(pneumaticsSubsystem::disableCompressors));
		joystick.b5().onTrue(pneumaticsSubsystem.runOnce(pneumaticsSubsystem::enableCompressors));
		joystick.b11().whileTrue(actuatorSubsystem.lowerCannon());
		joystick.b12().whileTrue(actuatorSubsystem.raiseCannon());
	}
	
}
