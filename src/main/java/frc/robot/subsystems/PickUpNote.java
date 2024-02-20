package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PickUpNote extends SubsystemBase {

  private final CANSparkMax m_noteMotor = new CANSparkMax(43, MotorType.kBrushless);

  /** Creates a new PickUpNote. */
  public PickUpNote() {
    m_noteMotor.restoreFactoryDefaults();
    m_noteMotor.setInverted(true);
    m_noteMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /*public void runintake(){
    m_noteMotor.set(1);
  }*/

  public void intake(double throttle){
    m_noteMotor.set(throttle);
  }

  public void stop(){
    m_noteMotor.set(0);
  }
}
