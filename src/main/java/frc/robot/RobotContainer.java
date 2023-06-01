// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;

public class RobotContainer {

	private Joystick joystick = new Joystick(0);
	private DriveSubsystem driveSubsystem = new DriveSubsystem();
	private PneumaticsSubsystem pneumaticsSubsystem = new PneumaticsSubsystem();
	
	public RobotContainer() {
		configureBindings();
		driveSubsystem.setDefaultCommand(new DriveCommand(() -> -joystick.getRawAxis(1), () -> -joystick.getRawAxis(0), driveSubsystem));
	}
	
	private void configureBindings() {
		new JoystickButton(joystick, 1).onTrue(
			pneumaticsSubsystem.runOnce(pneumaticsSubsystem::fireCannon)
		);
		new JoystickButton(joystick, 2).onTrue(
			pneumaticsSubsystem.runOnce(pneumaticsSubsystem::closeCannon)
		);
		new JoystickButton(joystick, 3).onTrue(
			pneumaticsSubsystem.runOnce(pneumaticsSubsystem::disableCompressors)
		);
		new JoystickButton(joystick, 5).onTrue(
			pneumaticsSubsystem.runOnce(pneumaticsSubsystem::enableCompressors)
		);
	}
	
}
