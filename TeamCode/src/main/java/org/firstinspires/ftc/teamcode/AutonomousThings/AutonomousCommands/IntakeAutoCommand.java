package org.firstinspires.ftc.teamcode.AutonomousThings.AutonomousCommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Subsystem.ArmExtensionSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeServoSubsystem;

public class IntakeAutoCommand extends CommandBase {
    
    private final IntakeServoSubsystem m_intake;
    private final ArmSubsystem m_arm;
    private final ArmExtensionSubsystem m_armExtension;
    
    public IntakeAutoCommand(IntakeServoSubsystem intake, ArmSubsystem arm, ArmExtensionSubsystem armExtension){
        this.m_intake = intake;
        this.m_arm = arm;
        this.m_armExtension = armExtension;
        
        addRequirements(m_intake, m_arm, m_armExtension);
    }
    
    @Override
    public void execute() {
        m_armExtension.goToPosition(3500);
        m_arm.goToPosition(700);
        m_intake.setPower(1);
    }
    
    @Override
    public void end(boolean interrupted) {
        m_intake.setPower(0);
        m_arm.goToPosition(0);
        m_armExtension.goToPosition(400);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
}
