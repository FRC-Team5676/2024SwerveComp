package frc.robot.constants;

import edu.wpi.first.math.util.Units;

public final class DriveConstants {
    public static final double kXYDeadband = 0.05;
    public static final double kRotationDeadband = 0.1;

    public static final boolean kTurnMotorInverted = true;

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = 0;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kRearLeftChassisAngularOffset = 0;
    public static final double kRearRightChassisAngularOffset = 0;

    public static final double kFrontLeftAngularOffset = 303.75;
    public static final double kFrontRightAngularOffset = 166.0;
    public static final double kRearLeftAngularOffset = 124.9;
    public static final double kRearRightAngularOffset = 206.9;

    // Spark MAX Drive Motor CAN IDs
    public static final int kFrontLeftDriveMotorCanId = 24;
    public static final int kFrontRightDriveMotorCanId = 20;
    public static final int kRearLeftDriveMotorCanId = 41;
    public static final int kRearRightDriveMotorCanId = 30;

    // Spark MAX Turn Motor CAN IDs
    public static final int kFrontLeftTurnMotorCanId = 25;
    public static final int kFrontRightTurnMotorCanId = 26;
    public static final int kRearLeftTurnMotorCanId = 27;
    public static final int kRearRightTurnMotorCanId = 22;

    // Spark MAX Turn Encoder CAN IDs
    public static final int kFrontLeftTurnEncoderCanId = 50;
    public static final int kFrontRightTurnEncoderCanId = 52;
    public static final int kRearLeftTurnEncoderCanId = 53;
    public static final int kRearRightTurnEncoderCanId = 51;

    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 5.1;
    public static final double kMaxRotationRadiansPerSecond = Math.PI; // radians per second

    // Chassis configuration
    public static final double kRobotWidth = Units.inchesToMeters(20.75);  // Distance between centers of right and left wheels on robot
    public static final double kRobotLength = Units.inchesToMeters(21.25); // Distance between front and back wheels on robot

    // Drive Control
    public static final boolean kFieldRelative = false;
    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)
}
