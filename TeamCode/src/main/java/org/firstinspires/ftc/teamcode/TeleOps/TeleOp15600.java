package org.firstinspires.ftc.teamcode.TeleOps;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.Subsystem.ArmExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeServoSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@TeleOp
public class TeleOp15600 extends CommandOpMode {
//2350     2800

    @Override
    public void initialize() {
        GamepadEx chassisDriver = new GamepadEx(gamepad1);
        GamepadEx subsystemsDriver = new GamepadEx(gamepad2);

        SampleMecanumDrive sampleMecanumDrive = new SampleMecanumDrive(hardwareMap);
        MecanumDriveSubsystem driveSystem = new MecanumDriveSubsystem(sampleMecanumDrive, false, false);
        ArmSubsystem m_mainArm = new ArmSubsystem(telemetry, hardwareMap);
        ArmExtensionSubsystem m_extentionArm = new ArmExtensionSubsystem(telemetry,hardwareMap);
        IntakeServoSubsystem m_intake = new IntakeServoSubsystem(hardwareMap, telemetry);

        //Leave on baskets
        chassisDriver.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(
                        new SequentialCommandGroup(
                                new InstantCommand(()->m_extentionArm.goToPosition(2400)),
                                new WaitCommand(150
                                )
                                , new InstantCommand(()->m_mainArm.goToPosition(2600))
                        ));

        chassisDriver.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(
                        new SequentialCommandGroup(
                                new InstantCommand(()->m_mainArm.goToPosition(0)),
                                new WaitCommand(500)
                                , new InstantCommand(()->m_extentionArm.goToPosition(0))
                        ));

        //Intake
        chassisDriver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(
                        new SequentialCommandGroup(
                                 new InstantCommand(()->m_extentionArm.goToPosition(3400)),
                                 new WaitCommand(200),
                                 new InstantCommand(()->m_mainArm.goToPosition(700)),
                                 new RunCommand(()-> m_intake.setPower(1))))

                .whenReleased(
                        new ParallelCommandGroup(
                        new InstantCommand(()->m_intake.setPower(0)),
                        new InstantCommand(()->m_mainArm.goToPosition(0))
                        , new InstantCommand(()->m_extentionArm.goToPosition(0))
                ));

        //Chamber
        chassisDriver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(
                        new SequentialCommandGroup(
                                new InstantCommand(()->m_extentionArm.goToPosition(2000)),
                                new WaitCommand(150
                                )
                                , new InstantCommand(()->m_mainArm.goToPosition(1035))
                        ));
        //Reset
        chassisDriver.getGamepadButton(GamepadKeys.Button.START)
                        .whenPressed(
                                new ParallelCommandGroup(
                                        new InstantCommand(
                                                m_mainArm::resetTicks),
                                        new InstantCommand(
                                                m_extentionArm::resetTicks)));

        //Arm positions
        chassisDriver.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(
                        new SequentialCommandGroup(
                                new InstantCommand(()->m_extentionArm.goToPosition(3200)),
                                new WaitCommand(200),
                                new InstantCommand(()->m_mainArm.changeArmState(ArmSubsystem.ArmState.InsideSubmersible)),
                                new InstantCommand(()->m_mainArm.goToPosition(700))));

        chassisDriver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(()->m_intake.setPower(-1))
                .whenReleased(
        new SequentialCommandGroup(
                new InstantCommand(()->m_intake.setPower(0)),
                new InstantCommand(()->m_mainArm.goToPosition(0)),
                new WaitCommand(500)
                , new InstantCommand(()->m_extentionArm.goToPosition(0))
        ));


        driveSystem.setDefaultCommand(new MecanumDriveCommand(
                driveSystem, () -> -chassisDriver.getLeftY(), chassisDriver::getLeftX, chassisDriver::getRightX
        ));


        schedule(new RunCommand(()-> {

            telemetry.update();
        }));


    }




}
