package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase{

    ///////////////////////////////// Can ID is wrong! //////////////////////////////////

    private final CANSparkMax m_climb = new CANSparkMax(43, MotorType.kBrushless);

    public double climb;
    

    public void climb(double throttle){
        if (Math.abs(throttle) > 0.05) { // Stops drift on climber
          m_climb.set(throttle);
        }
        else {
          m_climb.set(0);
        }
      }
}
