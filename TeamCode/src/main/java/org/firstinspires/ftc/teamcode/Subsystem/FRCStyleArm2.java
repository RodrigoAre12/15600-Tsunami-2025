package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FRCStyleArm2 extends SubsystemBase {

    private final DcMotorEx linearDer;
    private final DcMotorEx linearIzq;

    HardwareMap hardwareMap;
    Telemetry telemetry;

    private PIDController m_controller = new PIDController(0.0008,0,0);
    private double output = 0;

    public FRCStyleArm2(Telemetry telemetry, HardwareMap hardwareMap){
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        linearDer = hardwareMap.get(DcMotorEx.class, "arm2Leader");
        linearDer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearDer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearDer.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearDer.setDirection(DcMotorSimple.Direction.REVERSE);

        linearIzq = hardwareMap.get(DcMotorEx.class,"arm2Slave");
        linearIzq.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearIzq.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearIzq.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearIzq.setDirection(DcMotorSimple.Direction.FORWARD);

        m_controller.reset();
    }

    public void set1Power(){
        linearDer.setPower(1);
        linearIzq.setPower(1);
    }

    public void set0Power(){
        linearDer.setPower(0);
        linearIzq.setPower(0);
    }

    public void setminus1Power(){
        linearDer.setPower(-1);
        linearIzq.setPower(-1);
    }

    public void goToPosition(int position){
        m_controller.setSetPoint(position);
    }
        //Llamalo en el teleOp y solo pon la posicion, y se ira a esa pose cuando le quites el comentario en el periodic
    @Override
    public void periodic(){
        output = -m_controller.calculate(linearDer.getCurrentPosition());
        //linearDer.setPower(output);
        //linearIzq.setPower(-output);  //Estos para ya son para pid, quitar comentario cuando estes seguro de las potencias
        telemetry.addData("Setpoint",m_controller.getSetPoint());
        telemetry.addData("Output val", output);
        telemetry.addData("REV encoder val", linearDer.getCurrentPosition());
        telemetry.addData("Error",m_controller.getPositionError());
    }
}
