package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeWheels extends SubsystemBase {

  private final CANSparkMax m_intakeMotor = new CANSparkMax(61, MotorType.kBrushless);

  /** Creates a new PickUpNote. */
  public IntakeWheels() {
    m_intakeMotor.restoreFactoryDefaults();
    m_intakeMotor.setInverted(true);
    m_intakeMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  public void intake(double throttle){
    if (Math.abs(throttle) > 0.05) { // Stops drift on green wheels
      m_intakeMotor.set(throttle);
    }
    else {
      m_intakeMotor.set(0);
    }
  }
}
