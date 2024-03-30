package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.constants.IntakeArmConstants;
import frc.robot.subsystems.IntakeArm;

public class AutoRoutines {
        public static Command Shoot2Notes(IntakeArm intakeArm, SwerveDrive swerve) {
                return Commands.sequence(
                                new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker), intakeArm),
                                new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                                new WaitCommand(1),
                                new StartEndCommand(() -> intakeArm.intake(-1),
                                                () -> intakeArm.intake(0),
                                                intakeArm)
                                                .withTimeout(1),
                                new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                                new WaitCommand(1),
                                new InstantCommand(() -> intakeArm.runWheelsBackwards(), intakeArm),
                                new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                                new WaitCommand(1),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> swerve.teleopDrive(0.2, 0, 0), swerve).withTimeout(1.5),
                                        new StartEndCommand(() -> intakeArm.intake(-1),
                                                        () -> intakeArm.intake(0),
                                                        intakeArm)
                                                        .withTimeout(2)),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> swerve.teleopDrive(-0.3, 0, 0), swerve).withTimeout(2),
                                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker),
                                                        intakeArm).withTimeout(2)),
                                new WaitCommand(0.5),
                                new StartEndCommand(() -> intakeArm.intake(0.1),
                                                () -> intakeArm.intake(0),
                                                intakeArm)
                                                .withTimeout(0.1),
                                new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                                new WaitCommand(1),
                                new StartEndCommand(() -> intakeArm.intake(-1),
                                                () -> intakeArm.intake(0),
                                                intakeArm)
                                                .withTimeout(1),
                                new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                                new WaitCommand(1),
                                new ParallelCommandGroup(
                                        new StartEndCommand(() -> swerve.teleopDrive(0.35, 0, 0),
                                                        () -> swerve.teleopDrive(0, 0, 0),
                                                        swerve)
                                                        .withTimeout(3), // was 2 seconds
                                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm).withTimeout(2)));
        }

        public static Command Shoot3NotesRed(IntakeArm intakeArm, SwerveDrive swerve) {
                return Commands.sequence(
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker), intakeArm),
                        // Shoot Start
                        new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm), 
                        new WaitCommand(1), 
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(1),
                        new InstantCommand(() -> intakeArm.runWheelsBackwards(), intakeArm),
                        // Shoot End
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                        new WaitCommand(0.5), 
                        new ParallelCommandGroup(
                                new InstantCommand(() -> swerve.teleopDrive(0.4, 0, 0), swerve).withTimeout(1.2),
                                new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(1.2)
                                ),
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> swerve.teleopDrive(-0.4, 0, 0), swerve).withTimeout(1),
                                new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker),intakeArm).withTimeout(1)
                                ),
                        new StartEndCommand(() -> intakeArm.intake(0.1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.1),
                        // Shoot Start
                        new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                        new WaitCommand(1.2),
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve),
                        new WaitCommand(0.5),
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.5),
                        new InstantCommand(() -> intakeArm.runWheelsBackwards(), intakeArm),
                        // Shoot End
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                        new WaitCommand(1), 
                        new InstantCommand(() -> swerve.teleopDrive(0.4, -0.65, 0), swerve),
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(1.05),
                        new InstantCommand(() -> swerve.teleopDrive(-0.4, 0.65, 0), swerve),
                        new WaitCommand(0.05), 
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker), intakeArm),
                        new StartEndCommand(() -> intakeArm.intake(0.1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.1),
                        new WaitCommand(1.05), 
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve).withTimeout(0.1),
                        // Shoot Start
                        new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                        new WaitCommand(0.9),
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.5),
                        new InstantCommand(() -> intakeArm.runWheelsBackwards(), intakeArm),
                        // Shoot End
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                        new InstantCommand(() -> swerve.teleopDrive(0.4, 0, 0), swerve),
                        new WaitCommand(1),
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve));
        }

        public static Command Shoot3NotesBlue(IntakeArm intakeArm, SwerveDrive swerve) {
                return Commands.sequence(
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker), intakeArm),
                        // Shoot Start
                        new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm), 
                        new WaitCommand(1), 
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(1),
                        new InstantCommand(() -> intakeArm.runWheelsBackwards(), intakeArm),
                        // Shoot End
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                        new WaitCommand(0.5), 
                        new ParallelCommandGroup(
                                new InstantCommand(() -> swerve.teleopDrive(0.4, 0, 0), swerve).withTimeout(1.2),
                                new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(1.2)
                                ),
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve),
                        new ParallelCommandGroup(
                                new InstantCommand(() -> swerve.teleopDrive(-0.4, 0, 0), swerve).withTimeout(1),
                                new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker),intakeArm).withTimeout(1)
                                ),
                        new StartEndCommand(() -> intakeArm.intake(0.1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.1),
                        // Shoot Start
                        new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                        new WaitCommand(1.2),
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve),
                        new WaitCommand(0.5),
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.5),
                        new InstantCommand(() -> intakeArm.runWheelsBackwards(), intakeArm),
                        // Shoot End
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                        new WaitCommand(1), 
                        new InstantCommand(() -> swerve.teleopDrive(0.4, 0.65, 0), swerve),
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(1.05),
                        new InstantCommand(() -> swerve.teleopDrive(-0.4, -0.65, 0), swerve),
                        new WaitCommand(0.05), 
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker), intakeArm),
                        new StartEndCommand(() -> intakeArm.intake(0.1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.1),
                        new WaitCommand(1.05), 
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve).withTimeout(0.1),
                        // Shoot Start
                        new InstantCommand(() -> intakeArm.runWheelsFast(), intakeArm),
                        new WaitCommand(0.9),
                        new StartEndCommand(() -> intakeArm.intake(-1), () -> intakeArm.intake(0), intakeArm).withTimeout(0.5),
                        new InstantCommand(() -> intakeArm.runWheelsBackwards(), intakeArm),
                        // Shoot End
                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                        new InstantCommand(() -> swerve.teleopDrive(0.4, 0, 0), swerve),
                        new WaitCommand(1),
                        new InstantCommand(() -> swerve.teleopDrive(0, 0, 0), swerve));
        }
}
