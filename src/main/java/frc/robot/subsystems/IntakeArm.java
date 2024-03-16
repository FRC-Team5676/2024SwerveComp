package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeArmConstants;
import frc.robot.utils.ShuffleboardContent;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeArm extends SubsystemBase {

  public double m_positionRadians;

  private final RelativeEncoder m_leftEncoder;
  private final CANSparkMax m_leftSparkMax;
  private final CANSparkMax m_rightSparkMax;
  private final SparkPIDController m_leftPIDController;

  public IntakeArm() {
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

    m_positionRadians = getPosition();

    ShuffleboardContent.initIntakeArm(this);
  }

  @Override
  public void periodic() {
    setReferencePeriodic();
  }

  public double getMinRotations() {
    return IntakeArmConstants.kMinPosition;
  }

  public double getMaxRotations() {
    return IntakeArmConstants.kMaxPosition;
  }

  public double getPosition() {
    return m_leftEncoder.getPosition();
  }

  public double getPositionSetpoint() {
    return m_positionRadians;
  }

  public void setIntakePosition(double position) {
    m_positionRadians = position;
  }

  // Throttle controllers
  public void rotateIntake(double throttle) {
    if (Math.abs(throttle) > 0.05) {
      m_positionRadians += Units.degreesToRadians(throttle * IntakeArmConstants.throttleMultiplier);
    }
  }

  public void setReferencePeriodic() {
    m_positionRadians = MathUtil.clamp(m_positionRadians, IntakeArmConstants.kMinPosition, IntakeArmConstants.kMaxPosition);
    m_leftPIDController.setReference(m_positionRadians, CANSparkMax.ControlType.kPosition);
  }

  public void intakeZeroPosition() {
    m_positionRadians = IntakeArmConstants.kZeroPosition;
  }

  public void intakeNotePosition() {
    m_positionRadians = IntakeArmConstants.kIntakePosition;
  }

  public void shootSpeaker() {
    m_positionRadians = IntakeArmConstants.kShootSpeaker;
  }

  public void shootStage() {
    m_positionRadians = IntakeArmConstants.kShootStage;
  }

  public void shootAmp() {
    m_positionRadians = IntakeArmConstants.kAmpPosition;
  }
}
