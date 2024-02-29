package frc.robot.commands.auto;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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
                                new StartEndCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker),
                                                () -> intakeArm.setIntakePosition(IntakeArmConstants.kShootSpeaker),
                                                intakeArm),
                                new StartEndCommand(() -> shooter.runWheels(),
                                                null,
                                                shooter),
                                new WaitCommand(2),
                                new StartEndCommand(() -> intake.intake(1),
                                                () -> intake.intake(0),
                                                intake)
                                                .withTimeout(1),
                                new StartEndCommand(() -> shooter.runWheels(),
                                                null,
                                                shooter),
                                new StartEndCommand(() -> intakeArm.setIntakePosition(IntakeArmConstants.kZeroPosition),
                                                () -> intakeArm.setIntakePosition(IntakeArmConstants.kZeroPosition),
                                                intakeArm),
                                new PathPlannerAuto("Test Auto"));
        }
}
