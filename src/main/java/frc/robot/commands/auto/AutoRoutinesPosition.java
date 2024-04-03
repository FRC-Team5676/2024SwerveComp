package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.IntakeArm;

public class AutoRoutinesPosition {
        public static Command testCommand(IntakeArm intakeArm, SwerveDrive swerve) {
                return Commands.sequence(
                                new InstantCommand(() -> swerve.autonDriveRobotRelative(2, 2, 0, 0), swerve),
                                new WaitCommand(5)
                                );
        }
}
