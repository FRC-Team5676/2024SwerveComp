package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeArmConstants;
import frc.robot.utils.ShuffleboardContent;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class RotateIntakeArm extends SubsystemBase {

  public double rotations;

  private final double m_minRotations = IntakeArmConstants.kMinRotations * IntakeArmConstants.kGearRatio;
  private final double m_maxRotations = IntakeArmConstants.kMaxRotations * IntakeArmConstants.kGearRatio;

  private final RelativeEncoder m_leftEncoder;
  private final CANSparkMax m_leftSparkMax;
  private final CANSparkMax m_rightSparkMax;
  private final SparkPIDController m_leftPIDController;

  public RotateIntakeArm() {
    m_leftSparkMax = new CANSparkMax(IntakeArmConstants.kLeftCanId, MotorType.kBrushless);
    m_leftSparkMax.restoreFactoryDefaults();
    m_leftSparkMax.setIdleMode(IntakeArmConstants.kMotorIdleMode);

    m_rightSparkMax = new CANSparkMax(IntakeArmConstants.kRightCanId, MotorType.kBrushless);
    m_rightSparkMax.restoreFactoryDefaults();
    m_rightSparkMax.setIdleMode(IntakeArmConstants.kMotorIdleMode);
    m_rightSparkMax.follow(m_leftSparkMax, true);

    // Encoder setup

    m_leftEncoder = m_leftSparkMax.getEncoder();
    m_leftPIDController = m_leftSparkMax.getPIDController();
    m_leftPIDController.setFeedbackDevice(m_leftEncoder);
    m_leftEncoder.setPositionConversionFactor(IntakeArmConstants.kIntakeArmEncoderPositionFactor);
    m_leftPIDController.setP(IntakeArmConstants.kP);
    m_leftPIDController.setI(IntakeArmConstants.kI);
    m_leftPIDController.setD(IntakeArmConstants.kD);
    m_leftPIDController.setIZone(IntakeArmConstants.kIZone);
    m_leftPIDController.setFF(IntakeArmConstants.kFF);
    m_leftPIDController.setOutputRange(IntakeArmConstants.kMinOutput, IntakeArmConstants.kMaxOutput);

    m_leftSparkMax.burnFlash();
    m_rightSparkMax.burnFlash();

    ShuffleboardContent.initIntakeArm(this);
  }

  @Override
  public void periodic() {
    setReferencePeriodic();
  }

  public double getMinRotations() {
    return IntakeArmConstants.kMinRotations;
  }

  public double getMaxRotations() {
    return IntakeArmConstants.kMaxRotations;
  }

  public double getPosition() {
    return m_leftEncoder.getPosition();
  }

  public void setIntakePosition(double position) {
    rotations = position;
  }

  // Throttle controllers
  public void rotateIntake(double throttle) {
    rotations += throttle * 0.5;
    setReferencePeriodic();
  }

  public void setReferencePeriodic() {
    rotations = MathUtil.clamp(rotations, m_minRotations, m_maxRotations);
    m_leftPIDController.setReference(rotations, CANSparkMax.ControlType.kPosition);
  }
}
