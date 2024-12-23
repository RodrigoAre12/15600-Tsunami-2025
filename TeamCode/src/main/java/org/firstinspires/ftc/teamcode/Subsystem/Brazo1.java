package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Brazo1 extends SubsystemBase {

    DcMotorEx armMotor1, armMotor2;

    Telemetry telemetry;

    HardwareMap hardwareMap;

    public Brazo1(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        armMotor1 = hardwareMap.get(DcMotorEx.class, "roller");
        armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armMotor2 = hardwareMap.get(DcMotorEx.class, "abs");
        armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void intakeRoller(){
        armMotor1.setPower(1);
    }

    public void outakeRoller(){
        armMotor1.setPower(-1);
    }

    public void stopRoller(){
        armMotor1.setPower(0);
    }

    @Override
    public void periodic(){
        telemetry.addData("Rev Encoder", armMotor1.getCurrentPosition());
        telemetry.addData("Motor Encoder", armMotor2.getCurrentPosition());
        telemetry.update();

    }
}