package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp(name="BigJTeleop")
public class BigJTeleop extends LinearOpMode {
    private Johnny9 johnny9;
    AprilTagPoseFtc ftcPose = null;
    Pose3D robotPose = null;
    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        //johnny9.initAprilTag();
        runtime.reset();
        double cylinderPos = 0;
        double cylinderChange;
        int cylinderAdjustment = 0;
        boolean cylinderOffset = false;



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
                    y = -y;
                }
                x *= x;
                if (gamepad1.left_stick_x < 0){
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
                //intake system(left trigger
                if (gamepad2.left_trigger > 0) {
                    johnny9.intakeSystem(0.6);
                } else {
                    johnny9.intakeSystem(0);
                }

                if (gamepad2.dpadUpWasPressed()){
                    cylinderChange = Johnny9.CYLINDERSPINNY;
                } else if (gamepad2.dpadDownWasPressed()) {
                    cylinderChange = -Johnny9.CYLINDERSPINNY;
                } else if (gamepad2.dpadRightWasPressed()) {
                    cylinderChange = Johnny9.ADJUSTMENTSPINNY;
                    cylinderAdjustment++;
                } else if (gamepad2.dpadLeftWasPressed()){
                    cylinderChange = -Johnny9.ADJUSTMENTSPINNY;
                    cylinderAdjustment--;
                }
                //
                else if (gamepad2.xWasPressed()) {
                    cylinderOffset = !cylinderOffset;
                    if (cylinderOffset){
                        cylinderChange = -Johnny9.OFFSETSPINNY;
                    } else {
                        cylinderChange = Johnny9.OFFSETSPINNY;
                    }
                } else {
                    cylinderChange = 0;
                }
                cylinderPos = johnny9.cylinderSpin(cylinderPos, cylinderChange, cylinderOffset, cylinderAdjustment);

                /* Led output, if the blue tag is read,
                 and the position is practically on the line(edit if need be),
                 then it turns green(can edit based on length of shot)
                 */
                if(johnny9.getTag()==20 && (johnny9.getPos().y<-30 && johnny9.getPos().x<-30 && johnny9.getPos().y>-26 && johnny9.getPos().x>-26)){
                    johnny9.Led.setPosition(johnny9.GREENPOS);
                }
                else{
                    johnny9.Led.setPosition(johnny9.REDPOS);
                }
                // Localization
                /*if (gamepad1.bWasPressed()) {
                        ftcTag = !ftcTag;
                }
                if (ftcTag) {
                    ftcPose = johnny9.getPos();
                    telemetry.addLine("\nMode: ftcPose");
                } else {
                    robotPose = johnny9.getRobotPos();
                    telemetry.addLine("\nMode: robotPose");
                }
                telemetry.update();*/
            }
        }
        johnny9.visionPortal.close();

    }
}
