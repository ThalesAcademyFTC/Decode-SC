package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous
public class AprilTagAutonTest extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        runtime.reset();


        waitForStart();
        // if id21 - move left
        // if id22 - move right
        // if id23 - move forward
        if(opModeIsActive()) {
            while(opModeIsActive() && obValue == UNKNOWN) {
                obValue = johnny9.getObelisk();
                switch (obValue) {
                    case GPP:
                        telemetry.addLine("found green, purple, purple");
                        //johnny9.moveLeftInches(12, speed);
                        break;
                    case PGP:
                        telemetry.addLine("found purple, green, purple");
                        //johnny9.moveRightInches(12, speed);
                        break;
                    case PPG:
                        telemetry.addLine("found purple, purple, green");
                        //johnny9.moveForwardInches(12, speed);
                        break;
                    case UNKNOWN:
                        telemetry.addLine("null");
                        johnny9.rest();
                        break;
                }


                telemetry.update();

            }
            blackboard.put(OBELISK_VALUE_STRING,obValue);

        }

        johnny9.visionPortal.close();

    }

}
