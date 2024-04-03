package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DriveConstants;
import frc.robot.utils.Enums.ModulePosition;

public class SwerveDrive extends SubsystemBase {
    public static final SwerveDriveKinematics m_driveKinematics = new SwerveDriveKinematics(
            new Translation2d(DriveConstants.kRobotWidth / 2, DriveConstants.kRobotLength / 2), // Front Left
            new Translation2d(DriveConstants.kRobotWidth / 2, -DriveConstants.kRobotLength / 2), // Front Right
            new Translation2d(-DriveConstants.kRobotWidth / 2, DriveConstants.kRobotLength / 2), // Rear Left
            new Translation2d(-DriveConstants.kRobotWidth / 2, -DriveConstants.kRobotLength / 2)); // Rear Right

    private final SwerveModule m_frontLeft = new SwerveModule(
            ModulePosition.FRONT_LEFT,
            DriveConstants.kFrontLeftDriveMotorCanId,
            DriveConstants.kFrontLeftTurnMotorCanId,
            DriveConstants.kFrontLeftTurnEncoderCanId,
            DriveConstants.kTurnMotorInverted,
            DriveConstants.kFrontLeftAngularOffset);
    private final SwerveModule m_frontRight = new SwerveModule(
            ModulePosition.FRONT_RIGHT,
            DriveConstants.kFrontRightDriveMotorCanId,
            DriveConstants.kFrontRightTurnMotorCanId,
            DriveConstants.kFrontRightTurnEncoderCanId,
            DriveConstants.kTurnMotorInverted,
            DriveConstants.kFrontRightAngularOffset);
    private final SwerveModule m_rearLeft = new SwerveModule(
            ModulePosition.REAR_LEFT,
            DriveConstants.kRearLeftDriveMotorCanId,
            DriveConstants.kRearLeftTurnMotorCanId,
            DriveConstants.kRearLeftTurnEncoderCanId,
            DriveConstants.kTurnMotorInverted,
            DriveConstants.kRearLeftAngularOffset);
    private final SwerveModule m_rearRight = new SwerveModule(
            ModulePosition.REAR_RIGHT,
            DriveConstants.kRearRightDriveMotorCanId,
            DriveConstants.kRearRightTurnMotorCanId,
            DriveConstants.kRearRightTurnEncoderCanId,
            DriveConstants.kTurnMotorInverted,
            DriveConstants.kRearRightAngularOffset);
    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);
    private boolean m_fieldRelative = DriveConstants.kFieldRelative;
    private SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(
            m_driveKinematics,
            Rotation2d.fromDegrees(getAngle()),
            new SwerveModulePosition[] {
                    m_frontLeft.getPosition(),
                    m_frontRight.getPosition(),
                    m_rearLeft.getPosition(),
                    m_rearRight.getPosition()
            });

    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        m_odometry.update(Rotation2d.fromDegrees(getAngle()), getPositions());
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose) {
        m_odometry.resetPosition(Rotation2d.fromDegrees(getAngle()), getPositions(), pose);
    }

    public ChassisSpeeds getSpeeds() {
        return m_driveKinematics.toChassisSpeeds(getModuleStates());
    }

    public void teleopDrive(double throttle, double strafe, double rotation) {

        // Convert the commanded speeds into the correct units for the drivetrain
        double throttleSpeed = throttle * DriveConstants.kMaxSpeedMetersPerSecond;
        double strafeSpeed = strafe * DriveConstants.kMaxSpeedMetersPerSecond;
        double rotationSpeed = rotation * DriveConstants.kMaxRotationRadiansPerSecond;

        SwerveModuleState[] swerveModuleStates = m_driveKinematics.toSwerveModuleStates(
                m_fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(throttleSpeed, strafeSpeed, rotationSpeed,
                                Rotation2d.fromDegrees(getYaw()))
                        : new ChassisSpeeds(throttleSpeed, strafeSpeed, rotationSpeed));

        setRobotStateTeleop(swerveModuleStates);
    }

    public void autonDriveRobotRelative(double metersPerSec, double xMeters, double yMeters, double angleRad) {

        if (DriverStation.getAlliance().get() != null) {
            if (DriverStation.getAlliance().get() == Alliance.Red) {
                xMeters = -xMeters;
            }
        }
        double distanceToTarget = Math.sqrt(Math.pow(xMeters, 2) + Math.pow(yMeters, 2));
        double totalSeconds = distanceToTarget / metersPerSec;

        // Convert the commanded speeds into the correct units for the drivetrain
        double throttleSpeed = xMeters / totalSeconds;
        double strafeSpeed = yMeters / totalSeconds;
        double rotationSpeed = angleRad / (2 * Math.PI);

        for (double i = 0; i >= totalSeconds; i += 0.02) {
            ChassisSpeeds robotRelativeSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(throttleSpeed, strafeSpeed,
                    rotationSpeed, Rotation2d.fromDegrees(getYaw()));
            ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);

            SwerveModuleState[] swerveModuleStates = m_driveKinematics.toSwerveModuleStates(targetSpeeds);
            setRobotStateAuton(swerveModuleStates);
        }
    }

    public void zeroGyro() {
        m_gyro.zeroYaw();
    }

    // Yaw Z Direction
    // Perpendicular to board
    // Positive towards the ceiling
    // + Clockwise / - Counter-clockwise
    public double getYaw() {
        return -m_gyro.getYaw();
    }

    // Roll Y direction
    // Width (short side) or board
    // Positive towards left when looking towards the RoboRio connector
    // + Left / - Right
    public double getRoll() {
        return m_gyro.getRoll();
    }

    // Pitch X direction
    // lengthwise (long side) of board
    // Positive towards RoboRio connector
    // + Forward / - Backwards
    public double getPitch() {
        return m_gyro.getPitch();
    }

    public void toggleFieldRelative() {
        m_fieldRelative = !m_fieldRelative;
    }

    private double getAngle() {
        return m_gyro.getAngle();
    }

    private void setRobotStateTeleop(SwerveModuleState[] swerveModuleStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(
                swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
        m_frontLeft.setDesiredStateTeleop(swerveModuleStates[0]);
        m_frontRight.setDesiredStateTeleop(swerveModuleStates[1]);
        m_rearLeft.setDesiredStateTeleop(swerveModuleStates[2]);
        m_rearRight.setDesiredStateTeleop(swerveModuleStates[3]);
    }

    private void setRobotStateAuton(SwerveModuleState[] swerveModuleStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(
                swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
        m_frontLeft.setDesiredStateAuton(swerveModuleStates[0]);
        m_frontRight.setDesiredStateAuton(swerveModuleStates[1]);
        m_rearLeft.setDesiredStateAuton(swerveModuleStates[2]);
        m_rearRight.setDesiredStateAuton(swerveModuleStates[3]);
    }

    private SwerveModulePosition[] getPositions() {
        return new SwerveModulePosition[] {
                m_frontLeft.getPosition(),
                m_frontRight.getPosition(),
                m_rearLeft.getPosition(),
                m_rearRight.getPosition()
        };
    }

    private SwerveModuleState[] getModuleStates() {
        return new SwerveModuleState[] {
                m_frontLeft.getState(),
                m_frontRight.getState(),
                m_rearLeft.getState(),
                m_rearRight.getState()
        };
    }
}