package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="BigJ")
public class AprilTagLocalizationTest extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        runtime.reset();
        boolean ftcTag = true;
        boolean toogle = true;
        boolean triggerPull = false;


        waitForStart();
        if(opModeIsActive()) {
            while(opModeIsActive()) {
                // Movement
                double y = gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double turn = gamepad1.right_stick_x / 2;

                telemetry.update();

                y *= y;
                if (gamepad1.left_stick_y > 0){
                    y =- y;
                }
                x *= x;
                if (gamepad1.left_stick_x < 0){
                    x =- x;
                }
                johnny9.move(x, y, turn);
                // Special functions
                if (gamepad2.right_trigger > 0) {
                    if (toogle){
                       if (triggerPull){
                           triggerPull = false;
                       } else {
                           triggerPull = true;
                       }
                       toogle = false;
                    }
                } else {
                    toogle = true;
                }
                if (triggerPull){
                    johnny9.barrelFire(.3);
                }
                // Localization
                toogle = true;
                if (ftcTag) {
                    johnny9.getPos();
                    telemetry.addLine("Mode: ftcPose");
                    if (gamepad1.x){
                        if (toogle) {
                            ftcTag = false;
                            toogle = false;
                        }
                    } else {
                        toogle = true;
                    }
                } else {
                    johnny9.getRobotPos();
                    telemetry.addLine("Mode: RobotPose");
                    if (gamepad1.x){
                        if (toogle) {
                            ftcTag = true;
                            toogle = false;
                        }
                    } else {
                        toogle = true;
                    }
                }
                telemetry.update();
            }
        }
        johnny9.visionPortal.close();

    }
}
