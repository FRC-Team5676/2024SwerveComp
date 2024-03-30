// Blue wheels; "The Cannon"

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.IntakeArm;

public class ShooterCommand extends Command {

  private final IntakeArm m_intakeArm;
  private final CommandXboxController m_controller;

  private boolean m_isOn = false;
  private boolean m_isOnBackwards = false;

  /** Driver control */
  public ShooterCommand(IntakeArm intakeArm, CommandXboxController controller) {
    m_intakeArm = intakeArm;
    m_controller = controller;

    addRequirements(intakeArm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_controller.rightBumper().getAsBoolean()) {
      m_isOn = !m_isOn;
      m_intakeArm.runWheelsFast();
    } else if (m_controller.leftBumper().getAsBoolean()) {
      m_isOnBackwards = !m_isOnBackwards;
      m_intakeArm.runWheelsBackwards();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
