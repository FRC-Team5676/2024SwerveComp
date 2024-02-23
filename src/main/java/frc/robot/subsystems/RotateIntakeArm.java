package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class RotateIntakeArm extends SubsystemBase {

  public double rotations;

  private final int m_leftIntakeCanId = 21;
  private final int m_rightIntakeCanId = 23;

  private final RelativeEncoder m_leftDriveEncoder;
  private final CANSparkMax m_leftDriveMotor;
  private final CANSparkMax m_rightDriveMotor;
  private final SparkPIDController m_leftDriveController;
  private final SparkPIDController m_rightDriveController;

  private final double minRotations = 0;
  private final double maxRotations = 9.23;

  public RotateIntakeArm(boolean motorInverted) {
    m_leftDriveMotor = new CANSparkMax(m_leftIntakeCanId, MotorType.kBrushless);
    m_leftDriveMotor.restoreFactoryDefaults();
    m_leftDriveMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    m_rightDriveMotor = new CANSparkMax(m_rightIntakeCanId, MotorType.kBrushless);
    m_rightDriveMotor.restoreFactoryDefaults();
    m_rightDriveMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    m_rightDriveMotor.setInverted(motorInverted);

    m_rightDriveMotor.follow(m_leftDriveMotor);
    
    // drive encoder setup

    m_leftDriveEncoder = m_leftDriveMotor.getEncoder();
    m_leftDriveController = m_leftDriveMotor.getPIDController();
    m_leftDriveController.setP(0.01);
    m_leftDriveController.setI(0);
    m_leftDriveController.setD(0);
    m_leftDriveController.setIZone(0);
    m_leftDriveController.setFF(0);
    m_leftDriveController.setOutputRange(-1, 1);

    m_rightDriveController = m_rightDriveMotor.getPIDController();
    m_rightDriveController.setP(0.01);
    m_rightDriveController.setI(0);
    m_rightDriveController.setD(0);
    m_rightDriveController.setIZone(0);
    m_rightDriveController.setFF(0);
    m_rightDriveController.setOutputRange(-1, 1);
  }

  @Override
  public void periodic() {
    setReferencePeriodic();
  }

  public double getMinRotations() {
    return minRotations;
  }

  public double getMaxRotations() {
    return maxRotations;
  }

  public double getPosition() {
    return m_leftDriveEncoder.getPosition();
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
    rotations = MathUtil.clamp(rotations, minRotations, maxRotations);
    m_leftDriveController.setReference(rotations, CANSparkMax.ControlType.kPosition);

    // I don't think we need because this moter is a follower
    //m_rightDriveController.setReference(rotations, CANSparkMax.ControlType.kPosition); 
  }
}
