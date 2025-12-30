package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp(name="BigJTeleop")
public class BigJRobotCentricTeleop extends LinearOpMode {
    private Johnny9 johnny9;
    AprilTagPoseFtc ftcPose = null;
    Pose3D robotPose = null;
    private ElapsedTime runtime = new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";

    @Override
    public void runOpMode() {
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        //johnny9.initAprilTag();
        runtime.reset();


        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                // Movement
                double y = gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double turn = gamepad1.right_stick_x / 2;

                telemetry.update();

                y *= y;
                if (gamepad1.left_stick_y < 0) {
                    y = -y;
                }
                x *= x;
                if (gamepad1.left_stick_x < 0) {
                    x = -x;
                }
                johnny9.move(x, y, turn);

                // Special functions
                // launch trigger(right trigger)
                if (gamepad2.right_trigger > 0 /*&& johnny9.Led.getPosition() == Johnny9.GREENPOS*/) {
                    johnny9.launchTime(1);
                } else {
                    johnny9.launchTime(0);
                }

                //intake system(left trigger)
                if (gamepad2.left_trigger > 0) {
                    johnny9.intakeSystem(1);
                }
                //output system
                else if (gamepad2.leftBumperWasPressed()) {
                    johnny9.intakeSystem(-1);
                } else {
                    johnny9.intakeSystem(0);
                }

                if (gamepad2.dpad_up){
                    johnny9.elevate(1);
                } else if (gamepad2.dpad_down){
                    johnny9.elevate(-1);
                } else {
                    johnny9.elevate(0);
                }

                /*ftcPose = johnny9.getPos();
                telemetry.update();
                if (ftcPose != null) {
                    if (ftcPose.y > 80 || ftcPose.y < 20) {
                        johnny9.Led.setPosition(johnny9.REDPOS);
                    } else {
                        johnny9.Led.setPosition((johnny9.GREENPOS));
                    }
                }*/
                /* Led output, if the blue tag is read,
                 and the position is practically on the line(edit if need be),
                 then it turns green(can edit based on length of shot)
                 */
               /* if(johnny9.getTag()==20 ){
                    johnny9.Led.setPosition(johnny9.GREENPOS);
                }
                else{
                    johnny9.Led.setPosition(johnny9.REDPOS);
                }*/
                // Localization
              /*  if (gamepad1.bWasPressed()) {
                        ftcTag = !ftcTag;
                }
                if (ftcTag) {
                    ftcPose = johnny9.getPos();
                    telemetry.addLine("\nMode: ftcPose");
                } else {
                    robotPose = johnny9.getRobotPos();
                    telemetry.addLine("\nMode: robotPose");
                }
                telemetry.update();
            }*/
            }
            // johnny9.visionPortal.close();

        }
    }
}
