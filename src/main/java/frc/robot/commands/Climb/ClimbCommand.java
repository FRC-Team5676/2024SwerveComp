// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// Robot climbs up chain

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Climb;

public class ClimbCommand extends Command {
  /** Creates a new Climb. */
  private final Climb m_climb;
  private final CommandXboxController m_controller;

  public ClimbCommand(Climb climb, CommandXboxController controller) {
    m_climb = climb;
    m_controller = controller;

    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double throttle = m_controller.getLeftY(); // Left joystick side to side
    m_climb.climb(throttle);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
