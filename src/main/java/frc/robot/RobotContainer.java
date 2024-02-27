package frc.robot;

import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.AutonManager;
import frc.robot.commands.Intake.PickupCommand;
import frc.robot.commands.Intake.RotateIntakeCommand;
import frc.robot.commands.swerve.TeleopSwerveCommand;
import frc.robot.constants.DriveConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.PickUpNote;
import frc.robot.subsystems.RotateIntakeArm;

public class RobotContainer {
  public final SwerveDrive swerve = new SwerveDrive(); // Swerve drive system

  private final AutonManager autonManager = new AutonManager();
  private final CommandJoystick driver = new CommandJoystick(1);
  private final CommandXboxController operator = new CommandXboxController(0);

  private final RotateIntakeArm intakeArm = new RotateIntakeArm(); // Arm controller
  private final Shooter shooterWheels = new Shooter(); // Pickup intake controller
  private final PickUpNote intakeWheels = new PickUpNote(); // Cannon controller

  public RobotContainer() {
    addAutonomousChoices();
    autonManager.displayChoices();

    configureButtonBindings();
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }

  private void addAutonomousChoices() {
    /*
     * autonManager.addDefaultOption("Set Cone and Leave",
     * AutoRoutines.PlaceConeAndLeave(lowerArm, upperArm, intakeArm,
     * swerve, xController, yController, zController));
     */
  }

  private void configureButtonBindings() {
    swerve.setDefaultCommand(
        new TeleopSwerveCommand(
            swerve,
            () -> -MathUtil.applyDeadband(driver.getY(), DriveConstants.kXYDeadband),
            () -> -MathUtil.applyDeadband(driver.getX(), DriveConstants.kXYDeadband),
            () -> -MathUtil.applyDeadband(driver.getZ(), DriveConstants.kRotationDeadband)));

    driver.button(1).onTrue(new InstantCommand(swerve::toggleFieldRelative));
    driver.button(0).onTrue(new InstantCommand(swerve::zeroGyro));

    // Intake commands
    intakeWheels.setDefaultCommand(new PickupCommand(intakeWheels, operator));

    intakeArm.setDefaultCommand(new RotateIntakeCommand(intakeArm, operator));
    operator.button(XboxController.Button.kX.value).onTrue(new InstantCommand(intakeArm::shootSpeaker));
    operator.button(XboxController.Button.kY.value).onTrue(new InstantCommand(intakeArm::shootStage));
    operator.button(XboxController.Button.kA.value).onTrue(new InstantCommand(intakeArm::intakeNotePosition));
    operator.button(XboxController.Button.kB.value).onTrue(new InstantCommand(intakeArm::intakeZeroPosition));

    operator.button(XboxController.Button.kRightBumper.value).onTrue(Commands.runOnce(() -> shooterWheels.runWheels()));
    operator.button(XboxController.Button.kLeftBumper.value).onTrue(Commands.runOnce(() -> shooterWheels.runWheelsBackwards()));
    };
  }