package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.util.Units;

public final class ShooterRotateConstants {
    public static final int kCanId = 60;

    public static final double kMinPosition = Units.degreesToRadians(-40);
    public static final double kMaxPosition = Units.degreesToRadians(0);

    public static final double kZeroPosition = Units.degreesToRadians(0);
    public static final double kShootSpeaker = Units.degreesToRadians(15);

    public static final double kGearRatio = 1 / 1;
    public static final double kEncoderPositionFactor = (2 * Math.PI) / kGearRatio;

    public static final double kP = 1;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kIZone = 0;
    public static final double kFF = 0;
    public static final double kMinOutput = -1;
    public static final double kMaxOutput = 1;

    public static final IdleMode kMotorIdleMode = IdleMode.kBrake;

    public static final double throttleMultiplier = 0.5;
}
