package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class SpinUp extends SubsystemBase {

  
  private final CANSparkMax uppershooterMotor = new CANSparkMax(40, MotorType.kBrushless);
  private final CANSparkMax lowershooterMotor = new CANSparkMax(42, MotorType.kBrushless);


  private final RelativeEncoder uppershooterEncoder = uppershooterMotor.getEncoder();
  private final RelativeEncoder lowershooterEncoder = lowershooterMotor.getEncoder();

  private final SparkPIDController uppershooterController;
  private final SparkPIDController lowershooterController;

  public SpinUp() {


    uppershooterMotor.restoreFactoryDefaults();
    lowershooterMotor.restoreFactoryDefaults();

    lowershooterMotor.setInverted(true);
    uppershooterMotor.setInverted(true);

    uppershooterMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    lowershooterMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

    uppershooterEncoder.setPosition(0);
    lowershooterEncoder.setPosition(0);

    uppershooterController = uppershooterMotor.getPIDController();
    uppershooterController.setP(0.01);
    uppershooterController.setI(0);
    uppershooterController.setD(0);
    uppershooterController.setIZone(0);
    uppershooterController.setFF(0);
    uppershooterController.setOutputRange(-1, 1);

    lowershooterController = lowershooterMotor.getPIDController();
    lowershooterController.setP(0.01);
    lowershooterController.setI(0);
    lowershooterController.setD(0);
    lowershooterController.setIZone(0);
    lowershooterController.setFF(0);
    lowershooterController.setOutputRange(-1, 1);

  }

  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Upper Shooter Encoder", uppershooterEncoderValue());
    //SmartDashboard.putNumber("Lower Shooter Encoder", lowershooterEncoderValue());
  }

  public double uppershooterEncoderValue() {
    return uppershooterEncoder.getPosition();
  }

  public double lowershooterEncoderValue() {
    return lowershooterEncoder.getPosition();
  }

  public void SpinUpWheels(double throttle) {
    uppershooterMotor.set(throttle);
    lowershooterMotor.set(throttle);
  }
   public void stop() {
    uppershooterMotor.set(0);
    lowershooterMotor.set(0);
  }

}
