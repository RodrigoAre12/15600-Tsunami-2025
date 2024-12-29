package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem extends SubsystemBase {

    private final DcMotorEx mainRight;
    private final DcMotorEx mainLeft;


    HardwareMap hardwareMap;
    Telemetry telemetry;

    private PIDController m_controller = new PIDController(0.00036,0,0.000035);
    private double outputMain = 0;
    public enum ArmState{
        InsideSubmersible,
        OutsideSubmersible
    }

    public ArmState arm_state = ArmState.OutsideSubmersible;

    public ArmSubsystem(Telemetry telemetry, HardwareMap hardwareMap){
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        mainRight = hardwareMap.get(DcMotorEx.class, "mainRight");
        mainRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mainRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mainRight.setDirection(DcMotorSimple.Direction.REVERSE);

        mainLeft = hardwareMap.get(DcMotorEx.class,"mainLeft");
        mainLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mainLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mainLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        m_controller.reset();
    }

    public void goToPosition(int position){
     m_controller.setSetPoint(position);
    }

    public void resetTicks(){
        mainLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mainRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        mainRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mainLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void changeArmState(ArmState state){
        arm_state = state;
    }

    @Override
    public void periodic(){
        outputMain = -m_controller.calculate(mainRight.getCurrentPosition());
        mainRight.setPower(outputMain);
        mainLeft.setPower(-outputMain);
        telemetry.addData("Setpoint",m_controller.getSetPoint());
        telemetry.addData("Output val", outputMain);
        telemetry.addData("REV encoder val", mainRight.getCurrentPosition());
        telemetry.addData("Error",m_controller.getPositionError());

    }
}
