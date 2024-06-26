// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import java.util.Map;

import com.ctre.phoenix6.StatusSignal;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.constants.ModuleConstants;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.SwerveModule;

/** Add your docs here. */
public class ShuffleboardContent {

        static ShuffleboardLayout boolsLayout;

        public ShuffleboardContent() {

        }

        public static void initBooleanShuffleboard(SwerveModule sm) {
                int moduleNumber = sm.m_modulePosition.ordinal();
                String abrev = ModuleConstants.modAbrev[moduleNumber];

                ShuffleboardTab x = Shuffleboard.getTab("Drivetrain");

                x.addBoolean("DriveCAN" + abrev, () -> sm.m_driveMotorConnected)
                                .withPosition(8, moduleNumber);
                x.addBoolean("TurnCAN" + abrev, () -> sm.m_turnMotorConnected)
                                .withPosition(9, moduleNumber);

        }

        public static void initDriveShuffleboard(SwerveModule sm) {
                int moduleNumber = sm.m_modulePosition.ordinal();
                String abrev = ModuleConstants.modAbrev[moduleNumber];
                String driveLayout = sm.m_modulePosition.toString() + " Drive";

                ShuffleboardLayout drLayout = Shuffleboard.getTab("Drivetrain")
                                .getLayout(driveLayout, BuiltInLayouts.kList).withPosition(moduleNumber * 2, 0)
                                .withSize(2, 2).withProperties(Map.of("Label position", "LEFT"));

                drLayout.addNumber("Drive Speed MPS " + abrev, () -> sm.m_driveEncoder.getVelocity());
                drLayout.addNumber("Drive Position DEG" + abrev, () -> sm.m_driveEncoder.getPosition());
                drLayout.addNumber("App Output " + abrev, () -> sm.m_driveSparkMax.getAppliedOutput());
                drLayout.addNumber("Current Amps " + abrev, () -> sm.m_driveSparkMax.getOutputCurrent());
                drLayout.addNumber("Firmware" + abrev, () -> sm.m_driveSparkMax.getFirmwareVersion());
        }

        public static void initTurnShuffleboard(SwerveModule sm) {
                int moduleNumber = sm.m_modulePosition.ordinal();
                String abrev = ModuleConstants.modAbrev[moduleNumber];
                String turnLayout = sm.m_modulePosition.toString() + " Turn";

                ShuffleboardLayout tuLayout = Shuffleboard.getTab("Drivetrain")
                                .getLayout(turnLayout, BuiltInLayouts.kList).withPosition(moduleNumber * 2, 2)
                                .withSize(2, 3).withProperties(Map.of("Label position", "LEFT"));

                tuLayout.addNumber("TurnEncPosDeg" + abrev, () -> Units.radiansToDegrees(sm.getTurnPositionRad()));
                tuLayout.addNumber("ActAngDeg" + abrev, () -> Units.radiansToDegrees(sm.m_turnEncoder.getPosition()));
                tuLayout.addNumber("TurnAngleOut" + abrev, () -> sm.m_turnSparkMax.getAppliedOutput());
                tuLayout.addNumber("AbsPosDeg" + abrev, () -> Units.rotationsToDegrees(sm.m_turnCANcoder.getAbsolutePosition().getValueAsDouble()));
                tuLayout.addNumber("CurrentAmps" + abrev, () -> sm.m_turnSparkMax.getOutputCurrent());
                tuLayout.addNumber("AbsOffsetDeg" + abrev, () -> sm.m_turnEncoderOffsetDeg);
                tuLayout.addNumber("Firmware" + abrev, () -> sm.m_turnSparkMax.getFirmwareVersion());
        }

        public static void initCoderBooleanShuffleboard(SwerveModule sm) {
                int moduleNumber = sm.m_modulePosition.ordinal();
                String abrev = ModuleConstants.modAbrev[moduleNumber];
                ShuffleboardTab x = Shuffleboard.getTab("CanCoders");

                x.addBoolean("Connected" + abrev, () -> sm.m_turnCoderConnected)
                                .withPosition(moduleNumber * 2 - 1, 4); // 1 3 5 7

                StatusSignal<Integer> faults = sm.m_turnCANcoder.getFaultField();
                x.addBoolean("Faults" + abrev, () -> 0 != faults.getValue())
                                .withPosition(moduleNumber * 2, 4); // 2 4 6 8

        }

        public static void initCANCoderShuffleboard(SwerveModule sm) {
                int moduleNumber = sm.m_modulePosition.ordinal();
                String abrev = ModuleConstants.modAbrev[moduleNumber];
                String canCoderLayout = sm.m_modulePosition.toString() + " CanCoder";

                ShuffleboardLayout coderLayout = Shuffleboard.getTab("CanCoders")
                                .getLayout(canCoderLayout, BuiltInLayouts.kList).withPosition(moduleNumber * 2, 0)
                                .withSize(2, 3).withProperties(Map.of("Label position", "LEFT"));

                coderLayout.addNumber("AbsPosDeg" + abrev, () -> Units.rotationsToDegrees(sm.m_turnCANcoder.getAbsolutePosition().getValueAsDouble()));
                coderLayout.addNumber("AbsOffsetDeg" + abrev, () -> sm.m_turnEncoderOffsetDeg);
                coderLayout.addNumber("PosDeg" + abrev, () -> Units.rotationsToDegrees(sm.m_turnCANcoder.getPosition().getValueAsDouble()));
                coderLayout.addNumber("VelocityRps" + abrev, () -> sm.m_turnCANcoder.getVelocity().getValueAsDouble());
        }

        public static void initGyro(SwerveDrive sd) {
                ShuffleboardTab drLayout1 = Shuffleboard.getTab("Gyro");

                drLayout1.addNumber("Yaw", () -> sd.getYaw()).withPosition(1, 1)
                                .withSize(1, 1);
                drLayout1.addNumber("Roll", () -> sd.getRoll()).withPosition(2, 1)
                                .withSize(1, 1);
                drLayout1.addNumber("Pitch", () -> sd.getPitch()).withPosition(3, 1)
                                .withSize(1, 1);
        }
        
        public static void initIntakeArm(IntakeArm intakeArm) {
                ShuffleboardTab drLayout1 = Shuffleboard.getTab("Intake Arm");

                // Arm Rotation
                drLayout1.addNumber("Rotate Position", () -> Units.radiansToDegrees(intakeArm.getRotationPosition())).withPosition(1, 1)
                                .withSize(1, 1);
                drLayout1.addNumber("Rotate Setpoint", () -> Units.radiansToDegrees(intakeArm.getRotationPositionSetpoint())).withPosition(2, 1)
                                .withSize(1, 1);
                drLayout1.addNumber("Ext Position", () -> (intakeArm.getExtensionPosition())).withPosition(1, 3)
                                .withSize(1, 1);
                drLayout1.addNumber("Ext Setpoint", () -> (intakeArm.getExtensionPositionSetpoint())).withPosition(2, 3)
                                .withSize(1, 1);

                // Intake Sensor
                drLayout1.addBoolean("Intake Sensor", () -> IntakeArm.m_noteDetected).withPosition(1, 2)
                                .withSize(1, 1);
                drLayout1.addBoolean("Intake Note", () -> IntakeArm.m_noteIntake).withPosition(2, 2)
                                .withSize(1, 1);
                drLayout1.addBoolean("Reverse Note", () -> IntakeArm.m_noteReverse).withPosition(3, 2)
                                .withSize(1, 1);
                drLayout1.addBoolean("Loaded", () -> IntakeArm.m_noteLoaded).withPosition(4, 2)
                                .withSize(1, 1);
                drLayout1.addBoolean("Use Note Sensor", () -> !IntakeArm.m_disableNoteSensor).withPosition(6, 2)
                                .withSize(1, 1);
        }
}
