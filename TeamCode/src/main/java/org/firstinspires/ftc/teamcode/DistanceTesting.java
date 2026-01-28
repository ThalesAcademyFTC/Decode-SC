package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@Autonomous
public class DistanceTesting extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public void runOpMode() {
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        johnny9.findLauncherZero();
        runtime.reset();
        double speed = 0.5;
        int rest = 100;
        boolean sightToogle = false;
        boolean taskToogle = false;

        waitForStart();
        while (opModeIsActive()) {
            AprilTagPoseFtc pose = johnny9.getPos(24);
        }
    }
}