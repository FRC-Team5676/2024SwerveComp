// Arm

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.RotateIntakeArm;


public class RotateIntakeCommand extends Command {

  private final RotateIntakeArm m_controlArm;
  private final CommandXboxController m_controller;

  /** Driver control */
  public RotateIntakeCommand(RotateIntakeArm controlArm, CommandXboxController controller) {
      m_controlArm = controlArm;
      m_controller = controller;

      addRequirements(controlArm);
  }


 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double throttle = m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis(); // Throttles for positive and negative signs
    m_controlArm.rotateIntake(throttle * 0.30);
    
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
