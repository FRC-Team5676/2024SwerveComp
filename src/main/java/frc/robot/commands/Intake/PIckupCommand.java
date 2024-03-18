// Green wheels; the intake

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.IntakeWheels;

public class PickupCommand extends Command {

    /** Creates a new PickupCommand. */
    private final IntakeWheels m_noteMotor;
    private final CommandXboxController m_controller;

  public PickupCommand(IntakeWheels noteMotor, CommandXboxController controller) {
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

      double throttle = m_controller.getLeftTriggerAxis() - m_controller.getRightTriggerAxis(); // Throttles for positive and negative signs
        m_noteMotor.intake(throttle * 0.6);

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
