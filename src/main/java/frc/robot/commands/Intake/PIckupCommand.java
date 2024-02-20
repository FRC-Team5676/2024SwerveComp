// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.PickUpNote;

public class PickupCommand extends Command {
  /** Creates a new PIckupCommand. */
  private final PickUpNote m_noteMotor;
  private final CommandXboxController m_controller;

  public PickupCommand(PickUpNote noteMotor, CommandXboxController controller) {
    m_noteMotor = noteMotor;
    m_controller = controller;

    addRequirements(noteMotor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double throttle = m_controller.getRightY();
    m_noteMotor.intake(throttle);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
