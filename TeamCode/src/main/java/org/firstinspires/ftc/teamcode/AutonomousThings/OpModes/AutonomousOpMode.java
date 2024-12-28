package org.firstinspires.ftc.teamcode.AutonomousThings.OpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AutonomousThings.Commands.AutonomousTest;
import org.firstinspires.ftc.teamcode.Subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class AutonomousOpMode extends CommandOpMode {
    @Override
    public void initialize() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        MecanumDriveSubsystem driveSystem = new MecanumDriveSubsystem(drive, false, false);
        AutonomousTest autonomousTest = new AutonomousTest(drive, driveSystem);

        schedule(autonomousTest);
    }
}
