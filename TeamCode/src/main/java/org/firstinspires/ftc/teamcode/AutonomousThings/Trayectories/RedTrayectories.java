package org.firstinspires.ftc.teamcode.AutonomousThings.Trayectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class RedTrayectories {

    public Trajectory goToHighChamber(SampleMecanumDrive drive){
        Pose2d firstPose = new Pose2d(11.66, 71.02, Math.toRadians(270));
        return drive.trajectoryBuilder(firstPose)
                .forward(20)
                .build();
    }

    public Trajectory goForSample(SampleMecanumDrive drive){
        return drive.trajectoryBuilder(new Pose2d(1.66, 57.06, Math.toRadians(270)))
                .lineToLinearHeading(new Pose2d(57.61, 57.06, Math.toRadians(225)))
                .build();
    }

    public Trajectory leaveHighBasket(SampleMecanumDrive drive){
        return drive.trajectoryBuilder(new Pose2d(57.61, 57.06, Math.toRadians(225)))
                .lineToLinearHeading(new Pose2d(59.71, 38.62, Math.toRadians(225)))
                .build();
    }

    public Trajectory grabSecondSample(SampleMecanumDrive drive){
        return drive.trajectoryBuilder(new Pose2d(59.71, 38.62, Math.toRadians(225)))
                .lineToLinearHeading(new Pose2d(51.33, 38.48, Math.toRadians(50)))
                .build();
    }

    public Trajectory grabThirdSample(SampleMecanumDrive drive){
        return drive.trajectoryBuilder(new Pose2d(51.33, 38.48, Math.toRadians(50)))
                .lineToLinearHeading(new Pose2d(64.04, 32.33, Math.toRadians(-50)))
                .build();
    }


}
