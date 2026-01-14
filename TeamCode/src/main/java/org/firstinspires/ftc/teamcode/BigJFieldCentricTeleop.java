package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
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

    @Override
    public void init() {
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        telemetry.addData("Launcher Pos", () -> {
            return johnny9.launcherMotor.getCurrentPosition();
        });
        telemetry.addData("Launcher Mode",()->{return johnny9.launcherMotor.getMode();});

        telemetry.addData("Ball color: ", () -> {return johnny9.getBallColor();});
        johnny9.initAprilTag();
        johnny9.findLauncherZero();
        johnny9.moveToLauncherZero();
        runtime.reset();
        telemetry.update();

    }


    @Override
    public void loop()  {

       
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;


        if (gamepad1.options) {
            johnny9.imu.resetYaw();
        }
        double botHeading = johnny9.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;

                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;

                johnny9.motorFrontLeft.setPower(frontLeftPower);
                johnny9.motorFrontRight.setPower(frontRightPower);
                johnny9.motorBackLeft.setPower(backLeftPower);
                johnny9.motorBackRight.setPower(backRightPower);

                if(gamepad2.right_bumper){
                    johnny9.launcherMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    johnny9.launchTime(1);
                }
                else if(gamepad2.rightBumperWasReleased() && johnny9.launcherMotor.getCurrentPosition() < 5281){
                    johnny9.moveToLauncherZero();

                }
        if (gamepad2.left_bumper) {
            johnny9.intakeSystem(1);
        } else if (gamepad2.dpad_down) {
            johnny9.intakeSystem(-1);
        } else if (gamepad1.dpad_up) {
            johnny9.elevate(1);
        } else {
            johnny9.intakeSystem(0);
        }
            }


        }

