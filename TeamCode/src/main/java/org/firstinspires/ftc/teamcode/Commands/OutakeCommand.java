package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeServoSubsystem;

public class OutakeCommand extends CommandBase {

    IntakeServoSubsystem m_intake;
    ArmSubsystem m_arm;
    GamepadEx gamepad;

    public OutakeCommand(IntakeServoSubsystem m_intake, ArmSubsystem m_arm,GamepadEx gamepad){
        this.m_intake = m_intake;
        this.gamepad = gamepad;
        this.m_arm = m_arm;
    }

    @Override
    public void execute() {
        if(gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>.5){
            m_intake.setPower(-1);
            m_arm.changeArmState(ArmSubsystem.ArmState.OutsideSubmersible);
        }
    }
}
