package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeArmConstants;
import frc.robot.utils.ShuffleboardContent;

public class IntakeArmExtension extends SubsystemBase {
  public double positionRadians;

  private final TalonSRX m_motor;
  private final double m_minPosition = Units.degreesToRadians(0);
  private final double m_maxPosition = Units.degreesToRadians(5000);
  private final double m_encoderPositionFactor = (2 * Math.PI) / 5;

  public IntakeArmExtension() {
    m_motor = new TalonSRX(60);
    m_motor.configFactoryDefault();
    m_motor.setNeutralMode(NeutralMode.Brake);

    // Encoder setup
    m_motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,	0, 30);

    m_motor.config_kP(0, 1);
    m_motor.config_kI(0, 0);
    m_motor.config_kD(0, 0);
    m_motor.config_kF(0, 0);
    m_motor.configPeakOutputForward(1);
    m_motor.configPeakOutputReverse(-1);

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
    return m_motor.getSelectedSensorPosition() * m_encoderPositionFactor;
  }

  public double getPositionSetpoint() {
    return positionRadians;
  }

  public void setExtensionPosition(double position) {
    positionRadians = position;
  }

  // Throttle controllers
  public void rotateIntake(double throttle) {
    if (Math.abs(throttle) > 0.05) {
      positionRadians += Units.degreesToRadians(throttle * IntakeArmConstants.throttleMultiplier);
    }
  }

  public void setReferencePeriodic() {
    positionRadians = MathUtil.clamp(positionRadians, IntakeArmConstants.kMinPosition, IntakeArmConstants.kMaxPosition);
    m_motor.set(ControlMode.Position , positionRadians / m_encoderPositionFactor);
  }

  public void intakeZeroPosition() {
    positionRadians = m_minPosition;
  }

  public void shootAmp() {
    positionRadians = m_maxPosition;
  }
}
