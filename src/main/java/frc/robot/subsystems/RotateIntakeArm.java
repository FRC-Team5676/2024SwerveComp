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

  private final RelativeEncoder m_leftEncoder;
  private final RelativeEncoder m_rightEncoder;
  private final CANSparkMax m_leftSparkMax;
  private final CANSparkMax m_rightSparkMax;
  private final SparkPIDController m_leftPIDController;
  private final SparkPIDController m_rightPIDController;

  public RotateIntakeArm() {
    m_leftSparkMax = new CANSparkMax(IntakeArmConstants.kLeftCanId, MotorType.kBrushless);
    m_leftSparkMax.restoreFactoryDefaults();
    m_leftSparkMax.setIdleMode(IntakeArmConstants.kMotorIdleMode);
    m_leftSparkMax.setInverted(false);

    m_rightSparkMax = new CANSparkMax(IntakeArmConstants.kRightCanId, MotorType.kBrushless);
    m_rightSparkMax.restoreFactoryDefaults();
    m_rightSparkMax.setIdleMode(IntakeArmConstants.kMotorIdleMode);
    m_rightSparkMax.setInverted(true);

    m_rightSparkMax.follow(m_leftSparkMax);
    
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

    m_rightEncoder = m_rightSparkMax.getEncoder();
    m_rightPIDController = m_rightSparkMax.getPIDController();
    m_rightPIDController.setFeedbackDevice(m_rightEncoder);
    m_rightPIDController.setP(IntakeArmConstants.kP);
    m_rightPIDController.setI(IntakeArmConstants.kI);
    m_rightPIDController.setD(IntakeArmConstants.kD);
    m_rightPIDController.setIZone(IntakeArmConstants.kIZone);
    m_rightPIDController.setFF(IntakeArmConstants.kFF);
    m_rightPIDController.setOutputRange(IntakeArmConstants.kMinOutput, IntakeArmConstants.kMaxOutput);

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

  public double getLeftPosition() {
    return m_leftEncoder.getPosition();
  }

  public double getRightPosition() {
    return m_rightEncoder.getPosition();
  }

  public double getAvgPosition() {
    double leftPosition = getLeftPosition();
    double rightPosition = getRightPosition();
    return (leftPosition + rightPosition) / 2;
  }

  public void setIntakePosition(double position) {
    rotations = position;
  }

  // Throttle controllers
  public void rotateIntake(double throttle) {
    //m_leftDriveMotor.set(throttle);
    //m_rightDriveMotor.set(throttle);
    rotations += throttle;
    setReferencePeriodic();
  }

  public void setReferencePeriodic() {
    rotations = MathUtil.clamp(rotations, IntakeArmConstants.kMinRotations, IntakeArmConstants.kMaxRotations);
    m_leftPIDController.setReference(rotations, CANSparkMax.ControlType.kPosition);

    // I don't think we need because this moter is a follower
    //m_rightDriveController.setReference(rotations, CANSparkMax.ControlType.kPosition); 
  }
}
