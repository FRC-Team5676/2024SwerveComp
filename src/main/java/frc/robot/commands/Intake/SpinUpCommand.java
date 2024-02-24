// Blue wheels; "The Cannon"

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.SpinUp;

public class SpinUpCommand extends Command {

  private final SpinUp m_spinupwheels;
  private final CommandXboxController m_controller;

  //private boolean ison = false;

  /** Driver control */
  public SpinUpCommand(SpinUp spinwheels, CommandXboxController controller) {
    m_spinupwheels = spinwheels;
    m_controller = controller;

    addRequirements(spinwheels);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double throttle = m_controller.getLeftY(); // Left joystick
    m_spinupwheels.SpinUpWheels(throttle * 0.55);
  }

  /*public void TurnOnTurnOff(boolean ison) {
    ison = !ison;
  }*/

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
