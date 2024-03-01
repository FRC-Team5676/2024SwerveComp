package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  private final CANSparkMax m_leftClimb;
  private final CANSparkMax m_rightClimb;

  public double climb;

  public Climber() {
    m_leftClimb = new CANSparkMax(57, MotorType.kBrushless);
    m_leftClimb.restoreFactoryDefaults();
    m_leftClimb.setIdleMode(IdleMode.kBrake);

    m_rightClimb = new CANSparkMax(58, MotorType.kBrushless);
    m_rightClimb.restoreFactoryDefaults();
    m_rightClimb.setIdleMode(IdleMode.kBrake);

    m_rightClimb.follow(m_leftClimb);

  }

  public void climbUp() {
    m_leftClimb.set(0.50);
  }

  public void climbDown() {
    m_leftClimb.set(-0.50);
  }

  public void climbStop() {
    m_leftClimb.set(0);
  }

  public void climb(double throttle) {
    if (Math.abs(throttle) > 0.05) { // Stops drift on climber
      m_leftClimb.set(throttle);
    } else {
      m_leftClimb.set(0);
    }
  }
}
