package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous
public class AprilTagAutonTest extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtine=new ElapsedTime();

    private static final boolean USE_WEBCAM = true;
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        initAprilTag();
        runtime.reset();
        double speed = 0.5;
        int rest = 100;

        waitForStart();
        // if id20 - move left
        // if id21 - move right
        // if id22 - move forward
    }

    private void initAprilTag() {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap
            )
        } else {
    }

}
