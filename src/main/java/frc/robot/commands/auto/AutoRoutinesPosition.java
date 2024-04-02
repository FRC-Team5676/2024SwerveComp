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

public class AutoRoutinesPosition {
        public static Command testCommand(IntakeArm intakeArm, SwerveDrive swerve) {
                return Commands.sequence(
                                new InstantCommand(() -> swerve.autonDriveRobotRelative(2, 1, 1, 0), swerve)
                                );
        }
}
