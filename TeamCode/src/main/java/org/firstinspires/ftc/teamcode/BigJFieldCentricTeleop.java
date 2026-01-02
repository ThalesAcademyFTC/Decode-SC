package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp(name="BigJFieldCentric")
public class BigJFieldCentricTeleop extends LinearOpMode{
    private Johnny9 johnny9;
    //AprilTagPoseFtc ftcPose = johnny9.getPos();
    AprilTagPoseFtc ftcPose = null;
    Pose3D robotPose = null;
    private ElapsedTime runtime = new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    double launchPos;
    boolean ftcTag=true;
    public void runOpMode() throws InterruptedException{

        johnny9.launcherMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Adjust the orientation parameters to match your robot



        waitForStart();
        if(isStopRequested()) return;
        while(opModeIsActive()){
            launchPos=johnny9.launcherMotor.getCurrentPosition();
            double y=-gamepad1.left_stick_y;
            double x=gamepad1.left_stick_x;
            double rx=gamepad1.right_stick_x;


            if(gamepad1.options){
                johnny9.imu.resetYaw();
            }
            double botHeading=johnny9.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double rotX=x*Math.cos(-botHeading)-y*Math.sin(-botHeading);
            double rotY=x*Math.sin(-botHeading)+y*Math.cos(-botHeading);

            rotX=rotX*1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            johnny9.motorFrontLeft.setPower(frontLeftPower);
            johnny9.motorFrontRight.setPower(frontRightPower);
            johnny9.motorBackLeft.setPower(backLeftPower);
            johnny9.motorBackRight.setPower(backRightPower);
            telemetry.addData("Launcher Encoder Pos:", launchPos);
            telemetry.update();
        }


    }

}
