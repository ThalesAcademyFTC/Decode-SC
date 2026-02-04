package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp(name="BigJFieldCentric")

public class BigJFieldCentricTeleop extends OpMode {
    private Johnny9 johnny9;
    //AprilTagPoseFtc ftcPose = johnny9.getPos();
    AprilTagPoseFtc ftcPose = null;
    Pose3D robotPose = null;
    private ElapsedTime runtime = new ElapsedTime();

    boolean ftcTag = true;

    boolean isBackDetected =false;

    @Override
    public void init() {
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        telemetry.addData("Launcher Pos", () -> {
            return johnny9.launcherMotor.getCurrentPosition();
        });
        telemetry.addData("Launcher Target Pos", () -> {
            return johnny9.LAUNCHTARGETPOS;
        });
        telemetry.addData("Launcher Mode",()->{return johnny9.launcherMotor.getMode();});
        telemetry.addData("Launcher Power", () -> {
            return johnny9.launcherMotor.getPower();
        });
        telemetry.addData("Dist:", () -> { return johnny9.colorSensor.getDistance(DistanceUnit.MM); });
        telemetry.addData("Ball Detected ",()->{return johnny9.isBallDetected();});


        johnny9.initAprilTag();
        johnny9.findLauncherZero();
        runtime.reset();
        telemetry.update();

    }

    private void driveFieldRelative(double forward, double right, double rotate) {
        // First, convert direction being asked to drive to polar coordinates
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);

        // Second, rotate angle by the angle the robot is pointing
        theta = AngleUnit.normalizeRadians(theta -
                johnny9.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        // Third, convert back to cartesian
        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        // Finally, call the drive method with robot relative forward and right amounts
        johnny9.move( newRight, newForward , rotate);
    }

    @Override
    public void loop() {

        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        if (gamepad1.optionsWasPressed()) {
            johnny9.resetYaw();
        }
        if(gamepad1.back){
            isBackDetected=true;
        }
        y *= y;
        if (gamepad1.left_stick_y > 0) {
            y = -y;
        }
        x *= x;
        if (gamepad1.left_stick_x < 0) {
            x = -x;
        }
        if (gamepad1.right_trigger > 0){
            x /= 3;
            y /= 3;
            rx/=3;
        }
   //     if(johnny9.distanceSensor.getDistance(DistanceUnit.MM))
        /*if (gamepad1.right_trigger > 0){
            // TBD
            frontLeftPower /= 3;
            backLeftPower /= 3;
            frontRightPower /= 3;
            backRightPower /= 3;
        }*/

        driveFieldRelative(y, x, rx);

        if (gamepad2.right_bumper || (isBackDetected && gamepad1.right_bumper)) {
            johnny9.launcherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            johnny9.launchTime(1);
        } else if (gamepad2.rightBumperWasReleased() || gamepad1.rightBumperWasReleased()) {
            johnny9.moveToLauncherZero();
            gamepad2.resetEdgeDetection();
        }
        //intake and outtake system
        if (gamepad2.left_bumper || (isBackDetected && gamepad1.left_bumper)) {
            johnny9.intakeSystem(1);
        } else if (gamepad2.dpad_down || (isBackDetected && gamepad1.dpad_down)) {
            johnny9.intakeSystem(-1);
        } else {
            johnny9.intakeSystem(0);
        }
        //automatic intake system
        if (gamepad2.x) {
            johnny9.runIntakeBallSnatch(1);
        }
        if (gamepad2.dpad_up) {
            johnny9.elevate(1);
        }
        if (gamepad2.dpad_right ||(isBackDetected && gamepad1.dpad_right) ) {
            johnny9.launcherKick(.5);
        }
        if(isBackDetected){
            johnny9.resetYaw();
        }


    }
    public void stop(){
        johnny9.visionPortal.close();
    }
}