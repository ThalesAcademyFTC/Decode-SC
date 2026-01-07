package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous
public class AprilTagAutonTest extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;

    //public static final double FIRINGPERFECTY=90;
    //public static final double FIRINGLEEWAYY=10;
    //public static final double FIRINGPERFECTX=-60;
    public static final double FIRINGLEEWAYX=5;
    public static final double TURNLEEWAY = 2.5;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        runtime.reset();
        double speed=0.5;
        int rest=100;

        waitForStart();
        if(opModeIsActive()) {
            /*while(opModeIsActive() && obValue == UNKNOWN) {
                obValue = johnny9.getObelisk();
                switch (obValue) {
                    case GPP:
                        telemetry.addLine("found green, purple, purple");
                        break;
                    case PGP:
                        telemetry.addLine("found purple, green, purple");
                        break;
                    case PPG:
                        telemetry.addLine("found purple, purple, green");
                        break;
                    case UNKNOWN:
                        telemetry.addLine("null");
                        johnny9.rest();
                        break;
                }
                telemetry.update();
            }*/
            AprilTagPoseFtc ftcPose;
            while(opModeIsActive()) {
                // search for goal april tag and turn robot to point straight at it

                ftcPose = johnny9.getPos();

                if (ftcPose != null) {
                    telemetry.addData("Yaw: %f", ftcPose.yaw);
                    if(ftcPose.yaw>TURNLEEWAY || ftcPose.yaw<-TURNLEEWAY){
                        johnny9.turnLeftDegrees(ftcPose.yaw,speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (ftcPose.x>FIRINGLEEWAYX || ftcPose.x<-FIRINGLEEWAYX){
                        johnny9.moveRightInches(ftcPose.x-FIRINGLEEWAYX, speed);
                        johnny9.Led.setPosition((johnny9.BLUEPOS));
                    } else if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH)<=6){
                        johnny9.moveForwardInches(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - 3, speed);
                        johnny9.Led.setPosition((johnny9.BLUEPOS));
                    /*else if(ftcPose.y>FIRINGPERFECTY+FIRINGLEEWAYY || ftcPose.y<FIRINGPERFECTY-FIRINGLEEWAYY){
                        johnny9.moveForwardInches(ftcPose.y-FIRINGPERFECTY,speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    }
                    else if (ftcPose.x>FIRINGPERFECTX+FIRINGLEEWAYX || ftcPose.x<FIRINGPERFECTX-FIRINGLEEWAYX){
                        johnny9.moveRightInches(ftcPose.x-FIRINGPERFECTX,speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);*/
                    } else {
                        sleep(rest);
                        johnny9.Led.setPosition(johnny9.GREENPOS);
                    }
                } else {
                    // No tag detected
                    johnny9.Led.setPosition(johnny9.REDPOS);
                    sleep(rest);
                }
                telemetry.update();
            }//e
            blackboard.put(OBELISK_VALUE_STRING,obValue);
            if(johnny9.getTag()==24)//red team start on launch line bottom
            {
                if(johnny9.getObelisk()==GPP){
                    // if statement saying if the color is green, put it in the launcher
                }
                else if(johnny9.getObelisk()==PPG){
                    //if statement saying if the first color is purple
                }
                johnny9.moveForwardInches(72, speed);
                sleep(rest);
                johnny9.turnRightDegrees(45, speed);
                sleep(rest);
                johnny9.launchTime(speed);
                sleep(rest);
                johnny9.moveBackwardInches(18,speed);
                sleep(rest);

            }

        }
        johnny9.visionPortal.close();

    }
}
