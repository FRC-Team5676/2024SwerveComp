package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class RotateIntakeArm extends SubsystemBase {

  public double rotations;

  private final int m_leftintakeCanId = 21;
  private final int m_rightintakeCanId = 23;

  private final RelativeEncoder m_leftdriveEncoder;
  private final CANSparkMax m_leftdriveMotor;
  private final CANSparkMax m_rightdriveMotor;
  private final SparkPIDController m_leftdriveController;
  private final SparkPIDController m_rightdriveController;

  private final double minRotations = 0;
  private final double maxRotations = 9.23;

  public RotateIntakeArm(boolean motorInverted) {
    m_leftdriveMotor = new CANSparkMax(m_leftintakeCanId, MotorType.kBrushless);
    m_leftdriveMotor.restoreFactoryDefaults();
    m_leftdriveMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

    m_rightdriveMotor = new CANSparkMax(m_rightintakeCanId, MotorType.kBrushless);
    m_rightdriveMotor.restoreFactoryDefaults();
    m_rightdriveMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    m_rightdriveMotor.setInverted(motorInverted);

    // drive encoder setup

    m_leftdriveEncoder = m_leftdriveMotor.getEncoder();
    m_leftdriveController = m_leftdriveMotor.getPIDController();
    m_leftdriveController.setP(0.01);
    m_leftdriveController.setI(0);
    m_leftdriveController.setD(0);
    m_leftdriveController.setIZone(0);
    m_leftdriveController.setFF(0);
    m_leftdriveController.setOutputRange(-1, 1);

    // m_rightdriveEncoder = m_rightdriveMotor.getEncoder();

    m_rightdriveController = m_rightdriveMotor.getPIDController();
    m_rightdriveController.setP(0.01);
    m_rightdriveController.setI(0);
    m_rightdriveController.setD(0);
    m_rightdriveController.setIZone(0);
    m_rightdriveController.setFF(0);
    m_rightdriveController.setOutputRange(-1, 1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getMinRotations() {
    return minRotations;
  }

  public double getMaxRotations() {
    return maxRotations;
  }

  public double getPosition() {
    return m_leftdriveEncoder.getPosition();

  }

  public void setIntakePosition(double position) {
    setReferenceValue(position);
  }

// Throttle controllers

  public void rotateIntake(double throttle) {
      m_leftdriveMotor.set(throttle);
      m_rightdriveMotor.set(throttle);
  }

  /* public void stop() {
    m_leftdriveMotor.set(0);
    m_rightdriveMotor.set(0);
  } */

  public void setReferencePeriodic() {
    m_leftdriveController.setReference(rotations, CANSparkMax.ControlType.kPosition);
  }

  public void setReferenceValue(double rotation) {
    rotations = rotation;
  }

  public void moveIntakeToPosition(double position) {
    setReferenceValue(position);
    setReferencePeriodic();
  }
}
