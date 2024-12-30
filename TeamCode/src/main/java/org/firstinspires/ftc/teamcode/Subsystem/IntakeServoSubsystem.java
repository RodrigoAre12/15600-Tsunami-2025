package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeServoSubsystem extends SubsystemBase {

    HardwareMap hardwareMap;
    Telemetry telemetry;
    CRServo ser1;
    ColorSensor color;

    public IntakeServoSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        ser1 = hardwareMap.get(CRServo.class, "ser1");
        ser1.setDirection(DcMotorSimple.Direction.REVERSE);
        /*   1  = Intake  -1 = Outake*/
        color = hardwareMap.get(ColorSensor.class, "color");

    }

    public  void setPower(double power){
        ser1.setPower(power);
    }

    public ColorSensor getColorSensor(){
        return color;
    }

    public boolean getRed(){
        return color.red() > 1000;
    }

    public boolean getBlue(){
        return color.blue() > 2000;
    }

    public boolean getGreen(){
        return color.green() > 2000;
    }

    @Override
    public  void periodic() {
        telemetry.addData("Blue", color.blue());
        telemetry.addData("Red", color.red());
        telemetry.addData("Yellow",color.green());
        telemetry.update();
    }
}