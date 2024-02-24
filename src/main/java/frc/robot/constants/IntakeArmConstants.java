package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.util.Units;

public final class IntakeArmConstants {
    public static final int kLeftCanId = 21;
    public static final int kRightCanId = 23;

    public static final double kMinRotations = Units.degreesToRadians(-90);
    public static final double kMaxRotations = Units.degreesToRadians(30);

    public static final double kGearRatio = 100 / 1 * 64 / 15;
    public static final double kIntakeArmEncoderPositionFactor = (2 * Math.PI) / kGearRatio;

    public static final double kP = 0.2;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kIZone = 0;
    public static final double kFF = 0;
    public static final double kMinOutput = -1;
    public static final double kMaxOutput = 1;

    public static final IdleMode kMotorIdleMode = IdleMode.kBrake;
}
