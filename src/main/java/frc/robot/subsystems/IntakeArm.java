package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeArmConstants;
import frc.robot.utils.ShuffleboardContent;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeArm extends SubsystemBase {

  public double m_positionRadians;
  public double m_extendInches;

  private final RelativeEncoder m_leftEncoder;
  private final CANSparkMax m_leftSparkMax;
  private final CANSparkMax m_rightSparkMax;
  private final SparkPIDController m_leftPIDController;

  private final WPI_TalonSRX m_motor = new WPI_TalonSRX(IntakeArmConstants.kExtendCanId);

  public IntakeArm() {
    /*
     * ROTATE SETUP
     */
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

    m_positionRadians = getRotationPosition();

    /*
     * EXTENSION SETUP
     */
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

    m_extendInches = getRotationPosition();

    ShuffleboardContent.initIntakeArm(this);
  }

  @Override
  public void periodic() {
    setReferencePeriodic();
  }

  public double getMinRotations() {
    return IntakeArmConstants.kMinRotatePosition;
  }

  public double getMaxRotations() {
    return IntakeArmConstants.kMaxRotatePosition;
  }

  public double getRotationPosition() {
    return m_leftEncoder.getPosition();
  }

  public double getExtensionPosition() {
    return m_motor.getSelectedSensorPosition() / IntakeArmConstants.kIntakeArmEncoderExtendFactor;
  }

  public double getRotationPositionSetpoint() {
    return m_positionRadians;
  }

  public double getExtensionPositionSetpoint() {
    return m_extendInches;
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

  public void extendIntake(double throttle) {
    if (Math.abs(throttle) > 0.05) {
      m_extendInches += throttle * 0.1;
    }
  }

  public void setReferencePeriodic() {
    // Extend Setpoint
    m_extendInches = MathUtil.clamp(m_extendInches, IntakeArmConstants.kMinExtendPosition, IntakeArmConstants.kMaxExtendPosition);
    m_motor.set(ControlMode.Position, m_extendInches * IntakeArmConstants.kIntakeArmEncoderExtendFactor);

    // Rotate Setpoint
    if (m_extendInches > IntakeArmConstants.kMSafeExtendPosition) {
      m_positionRadians = MathUtil.clamp(m_positionRadians, IntakeArmConstants.kShootSpeaker,
          IntakeArmConstants.kMaxRotatePosition);
    } else {
      m_positionRadians = MathUtil.clamp(m_positionRadians, IntakeArmConstants.kMinRotatePosition,
          IntakeArmConstants.kMaxRotatePosition);
    }
    m_leftPIDController.setReference(m_positionRadians, CANSparkMax.ControlType.kPosition);
  }

  public void intakeNotePosition() {
    m_positionRadians = IntakeArmConstants.kIntakePosition;
    m_extendInches = IntakeArmConstants.kMinExtendPosition;
  }

  public void shootSpeaker() {
    m_positionRadians = IntakeArmConstants.kShootSpeaker;
    m_extendInches = IntakeArmConstants.kMinExtendPosition;
  }

  public void shootStage() {
    m_positionRadians = IntakeArmConstants.kShootStage;
    m_extendInches = IntakeArmConstants.kMinExtendPosition;
  }

  public void shootAmp() {
    m_positionRadians = IntakeArmConstants.kAmpPosition;
    m_extendInches = IntakeArmConstants.kMaxExtendPosition;
  }
}
