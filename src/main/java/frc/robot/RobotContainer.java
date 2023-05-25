// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;

public class RobotContainer {

	private Joystick joystick = new Joystick(0);
	private DriveSubsystem driveSubsystem = new DriveSubsystem();
	
	public RobotContainer() {
		configureBindings();
		driveSubsystem.setDefaultCommand(new DriveCommand(() -> joystick.getRawAxis(0), () -> joystick.getRawAxis(2), () -> joystick.getRawAxis(1), driveSubsystem));
	}
	
	private void configureBindings() {}
	
}
