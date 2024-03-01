package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterRotateConstants;
import frc.robot.utils.ShuffleboardContent;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterRotate extends SubsystemBase {

  public double positionRadians;

  private final RelativeEncoder m_encoder;
  private final CANSparkFlex m_sparkMax;
  private final SparkPIDController m_controller;

  public ShooterRotate() {
    m_sparkMax = new CANSparkFlex(ShooterRotateConstants.kCanId, MotorType.kBrushless);
    m_sparkMax.restoreFactoryDefaults();
    m_sparkMax.setIdleMode(ShooterRotateConstants.kMotorIdleMode);

    // Encoder setup

    m_encoder = m_sparkMax.getEncoder();
    m_controller = m_sparkMax.getPIDController();
    m_controller.setFeedbackDevice(m_encoder);
    m_encoder.setPositionConversionFactor(ShooterRotateConstants.kEncoderPositionFactor);
    m_controller.setP(ShooterRotateConstants.kP);
    m_controller.setI(ShooterRotateConstants.kI);
    m_controller.setD(ShooterRotateConstants.kD);
    m_controller.setIZone(ShooterRotateConstants.kIZone);
    m_controller.setFF(ShooterRotateConstants.kFF);
    m_controller.setOutputRange(ShooterRotateConstants.kMinOutput, ShooterRotateConstants.kMaxOutput);

    m_sparkMax.burnFlash();

    ShuffleboardContent.initShooterRotate(this);
  }

  @Override
  public void periodic() {
    setReferencePeriodic();
  }

  public double getMinRotations() {
    return ShooterRotateConstants.kMinPosition;
  }

  public double getMaxRotations() {
    return ShooterRotateConstants.kMaxPosition;
  }

  public double getPosition() {
    return m_encoder.getPosition();
  }

  public double getPositionSetpoint() {
    return positionRadians;
  }

  public void setIntakePosition(double position) {
    positionRadians = position;
  }

  // Throttle controllers
  public void rotateShooter(double throttle) {
    if (Math.abs(throttle) > 0.05) {
      positionRadians += Units.degreesToRadians(throttle * ShooterRotateConstants.throttleMultiplier);
    }
  }

  public void setReferencePeriodic() {
    positionRadians = MathUtil.clamp(positionRadians, ShooterRotateConstants.kMinPosition, ShooterRotateConstants.kMaxPosition);
    m_controller.setReference(positionRadians, CANSparkFlex.ControlType.kPosition);
  }

  public void intakeZeroPosition() {
    positionRadians = ShooterRotateConstants.kZeroPosition;
  }

  public void shootSpeaker() {
    positionRadians = ShooterRotateConstants.kShootSpeaker;
  }
}
