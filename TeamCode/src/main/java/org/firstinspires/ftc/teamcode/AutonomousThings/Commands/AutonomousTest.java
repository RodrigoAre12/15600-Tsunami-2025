package org.firstinspires.ftc.teamcode.AutonomousThings.Commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.AutonomousThings.Trayectories.RedTrayectories;
import org.firstinspires.ftc.teamcode.AutonomousThings.Trayectories.TrayectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.Subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class AutonomousTest extends SequentialCommandGroup {

    RedTrayectories redTrayectories = new RedTrayectories();

    public AutonomousTest(SampleMecanumDrive drive, MecanumDriveSubsystem driveSubsystem) {
        addCommands(new TrayectoryFollowerCommand(driveSubsystem, redTrayectories.goToHighChamber(drive)),
                new TrayectoryFollowerCommand(driveSubsystem, redTrayectories.goForSample(drive)),
                new TrayectoryFollowerCommand(driveSubsystem, redTrayectories.leaveHighBasket(drive)),
                new TrayectoryFollowerCommand(driveSubsystem, redTrayectories.leaveHighBasket(drive)),
                new TrayectoryFollowerCommand(driveSubsystem, redTrayectories.grabThirdSample(drive))
        );
    }
    //hola
}
