package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@Autonomous
public class IcarusAutonBlue extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;

    public static final double FIRINGLEEWAYY=6;
    public static final double FIRINGLEEWAYX=3;
    public static final double TURNLEEWAY = 2.5;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        runtime.reset();
        double speed=0.5;
        int rest=100;
        boolean sightToogle=false;

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
            //johnny9.moveRightInches(24, .7);
            while(opModeIsActive()) {
                // search for goal april tag and turn robot to point straight at it

                telemetry.addData("Distance %f", johnny9.distanceSensor.getDistance(DistanceUnit.INCH));
                telemetry.update();
                ftcPose = johnny9.getPos(24);
                if (ftcPose != null) {
                    telemetry.addData("X: %f", ftcPose.x);
                    telemetry.addData("Yaw: %f", ftcPose.yaw);
                    telemetry.update();
                    if(ftcPose.yaw>TURNLEEWAY || ftcPose.yaw<-TURNLEEWAY){
                        johnny9.turnLeftDegrees(ftcPose.yaw,speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (ftcPose.x>FIRINGLEEWAYX || ftcPose.x<-FIRINGLEEWAYX){
                        johnny9.moveBackwardInches(ftcPose.x/2.5, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH)>FIRINGLEEWAYY){
                        sightToogle = true;
                        if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH)>=300){
                            johnny9.moveRightInches(50, speed);
                        } else {
                            johnny9.moveRightInches((johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - FIRINGLEEWAYY), speed);
                        }
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else {
                        sleep(rest);
                        johnny9.Led.setPosition(johnny9.GREENPOS);
                        johnny9.runIntakeBallSnatch(1);
                    }
                } else if (sightToogle) {
                    if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH)>FIRINGLEEWAYY){
                        sightToogle = true;
                        if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH)>=300){
                            johnny9.moveRightInches(50, speed);
                        } else {
                            johnny9.moveRightInches((johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - FIRINGLEEWAYY), speed);
                        }
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else {
                        sleep(rest);
                        johnny9.Led.setPosition(johnny9.GREENPOS);
                        johnny9.runIntakeBallSnatch(1);
                    }
                } else {
                    // No tag detected
                    johnny9.Led.setPosition(johnny9.REDPOS);
                    sleep(rest);
                }
            }

        }
        johnny9.visionPortal.close();

    }
}
