// Blue wheels; "The Cannon"

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterWheels extends SubsystemBase {

  private final double shootSpeedForward = 0.55;
  private final double shootSpeedBackwards = -0.1;

  private final CANSparkFlex uppershooterMotor = new CANSparkFlex(62, MotorType.kBrushless);
  private final CANSparkFlex lowershooterMotor = new CANSparkFlex(59, MotorType.kBrushless);

  private static boolean m_isOn = false;
  private static boolean m_isOnBackwards = false;

  public ShooterWheels() {

    uppershooterMotor.restoreFactoryDefaults();
    lowershooterMotor.restoreFactoryDefaults();

    uppershooterMotor.setIdleMode(CANSparkFlex.IdleMode.kBrake);
    lowershooterMotor.setIdleMode(CANSparkFlex.IdleMode.kBrake);

    uppershooterMotor.setInverted(false);

    lowershooterMotor.follow(uppershooterMotor, false);
  }

  @Override
  public void periodic() {
    if (m_isOn) {
      uppershooterMotor.set(shootSpeedForward);
    } else if (m_isOnBackwards) {
      uppershooterMotor.set(shootSpeedBackwards);
    } else {
      uppershooterMotor.set(0);
    }
  }

  public void runWheels() {
    m_isOn = !m_isOn;
    m_isOnBackwards = !m_isOn;
  }

  public void runWheelsBackwards() {
    m_isOn = false;
    m_isOnBackwards = !m_isOnBackwards;
  }
}
