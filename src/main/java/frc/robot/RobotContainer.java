// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DriveCommand;
import frc.robot.lib.controller.SmartJoystick;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LinearActuatorSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;

public class RobotContainer {

	private final SmartJoystick joystick = new SmartJoystick(0);
	private final DriveSubsystem driveSubsystem = new DriveSubsystem();
	private final PneumaticsSubsystem pneumaticsSubsystem = new PneumaticsSubsystem();
	private final LinearActuatorSubsystem actuatorSubsystem = new LinearActuatorSubsystem();
	
	public RobotContainer() {
		configureBindings();
		driveSubsystem.setDefaultCommand(new DriveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), driveSubsystem));
	}
	

	private void configureBindings() {
		joystick.onTrue(1, pneumaticsSubsystem.fireCannon());
		joystick.onTrue(2, pneumaticsSubsystem.runOnce(pneumaticsSubsystem::closeSolenoid));
		joystick.onTrue(3, pneumaticsSubsystem.runOnce(pneumaticsSubsystem::disableCompressors));
		joystick.onTrue(5, pneumaticsSubsystem.runOnce(pneumaticsSubsystem::enableCompressors));
		joystick.whileTrue(11, actuatorSubsystem.lowerCannon());
		joystick.whileTrue(12, actuatorSubsystem.raiseCannon());
	}
	
}
