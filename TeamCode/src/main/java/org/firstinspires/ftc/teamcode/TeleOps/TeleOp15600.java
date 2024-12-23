package org.firstinspires.ftc.teamcode.TeleOps;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.Subsystem.Brazo1;
import org.firstinspires.ftc.teamcode.Subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp
public class TeleOp15600 extends CommandOpMode {


    @Override
    public void initialize() {
        GamepadEx chassisDriver = new GamepadEx(gamepad1);
        GamepadEx gamepadSystem = new GamepadEx(gamepad2);

        SampleMecanumDrive sampleMecanumDrive = new SampleMecanumDrive(hardwareMap);
        MecanumDriveSubsystem driveSystem = new MecanumDriveSubsystem(sampleMecanumDrive, true, false);
        Brazo1 m_brazo1 = new Brazo1(hardwareMap, telemetry);

        //Outake
        chassisDriver.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(m_brazo1::intakeRoller)
                .whenReleased(m_brazo1::stopRoller);

        driveSystem.setDefaultCommand(new MecanumDriveCommand(
                driveSystem, () -> -chassisDriver.getLeftY(), chassisDriver::getLeftX, chassisDriver::getRightX
        ));

        schedule(new RunCommand(()-> {
            driveSystem.getPoseEstimate();
            driveSystem.update();
            telemetry.addData("Heading", driveSystem.getPoseEstimate());
            telemetry.addData("Heading", driveSystem.getPoseEstimate().getHeading());
            telemetry.update();
        }));


    }




}
