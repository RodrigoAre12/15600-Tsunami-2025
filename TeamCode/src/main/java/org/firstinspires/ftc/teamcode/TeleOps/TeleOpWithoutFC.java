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
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Commands.NothingCommandCommand;
import org.firstinspires.ftc.teamcode.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.Subsystem.ArmExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeServoSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp
public class TeleOpWithoutFC extends CommandOpMode {
    //2350     2800

    @Override
    public void initialize() {

        Gamepad.RumbleEffect rumbleEffect = new Gamepad.RumbleEffect.Builder()
                .addStep(0.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.0, 0.0, 300)  //  Pause for 300 mSec
                .build();

        GamepadEx chassisDriver = new GamepadEx(gamepad1);
        GamepadEx subsystemsDriver = new GamepadEx(gamepad2);

        SampleMecanumDrive sampleMecanumDrive = new SampleMecanumDrive(hardwareMap);
        MecanumDriveSubsystem driveSystem = new MecanumDriveSubsystem(sampleMecanumDrive, false, true);
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
                                        new InstantCommand(()-> m_intake.setPower(1)),
                                        new ConditionalCommand(
                                                new InstantCommand(()-> chassisDriver.gamepad.runRumbleEffect(rumbleEffect)),
                                                new NothingCommandCommand(),
                                                ()-> m_intake.getColorSensor().blue() > 1000 || m_intake.getColorSensor().green() > 1000
                                        )
                                )
                                ,
                                new SequentialCommandGroup(
                                        new InstantCommand(()->m_extentionArm.goToPosition(4200)),
                                        new WaitCommand(200),
                                        new InstantCommand(()->m_mainArm.goToPosition(1000)),
                                        new InstantCommand(()-> m_intake.setPower(1)),
                                        new ConditionalCommand(
                                                new InstantCommand(()-> chassisDriver.gamepad.runRumbleEffect(rumbleEffect)),
                                                new NothingCommandCommand(),
                                                ()-> m_intake.getColorSensor().blue() > 1000 || m_intake.getColorSensor().green() > 1000
                                        )),
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
                                        new InstantCommand(()->m_intake.setPower(0)),
                                        new WaitCommand(750),
                                        new InstantCommand(()->m_mainArm.changeArmState(ArmSubsystem.ArmState.OutsideSubmersible)))
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
                                new InstantCommand(()->m_mainArm.goToPosition(800))));

        chassisDriver.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()-> m_extentionArm.goToPosition(950));

        chassisDriver.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON)
                .whenPressed(()->m_mainArm.goToPosition(800))
                .whenPressed(()-> m_extentionArm.goToPosition(1300));

        chassisDriver.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON)
                .whileHeld(new ParallelCommandGroup(
                        new InstantCommand(()-> m_intake.setPower(-1)),
                        new InstantCommand(()-> m_mainArm.changeArmState(ArmSubsystem.ArmState.OutsideSubmersible)))).whenReleased(

                        new SequentialCommandGroup(
                                new InstantCommand(()->m_mainArm.goToPosition(0)),
                                new InstantCommand(()->m_intake.setPower(0)),
                                new InstantCommand(()->m_extentionArm.goToPosition(400)))
                );

        chassisDriver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new ParallelCommandGroup(
                        new InstantCommand(()-> m_mainArm.goToPosition(1700)),
                        new InstantCommand(()-> m_extentionArm.goToPosition(2200))
                ));

        chassisDriver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(()->m_intake.setPower(-1))
                .whenReleased(
                        new SequentialCommandGroup(
                                new InstantCommand(()->m_intake.setPower(0)),
                                new InstantCommand(()->m_extentionArm.goToPosition(3000)),
                                new WaitCommand(500),
                                new InstantCommand(()->m_mainArm.goToPosition(800)),
                                new WaitCommand(300),
                                new InstantCommand(()->m_extentionArm.goToPosition(3200)),
                                new InstantCommand(()-> m_mainArm.changeArmState(ArmSubsystem.ArmState.InsideSubmersible))
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
