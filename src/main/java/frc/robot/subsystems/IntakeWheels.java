package frc.robot.subsystems;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeWheels extends SubsystemBase {

  public boolean m_noteDetected = false;

  private final CANSparkFlex m_intakeMotor = new CANSparkFlex(61, MotorType.kBrushless);
  private AnalogInput m_noteSensor = new AnalogInput(0);
  private boolean m_noteLoaded = false;
  private boolean m_noteReadyToShoot = false;

  public IntakeWheels() {
    m_intakeMotor.restoreFactoryDefaults();
    m_intakeMotor.setInverted(true);
    m_intakeMotor.setIdleMode(CANSparkFlex.IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    if (m_noteSensor.getValue() > 2000)
      m_noteDetected = true;
    else
      m_noteDetected = false;
  }

  public void intake(double throttle) {
    if (!m_noteDetected && !m_noteLoaded || m_noteReadyToShoot) {
      if (Math.abs(throttle) > 0.05) { // Stops drift on green wheels
        m_intakeMotor.set(throttle);
      } else {
        m_intakeMotor.set(0);
        m_noteLoaded = false;
      }
    } else if (m_noteDetected) {
      m_intakeMotor.set(-0.1);
      m_noteLoaded = true;
    } else if (m_noteLoaded) {
      m_intakeMotor.set(0);
    }

    if (m_noteLoaded && Math.abs(throttle) <= 0.05) {
       m_noteReadyToShoot = true;
    }
  }
}
