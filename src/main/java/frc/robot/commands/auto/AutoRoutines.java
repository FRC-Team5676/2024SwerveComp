package frc.robot.commands.auto;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.constants.IntakeArmConstants;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeWheels;
import frc.robot.subsystems.ShooterWheels;

public class AutoRoutines {
        public static Command ShootNoteAndLeave(ShooterWheels shooter, IntakeWheels intake, IntakeArm intakeArm,
                        SwerveDrive swerve) {
                return Commands.sequence(
                                new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker), intakeArm),
                                new InstantCommand(() -> shooter.runWheels(), shooter),
                                new WaitCommand(1),
                                new StartEndCommand(() -> intake.intake(-1),
                                                () -> intake.intake(0),
                                                intake)
                                                .withTimeout(1),
                                new InstantCommand(() -> shooter.runWheels(), shooter),
                                new WaitCommand(1),
                                new InstantCommand(() -> shooter.runWheelsBackwards(), shooter),
                                new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                                new WaitCommand(1),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> swerve.teleopDrive(-0.2, 0, 0), swerve).withTimeout(1.5),
                                        //new PathPlannerAuto("Pick Center Note").withTimeout(2.5),
                                        new StartEndCommand(() -> intake.intake(-1),
                                                        () -> intake.intake(0),
                                                        intake)
                                                        .withTimeout(2.5)),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> swerve.teleopDrive(0.2, 0, 0), swerve).withTimeout(1.5),
                                        //new PathPlannerAuto("Return To Speaker"),
                                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker),
                                                        intakeArm)).withTimeout(2),
                                new InstantCommand(() -> shooter.runWheels(), shooter),
                                new WaitCommand(1),
                                new StartEndCommand(() -> intake.intake(-1),
                                                () -> intake.intake(0),
                                                intake)
                                                .withTimeout(1),
                                new InstantCommand(() -> shooter.runWheels(), shooter),
                                new ParallelCommandGroup(
                                        new InstantCommand(() -> swerve.teleopDrive(-0.2, 0, 0), swerve).withTimeout(1.5),
                                        //new PathPlannerAuto("Pick Center Note").withTimeout(2),
                                        new InstantCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kIntakePosition), intakeArm),
                                        new InstantCommand(() -> shooter.runWheelsBackwards(), shooter)));
        }
}
