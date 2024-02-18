package frc.robot.commands.Intake;


import java.lang.ModuleLayer.Controller;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.controllers.xbox;
import frc.robot.subsystems.SpinUp;


public class SpinUpCommand extends Command {

  private final SpinUp m_spinupwheels;
  private final xbox m_controller;
  

  /** Driver control */
  public SpinUpCommand(SpinUp spinwheels, xbox controller) {
      m_spinupwheels = spinwheels;
      m_controller = controller;

      addRequirements(m_spinupwheels);
  }


 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  

    
    m_spinupwheels.SpinUpWheels(m_controller.buttonA);
    
  }
  
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
