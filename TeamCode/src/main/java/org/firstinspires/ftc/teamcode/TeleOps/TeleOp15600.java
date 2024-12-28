package org.firstinspires.ftc.teamcode.TeleOps;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
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
        MecanumDriveSubsystem driveSystem = new MecanumDriveSubsystem(sampleMecanumDrive, true, true);
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
                                , new InstantCommand(()->m_extentionArm.goToPosition(400))
                        ));

        chassisDriver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(
                        new SequentialCommandGroup(
                                new InstantCommand(()->m_mainArm.goToPosition(0)),
                                new WaitCommand(500)
                                , new InstantCommand(()->m_extentionArm.goToPosition(0))
                        ));

        //Intake
        chassisDriver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(
                        new ConditionalCommand(
                        new SequentialCommandGroup(
                                 new InstantCommand(()->m_extentionArm.goToPosition(3500)),
                                 new WaitCommand(200),
                                 new InstantCommand(()->m_mainArm.goToPosition(700)),
                                 new RunCommand(()-> m_intake.setPower(1)))
                                ,
                                new SequentialCommandGroup(
                                        new InstantCommand(()->m_extentionArm.goToPosition(3800)),
                                        new WaitCommand(200),
                                        new InstantCommand(()->m_mainArm.goToPosition(700)),
                                        new RunCommand(()-> m_intake.setPower(1))),
                                ()-> m_mainArm.arm_state == ArmSubsystem.ArmState.OutsideSubmersible))

                .whenReleased(
                        new ConditionalCommand(
                        new ParallelCommandGroup(
                        new InstantCommand(()->m_intake.setPower(0)),
                        new InstantCommand(()->m_mainArm.goToPosition(0))
                        , new InstantCommand(()->m_extentionArm.goToPosition(400))
                )
                        ,
                                new SequentialCommandGroup(
                                        new InstantCommand(()->m_extentionArm.goToPosition(3100)),
                                        new InstantCommand(()->m_mainArm.goToPosition(700)),
                                        new WaitCommand(1000),
                                        new InstantCommand(()->m_intake.setPower(0)),
                                        new InstantCommand(()->m_mainArm.goToPosition(0)),
                                        new InstantCommand(()->m_mainArm.changeArmState(ArmSubsystem.ArmState.OutsideSubmersible)),
                                        new InstantCommand(()->m_extentionArm.goToPosition(400)))
                ,()-> m_mainArm.arm_state == ArmSubsystem.ArmState.OutsideSubmersible));

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
                                new InstantCommand(()->m_mainArm.goToPosition(900))));

        chassisDriver.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(()-> m_extentionArm.goToPosition(900));

        chassisDriver.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                        .whenPressed(()->m_mainArm.goToPosition(800))
                        .whenPressed(()-> m_extentionArm.goToPosition(1300));

        chassisDriver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(new ParallelCommandGroup(
                                new InstantCommand(()-> m_mainArm.goToPosition(1750)),
                                new InstantCommand(()-> m_extentionArm.goToPosition(2200))
                        ));

        chassisDriver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                        .whenPressed(()->m_intake.setPower(-1))
                .whenReleased(
        new SequentialCommandGroup(
                new InstantCommand(()->m_intake.setPower(0)),
                new InstantCommand(()->m_extentionArm.goToPosition(3000)),
                new WaitCommand(500),
                new InstantCommand(()->m_mainArm.goToPosition(0)),
                new WaitCommand(300),
                new InstantCommand(()->m_extentionArm.goToPosition(900))
        ));


        driveSystem.setDefaultCommand(new MecanumDriveCommand(
                driveSystem, () -> -chassisDriver.getLeftY(), chassisDriver::getLeftX, chassisDriver::getRightX
        ));


        schedule(new RunCommand(()-> {
            driveSystem.update();
            telemetry.addData("Heading", driveSystem.getPoseEstimate().getHeading());
            telemetry.update();
        }));


    }




}
