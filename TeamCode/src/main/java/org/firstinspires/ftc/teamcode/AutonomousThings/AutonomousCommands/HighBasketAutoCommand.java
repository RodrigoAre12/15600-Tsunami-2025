package org.firstinspires.ftc.teamcode.AutonomousThings.AutonomousCommands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Subsystem.ArmExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeServoSubsystem;

public class HighBasketAutoCommand extends SequentialCommandGroup {

    private final ArmSubsystem m_arm;
    private final ArmExtensionSubsystem m_armExtension;

    public HighBasketAutoCommand(ArmSubsystem arm, ArmExtensionSubsystem armExtension){
        this.m_arm = arm;
        this.m_armExtension = armExtension;

        addRequirements(m_arm, m_armExtension);
    }


    @Override
    public boolean isFinished() {
        return false;
    }
}
