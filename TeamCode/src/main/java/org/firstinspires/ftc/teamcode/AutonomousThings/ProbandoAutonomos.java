package org.firstinspires.ftc.teamcode.AutonomousThings;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class ProbandoAutonomos {

    public Trajectory trajectoryTest(SampleMecanumDrive drive){
        return drive.trajectoryBuilder(new Pose2d(24, 24), Math.toDegrees(90))
                .build();
    }
}
