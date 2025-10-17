package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Ethan is a silly billy ")
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
        boolean ftcTag = false;
        boolean toogle = true;


        waitForStart();
        // if id21 - move left
        // if id22 - move right
        // if id23 - move forward
        if(opModeIsActive()) {
            while(opModeIsActive()) {
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
