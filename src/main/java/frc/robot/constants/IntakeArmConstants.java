package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.util.Units;

public final class IntakeArmConstants {
    public static final int kLeftCanId = 21;
    public static final int kRightCanId = 23;
    public static final int kExtendCanId = 60;
    public static final int kUpperShootCanId = 62;
    public static final int kLowerShootCanId = 59;
    public static final int kIntakeWheelsCanId = 61;

    // Rotate
    public static final double kMinRotatePosition = Units.degreesToRadians(0); // -87.5
    public static final double kMaxRotatePosition = Units.degreesToRadians(109.0); // 35

    public static final double kZeroPosition = Units.degreesToRadians(87.5); // 0
    public static final double kIntakePosition = Units.degreesToRadians(1.5); // -86.0
    public static final double kShootSpeaker = Units.degreesToRadians(25.5); // -64.5
    public static final double kShootStage = Units.degreesToRadians(44.5); // -43.0
    public static final double kAmpPosition = Units.degreesToRadians(98.5); // -31.0, 96.5

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

    // Extend
    public static final double kMinExtendPosition = 0.2;
    public static final double kMSafeExtendPosition = 0.3;
    public static final double kMaxExtendPosition = 2.45;
    public static final double kIntakeArmEncoderExtendFactor = 5 * 10 * 1024 / 0.8;

    // Shoot
    public static final double kShootSpeedForwardFast = 0.55;
    public static final double kShootSpeedForwardSlow = 0.25;
    public static final double kShootSpeedBackwards = -0.1;

    // Intake Wheels
    public static final int kIntakeSensorChannel = 0;

    // LED Lights
    public static final int kLedControllerChannel = 0;
  }
