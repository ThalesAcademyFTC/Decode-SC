package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp(name="BigJTeleopTest")
public class BigJRobotCentricTeleopTest extends OpMode {
    private Johnny9 johnny9;
    //AprilTagPoseFtc ftcPose = johnny9.getPos();
    AprilTagPoseFtc ftcPose = null;
    Pose3D robotPose = null;
    private ElapsedTime runtime = new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";

    boolean ftcTag=true;

    @Override
    public void init(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        runtime.reset();
        

    }

    @Override
    public void loop(){

        // Movement
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x / 2;
        AprilTagPoseFtc ftcPose = johnny9.getPos();
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
        if (gamepad2.right_trigger > 0 /*&& johnny9.Leosition() == Johnny9.GREENPOS*/) {
            johnny9.launchTime(1);
        } else {
            johnny9.launchTime(0);
        }

        //intake system(left trigger
        if (gamepad2.left_trigger > 0) {
            johnny9.intakeSystem(1);
        } else {
            johnny9.intakeSystem(0);
        }

        //output system
        //if(gamepad2.leftBumperWasPressed()){
        //    johnny9.intakeSystem(-1);
        //}



        // Localization

        ftcPose = johnny9.getPos();
        telemetry.update();

       // ftcPose = Johnny9.getPos();


    }
    @Override
    public void stop(){
        johnny9.visionPortal.close();
    }

}






