package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeServoSubsystem extends SubsystemBase {

    HardwareMap hardwareMap;
    Telemetry telemetry;
    CRServo ser1;

    public IntakeServoSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        ser1 = hardwareMap.get(CRServo.class, "ser1");
        ser1.setDirection(DcMotorSimple.Direction.REVERSE);
        /*   1  = Intake  -1 = Outake*/

    }

    public  void setPower(double power){
        ser1.setPower(power);
    }

    @Override
    public  void periodic() {
    }
}