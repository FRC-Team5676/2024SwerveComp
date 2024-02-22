package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PickUpNote extends SubsystemBase {

  private final CANSparkMax m_noteMotor = new CANSparkMax(61, MotorType.kBrushless);

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


  public void intake(double throttle){
    if (Math.abs(throttle) > 0.05) { // Stops drift on green wheels
      m_noteMotor.set(throttle);
    }
    else {
      m_noteMotor.set(0);
    }
  }
}
