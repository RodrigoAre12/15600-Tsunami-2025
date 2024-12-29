package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmExtensionSubsystem extends SubsystemBase {

    private final DcMotorEx extensionRight;
    private final DcMotorEx extensionLeft;

    HardwareMap hardwareMap;
    Telemetry telemetry;

    private boolean useZero = false;

    private PIDController m_controllerExtention = new PIDController(0.0005,0,0);
    private double outputExtension = 0;

    public enum ExtentionState{
        LEAVE_CHAMBER,
        NORMAL
    }

    public ArmExtensionSubsystem(Telemetry telemetry, HardwareMap hardwareMap){
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        extensionRight = hardwareMap.get(DcMotorEx.class, "extensionDer");
        extensionRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extensionRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extensionRight.setDirection(DcMotorSimple.Direction.REVERSE);

        extensionLeft = hardwareMap.get(DcMotorEx.class, "extensionIzq");
        extensionLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extensionLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extensionLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        m_controllerExtention.reset();
    }


    public void goToPosition(int position){
        m_controllerExtention.setSetPoint(position);
    }

    public void setZeroValue(boolean v){
        useZero = v;
    }

    public void resetTicks(){
        extensionLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extensionLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extensionRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void periodic(){

        outputExtension = m_controllerExtention.calculate(extensionRight.getCurrentPosition());
        extensionRight.setPower(useZero ? 0 : outputExtension);
        extensionLeft.setPower(useZero ? 0 : -outputExtension);
        telemetry.addData("Setpoint extention",m_controllerExtention.getSetPoint());
        telemetry.addData("Output extention val", outputExtension);
        telemetry.addData("REV extention encoder val", extensionRight.getCurrentPosition());
        telemetry.addData("Error extention",m_controllerExtention.getPositionError());
    }
}
