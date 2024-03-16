package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.ShuffleboardContent;

public class IntakeArmExtension extends SubsystemBase {
  public double m_positionInches;

  private final WPI_TalonSRX m_motor = new WPI_TalonSRX(60);
  private final double m_minPosition = 0.2;
  private final double m_maxPosition = 2.45;
  private final double m_convStepToInch = 5 * 10 * 1024 / 0.8;

  public IntakeArmExtension() {
    m_motor.configFactoryDefault();
    m_motor.setNeutralMode(NeutralMode.Brake);

    // PID Setup
    m_motor.config_kP(0, 0.04);
    m_motor.config_kI(0, 0);
    m_motor.config_kD(0, 0);
    m_motor.config_kF(0, 0);

    // Encoder setup
    m_motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    m_motor.setInverted(false);
    m_motor.setSensorPhase(false);
    m_motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 30);
    m_motor.configPeakOutputForward(1);
    m_motor.configPeakOutputReverse(-1);

    m_positionInches = getPosition();

    ShuffleboardContent.initIntakeArmExtension(this);
  }

  @Override
  public void periodic() {
    setReferencePeriodic();
  }

  public double getMinRotations() {
    return m_minPosition;
  }

  public double getMaxRotations() {
    return m_maxPosition;
  }

  public double getPosition() {
    return m_motor.getSelectedSensorPosition() / m_convStepToInch;
  }

  public double getPositionSetpoint() {
    return m_positionInches;
  }

  public void setExtensionPosition(double position) {
    m_positionInches = position;
  }

  // Throttle controllers
  public void extendIntake(double throttle) {
    m_positionInches += throttle * 0.1;
  }

  public void setReferencePeriodic() {
    m_positionInches = MathUtil.clamp(m_positionInches, m_minPosition, m_maxPosition);
    m_motor.set(ControlMode.Position, m_positionInches * m_convStepToInch);
  }

  public void intakeZeroPosition() {
    m_positionInches = m_minPosition;
  }

  public void shootAmp() {
    m_positionInches = m_maxPosition;
  }
}
