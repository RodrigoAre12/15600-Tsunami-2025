package org.firstinspires.ftc.teamcode.AutonomousThings.OpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AutonomousThings.Commands.AutonomousTest;
import org.firstinspires.ftc.teamcode.Subsystem.ArmExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class AutonomousOpMode extends CommandOpMode {
    @Override
    public void initialize() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        MecanumDriveSubsystem driveSystem = new MecanumDriveSubsystem(drive, false, false);
        ArmSubsystem m_mainArm = new ArmSubsystem(telemetry,hardwareMap);
        ArmExtensionSubsystem m_extentionArm = new ArmExtensionSubsystem(telemetry,hardwareMap);
      //  AutonomousTest autonomousTest = new AutonomousTest(drive, driveSystem);

        schedule(
                new SequentialCommandGroup(
                new SequentialCommandGroup(
                        new InstantCommand(()->m_extentionArm.goToPosition(2500)),
                        new WaitCommand(150),
                        new InstantCommand(()->m_mainArm.goToPosition(2600))
                ),
                new WaitCommand(5000),

                new SequentialCommandGroup(
                        new InstantCommand(()->m_mainArm.goToPosition(0)),
                        new WaitCommand(500),
                        new InstantCommand(()->m_extentionArm.goToPosition(400))
                ),
                new WaitCommand(5000),

                new SequentialCommandGroup(
                        new InstantCommand(()->m_extentionArm.goToPosition(2500)),
                        new WaitCommand(150),
                        new InstantCommand(()->m_mainArm.goToPosition(2600))
                ),
                new WaitCommand(5000),

                new SequentialCommandGroup(
                        new InstantCommand(()->m_mainArm.goToPosition(0)),
                        new WaitCommand(500),
                        new InstantCommand(()->m_extentionArm.goToPosition(400))
                ),
                new WaitCommand(5000)
        ));
    }
}
