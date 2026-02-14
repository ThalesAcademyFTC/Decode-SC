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
public class IcarusAutonBlueGoal extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    // Sets values here to be more easily changed
        // The only difference between the two autons is INTAKEPERFECTURN (Which is opposite).
    public static final double LEEWAYY = 1.5;
    public static final double LEEWAYX = 5;
    public static final double TURNLEEWAY = 5;
    public static final double FIRINGY = 5.5;
    public static final double INTAKESETUPY=47;
    public static final double INTAKETURN=124;
    public static final double INTAKEX=27.5;
    public static final double INTAKEFORWARD=27.5;
    public static final int APRILTAGCODE=20;


    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        johnny9.findLauncherZero();
        runtime.reset();
        double speed=1;
        int rest=100;
        boolean sightToogle=false;
        boolean taskToogle =false;
        AprilTagPoseFtc ftcPose;

        waitForStart();
        if(opModeIsActive()) {
            // Moves back to firing position and fires three times.
            johnny9.Led.setPosition(johnny9.GREENPOS);
            johnny9.moveLeftInches(FIRINGY, speed);
            johnny9.runIntakeBallSnatch(1);
            johnny9.runIntakeBallSnatch(1);
            johnny9.runIntakeBallSnatch(1);
            // Moves backwards to a position where it can see the april tag.
                // Then aligns to a more proper y position so the next steps are more accurate.
            johnny9.Led.setPosition(johnny9.BLUEPOS);
            johnny9.moveLeftInches(24,speed);
            while (taskToogle){
                ftcPose = johnny9.getPos(APRILTAGCODE);
                if(ftcPose != null){
                    if(Math.abs(ftcPose.y-INTAKESETUPY)>TURNLEEWAY){
                        johnny9.moveForwardInches(ftcPose.y-INTAKESETUPY, speed);
                    } else {
                        taskToogle = false;
                    }
                }
            }
            // Then, align the x and yaw to face the goal.
            ftcPose = johnny9.getPos(APRILTAGCODE);
            if (Math.abs(ftcPose.yaw) > TURNLEEWAY) {
                johnny9.turnRightDegrees(ftcPose.yaw, speed);
            }
            ftcPose = johnny9.getPos(APRILTAGCODE);
            if (Math.abs(ftcPose.x) > LEEWAYX){
                johnny9.moveForwardInches(ftcPose.x, speed);
            }
            // Resets the yaw, this is now the 0.
                // Then turns to proper intake position and moves to align with the balls.
            johnny9.resetYaw();
            johnny9.turnRightDegrees(johnny9.getHeading()-INTAKETURN,speed);
            johnny9.moveRightInches(INTAKEX, speed);
            /* Unused intake alignment tool.
            //Manages the Turn, Y, and X values, moving and fixing them up in that order.

            while (!taskToogle) {
                telemetry.addData("Distance %f", johnny9.distanceSensor.getDistance(DistanceUnit.INCH));
                ftcPose = johnny9.getPos(APRILTAGCODE);
                telemetry.update();
                if (ftcPose != null) {
                    if (Math.abs(ftcPose.yaw - INTAKEPERFECTTURN) > TURNLEEWAY) {
                        johnny9.turnLeftDegrees(INTAKEPERFECTTURN-ftcPose.yaw, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (Math.abs(ftcPose.x - INTAKEPERFECTX) > LEEWAYX) {
                        johnny9.moveBackwardInches(INTAKEPERFECTX-ftcPose.x, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (Math.abs(ftcPose.y - INTAKEPERFECTY) > LEEWAYY) {
                        johnny9.moveLeftInches(INTAKEPERFECTY-ftcPose.y, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else {
                        taskToogle = true;
                    }
                } else {
                    // No tag detected
                    johnny9.Led.setPosition(johnny9.REDPOS);
                }
            }*/

            // Intake turns on, moves forward to get the balls
                // Then goes back and turns to face the goal again.
            johnny9.intakeSystem(1);
            johnny9.moveForwardInches(INTAKEFORWARD,.2);
            johnny9.intakeSystem(0);
            johnny9.moveBackwardInches(INTAKEFORWARD, speed);
            johnny9.turnLeftDegrees(johnny9.getHeading()-INTAKETURN, speed);
            taskToogle = true;
            // Loops to check the april tag and distance sensor to align the robot accordingly.
                // (Further details inside)
            while (taskToogle) {
                telemetry.addData("Distance %f", johnny9.distanceSensor.getDistance(DistanceUnit.INCH));
                telemetry.update();
                ftcPose = johnny9.getPos(APRILTAGCODE);
                if (ftcPose != null) {
                    telemetry.addData("X: %f", ftcPose.x);
                    telemetry.addData("Yaw: %f", ftcPose.yaw);
                    telemetry.update();
                    // If the yaw is inaccurate, turn to fix it.
                    if (Math.abs(ftcPose.yaw) > TURNLEEWAY) {
                        johnny9.turnRightDegrees(ftcPose.yaw, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                        // If the left/right coordinates are too far from 0, move forward by the current x.
                            // Moving forward because the robot is turned 90 degrees.
                            // Moves a bit slower because it was sliding too much.
                    } else if (Math.abs(ftcPose.x) > LEEWAYX) {
                        johnny9.moveForwardInches(ftcPose.x, speed*.75);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                        // Do the same thing for the distance, using the sensor to check.
                            // If outside of the sensor's range (around 50 inches), defaults to 50.
                            // Also tags sight toggle, as we now know the x and yaw are already aligned.
                    } else if (Math.abs(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - FIRINGY) > LEEWAYY) {
                        sightToogle = true;
                        if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) >= 300) {
                            johnny9.moveRightInches(50, speed);
                        } else {
                            johnny9.moveRightInches((Math.abs(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - FIRINGY)), speed);
                        }
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                        // If the yaw, x, and y are all accurate, end the loop.
                    } else {
                        taskToogle = false;
                    }
                    // If it can't see the april tag but knows it's aligned, keep moving forward.
                        // This is because the robot can't see the x and y if it is too close.
                } else if (sightToogle) {
                    // Same distance sensor code as above.
                    if (Math.abs(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - FIRINGY) > LEEWAYY) {
                        sightToogle = true;
                        if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) >= 300) {
                            johnny9.moveRightInches(50, speed);
                        } else {
                            johnny9.moveRightInches((Math.abs(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - FIRINGY)), speed);
                        }
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                        // Ends the loop if the distance is correct, as we already know the x and yaw are.
                    } else {
                        taskToogle = false;
                    }
                } else {
                    // No tag detected
                    johnny9.Led.setPosition(johnny9.REDPOS);
                }
            }
            // Fire until the time is up.
            johnny9.Led.setPosition(johnny9.GREENPOS);
            while (!isStopRequested()) {
                johnny9.runIntakeBallSnatch(1);
            }
        }
        johnny9.visionPortal.close();
    }
}
