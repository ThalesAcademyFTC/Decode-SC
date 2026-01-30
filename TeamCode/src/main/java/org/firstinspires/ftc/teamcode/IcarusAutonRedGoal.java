package org.firstinspires.ftc.teamcode;

import static android.os.SystemClock.sleep;
import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
@Autonomous
public class IcarusAutonRedGoal extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;

    public static final double LEEWAYY =6;
    public static final double LEEWAYX =3;
    public static final double TURNLEEWAY = 2.5;
    public static final double DISTANCELEEWAYY =2;
    public static final double DISTANCEPERFECTY =45;
    public static final double INTAKEPERFECTX=-25;
    public static final double INTAKEPERFECTY=65;
    public static final double INTAKESETUPTURN=-45;
    public static final double INTAKEPERFECTTURN=55;


    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        johnny9.findLauncherZero();
        runtime.reset();
        double speed=0.5;
        int rest=100;
        boolean sightToogle=false;
        boolean taskToogle =false;

        waitForStart();
        if(opModeIsActive()) {
            // Fire until it doesn't detect any balls (Check if the function ends early)
            johnny9.Led.setPosition(johnny9.GREENPOS);
            johnny9.moveLeftInches(4.5,.5);
            johnny9.runIntakeBallSnatch(1);
            sleep(1200);
            johnny9.runIntakeBallSnatch(1);
            sleep(1200);
            johnny9.runIntakeBallSnatch(1);
            sleep(1200);
            // Backs up a lil to get ready
            johnny9.Led.setPosition(johnny9.BLUEPOS);
            while (Math.abs(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - DISTANCEPERFECTY) >= DISTANCELEEWAYY) {
                johnny9.moveRightInches(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - DISTANCEPERFECTY, 1);
            }
            AprilTagPoseFtc ftcPose=johnny9.getPos(24);
            if(ftcPose != null) {
                johnny9.turnLeftDegrees(ftcPose.yaw - INTAKESETUPTURN, .7);
            }
            // Manages the Turn, Y, and X values, moving and fixing them up in that order.
            //-25 65 55
            while (!taskToogle) {
                telemetry.addData("Distance %f", johnny9.distanceSensor.getDistance(DistanceUnit.INCH));
                ftcPose = johnny9.getPos(24);
                telemetry.update();
                if (ftcPose != null) {
                    if (Math.abs(ftcPose.yaw-INTAKEPERFECTTURN)>TURNLEEWAY){
                        johnny9.turnLeftDegrees(ftcPose.yaw-INTAKEPERFECTTURN, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if(Math.abs(ftcPose.y-INTAKEPERFECTY)>LEEWAYY) {
                        johnny9.moveRightInches(ftcPose.y-INTAKEPERFECTY, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (Math.abs(ftcPose.x-INTAKEPERFECTX)>LEEWAYX) {
                        johnny9.moveBackwardInches(ftcPose.x, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else {
                        taskToogle = true;
                    }
                } else {
                    // No tag detected
                    johnny9.Led.setPosition(johnny9.REDPOS);
                    sleep(rest);
                }
            johnny9.turnLeftDegrees(180, .8);
            sleep(rest);
            johnny9.intakeSystem(speed);
            johnny9.moveForwardInches(8,.3);
            johnny9.intakeSystem(speed);
            sleep(rest);
            johnny9.turnLeftDegrees(180,.8);
            taskToogle=false;
            while (!taskToogle) {
            // search for goal april tag and turn robot to point straight at it, basic fire setup code

                telemetry.addData("Distance %f", johnny9.distanceSensor.getDistance(DistanceUnit.INCH));
                telemetry.update();
                ftcPose = johnny9.getPos(24);
                if (ftcPose != null) {
                    telemetry.addData("X: %f", ftcPose.x);
                    telemetry.addData("Yaw: %f", ftcPose.yaw);
                    telemetry.update();
                    if (ftcPose.yaw > TURNLEEWAY || ftcPose.yaw < -TURNLEEWAY) {
                        johnny9.turnLeftDegrees(ftcPose.yaw, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (ftcPose.x > LEEWAYX || ftcPose.x < -LEEWAYX) {
                        johnny9.moveBackwardInches(ftcPose.x, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) > LEEWAYY) {
                        sightToogle = true;
                        if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) >= 300) {
                            johnny9.moveRightInches(50, speed);
                        } else {
                            johnny9.moveRightInches((johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - LEEWAYY), speed);
                        }
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else {
                        taskToogle = true;
                    }
                // If it can't see the april tag but knows it's aligned, keep moving forward.
                // This is because it can't see if it's too close.
                } else if (sightToogle) {
                    if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) > LEEWAYY) {
                        sightToogle = true;
                        if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) >= 300) {
                            johnny9.moveRightInches(50, speed);
                        } else {
                            johnny9.moveRightInches((johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - LEEWAYY), speed);
                        }
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else {
                        taskToogle = true;
                    }
                } else {
                    // No tag detected
                    johnny9.Led.setPosition(johnny9.REDPOS);
                    sleep(rest);
                }
            }
            //FIRE
            sleep(rest);
            johnny9.Led.setPosition(johnny9.GREENPOS);
            while (opModeIsActive()) {
                johnny9.runIntakeBallSnatch(1);
            }

        }

            johnny9.visionPortal.close();
        }

    }
}
