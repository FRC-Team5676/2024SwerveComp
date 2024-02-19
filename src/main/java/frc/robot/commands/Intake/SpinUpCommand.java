package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SpinUp;

public class SpinUpCommand extends Command {

  private final SpinUp m_spinupwheels;

  private boolean isOn = false;

  /** Driver control */
  public SpinUpCommand(SpinUp spinwheels) {
    m_spinupwheels = spinwheels;

    addRequirements(m_spinupwheels);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (isOn) {
      m_spinupwheels.SpinUpWheels();
    }
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

  public void TurnOnTurnOff() {
    isOn = !isOn;
  }
}
