package frc.robot;

import frc.robot.subsystems.SwerveDrive;
import frc.robot.utils.AutonManager;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.auto.AutoRoutines;
import frc.robot.commands.swerve.TeleopSwerveCommand;
import frc.robot.constants.DriveConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.IntakeArm;

public class RobotContainer {
  public final SwerveDrive swerve = new SwerveDrive(); // Swerve drive system
  public final IntakeArm intakeArm = new IntakeArm(); // Arm controller
  
  private final Climber climb = new Climber(); // Climber

  private final AutonManager autonManager = new AutonManager();
  private final CommandJoystick driver = new CommandJoystick(1);
  private final CommandXboxController operator = new CommandXboxController(0);

  public RobotContainer() {
    addAutonomousChoices();
    autonManager.displayChoices();

    configureButtonBindings();
  }

  public Command getAutonomousCommand() {
    return autonManager.getSelected();
  }

  private void addAutonomousChoices() {
    autonManager.addDefaultOption("Red / Blue - Shoot 2 Notes and Leave",
        AutoRoutines.Shoot2Notes(intakeArm, swerve));
    autonManager.addOption("Red - Shoot 3 Notes and Leave",
        AutoRoutines.Shoot3NotesRed(intakeArm, swerve));
    autonManager.addOption("Blue - Shoot 3 Notes and Leave",
        AutoRoutines.Shoot3NotesBlue(intakeArm, swerve));
  }

  private void configureButtonBindings() {

    // Swerve drive commands
    swerve.setDefaultCommand(
        new TeleopSwerveCommand(
            swerve,
            () -> -MathUtil.applyDeadband(driver.getY(), DriveConstants.kXYDeadband),
            () -> -MathUtil.applyDeadband(driver.getX(), DriveConstants.kXYDeadband),
            () -> -MathUtil.applyDeadband(driver.getZ(), DriveConstants.kRotationDeadband)));

    driver.button(1).onTrue(new InstantCommand(swerve::toggleFieldRelative));
    driver.button(8).onTrue(new InstantCommand(swerve::zeroGyro));

    // Rotate commands
    intakeArm.setDefaultCommand(new IntakeCommand(intakeArm, operator)); // Does intake wheels also
    operator.button(XboxController.Button.kX.value).onTrue(new InstantCommand(intakeArm::shootSpeaker));
    operator.button(XboxController.Button.kY.value).onTrue(new InstantCommand(intakeArm::shootStage));
    operator.button(XboxController.Button.kA.value).onTrue(new InstantCommand(intakeArm::intakeNotePosition));
    operator.button(XboxController.Button.kB.value).onTrue(new InstantCommand(intakeArm::shootAmp));
    operator.button(XboxController.Button.kStart.value).onTrue(new InstantCommand(intakeArm::toggleNoteSensor));
    operator.button(XboxController.Button.kBack.value).onTrue(new InstantCommand(intakeArm::resetNoteSensor));

    // Shoot commands
    operator.button(XboxController.Button.kRightBumper.value).onTrue(new InstantCommand(intakeArm::runWheelsFast));
    operator.button(XboxController.Button.kRightBumper.value).onFalse(new InstantCommand(intakeArm::runWheelsBackwards));
    operator.button(XboxController.Button.kLeftBumper.value).onTrue(new InstantCommand(intakeArm::runWheelsFast));
    operator.button(XboxController.Button.kLeftBumper.value).onFalse(new InstantCommand(intakeArm::runWheelsBackwards));

    // Climb commands
    driver.button(3).onTrue(new InstantCommand(climb::climbDown));
    driver.button(3).onFalse(new InstantCommand(climb::climbStop));
    driver.button(4).onTrue(new InstantCommand(climb::climbUp));
    driver.button(4).onFalse(new InstantCommand(climb::climbStop));
    driver.button(11).onTrue(new InstantCommand(intakeArm::toggleNoteSensor));
    driver.button(12).onTrue(new InstantCommand(intakeArm::resetNoteSensor));
  };
}