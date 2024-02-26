// Blue wheels; "The Cannon"

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Shooter extends SubsystemBase {

  private final CANSparkMax uppershooterMotor = new CANSparkMax(62, MotorType.kBrushless);
  private final CANSparkMax lowershooterMotor = new CANSparkMax(59, MotorType.kBrushless);

  private static boolean m_isOn = false;
  private static boolean m_isOnBackwards = false;

  public Shooter() {

    uppershooterMotor.restoreFactoryDefaults();
    lowershooterMotor.restoreFactoryDefaults();

    uppershooterMotor.setInverted(true);

    uppershooterMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    lowershooterMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    lowershooterMotor.follow(lowershooterMotor, true);
  }

  @Override
  public void periodic() {
    if (m_isOn) {
      uppershooterMotor.set(0.55);
    } else if (m_isOnBackwards) {
      uppershooterMotor.set(-0.1);
    } else {
      uppershooterMotor.set(0);
    }
  }

  public void runWheels() {
    m_isOnBackwards = false;
    m_isOn = !m_isOn;
  }

  public void runWheelsBackwards() {
    m_isOn = false;
    m_isOnBackwards = !m_isOnBackwards;
  }
}
