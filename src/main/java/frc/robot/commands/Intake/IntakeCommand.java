// Moves Arm

package frc.robot.commands.Intake;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.IntakeArm;

public class IntakeCommand extends Command {

  private final IntakeArm m_intakeArm;
  private final CommandXboxController m_controller;

  /** Driver control */
  public IntakeCommand(IntakeArm intakeArm, CommandXboxController controller) {
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
    // Rotate Arm
    double rotate = MathUtil.applyDeadband(m_controller.getRightY(), 0.05);
    m_intakeArm.rotateIntake(rotate);

    double extend = -MathUtil.applyDeadband(m_controller.getLeftY(), 0.05);
    m_intakeArm.extendIntake(extend);

    // Intake Wheels - Throttles for positive and negative signs
    double throttle = MathUtil.applyDeadband(m_controller.getLeftTriggerAxis(), 0.05)
        - MathUtil.applyDeadband(m_controller.getRightTriggerAxis(), 0.05);
    m_intakeArm.intake(throttle * 0.6);
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
