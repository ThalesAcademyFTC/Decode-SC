package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp(name="BigJRobotCentric")
public class BigJRobotCentricTeleop extends OpMode {
    private Johnny9 johnny9;
    //AprilTagPoseFtc ftcPose = johnny9.getPos();
    AprilTagPoseFtc ftcPose = null;
    Pose3D robotPose = null;
    private ElapsedTime runtime = new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    boolean resetInProgress = true;
    boolean isBallDetected;


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

    @Override
    public void loop() {

        // Movement
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x / 2;

        y *= -y;
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
            turn /= 3;
        }
        johnny9.move(x, y, turn);

        // Special functions
        // launch trigger(right bumper)
        if (gamepad2.right_bumper) {
            johnny9.launcherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            johnny9.launchTime(1);
        } else if (gamepad2.rightBumperWasReleased()) {
            johnny9.moveToLauncherZero();
            gamepad2.resetEdgeDetection();
        }
        //intake and outtake system
        if (gamepad2.left_bumper) {
            johnny9.intakeSystem(1);
        } else if (gamepad2.dpad_down) {
            johnny9.intakeSystem(-1);
        } else {
            johnny9.intakeSystem(0);
        }
        //automatic intake system
        if (gamepad2.x) {
            johnny9.runIntakeBallReset(1);
        }
        if (gamepad2.dpad_up){
            johnny9.elevate(1);
        }
        if (gamepad2.dpad_right){
            johnny9.launcherKick(.5);
        }


    }
    public void stop(){
        johnny9.visionPortal.close();
    }
}







