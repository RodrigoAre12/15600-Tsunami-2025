package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeServoSubsystem;

public class OutakeCommand extends CommandBase {

    IntakeServoSubsystem m_intake;
    ColorSensor m_sensor;
    boolean isBlueAlliance;

    public OutakeCommand(IntakeServoSubsystem m_intake, ColorSensor m_sensor, boolean isBlueAlliance){
        this.m_intake = m_intake;
        this.m_sensor = m_sensor;
        this.isBlueAlliance = isBlueAlliance;

        addRequirements(m_intake);
    }

    @Override
    public void execute() {
        if(isBlueAlliance ? m_sensor.red() > 1000 : m_sensor.blue() > 1000){
            m_intake.setPower(-1);
        }
        else{
            m_intake.setPower(0);
        }
    }
}
