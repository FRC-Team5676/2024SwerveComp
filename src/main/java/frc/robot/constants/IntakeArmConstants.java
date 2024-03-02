package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.util.Units;

public final class IntakeArmConstants {
    public static final int kLeftCanId = 21;
    public static final int kRightCanId = 23;

    public static final double kMinPosition = Units.degreesToRadians(-87.5);
    public static final double kMaxPosition = Units.degreesToRadians(35);

    public static final double kZeroPosition = Units.degreesToRadians(0);
    public static final double kIntakePosition = Units.degreesToRadians(-86.0);
    public static final double kShootSpeaker = Units.degreesToRadians(-64.5);
    public static final double kShootStage = Units.degreesToRadians(-45.0);
    public static final double kAmpPosition = Units.degreesToRadians(-31.0);

    public static final double kGearRatio = 100 / 1 * 64 / 15;
    public static final double kIntakeArmEncoderPositionFactor = (2 * Math.PI) / kGearRatio;

    public static final double kP = 1.5;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kIZone = 0;
    public static final double kFF = 0;
    public static final double kMinOutput = -1;
    public static final double kMaxOutput = 1;

    public static final IdleMode kMotorIdleMode = IdleMode.kBrake;

    public static final double throttleMultiplier = 0.5;
}
