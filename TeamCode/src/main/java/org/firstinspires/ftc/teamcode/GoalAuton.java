package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.GPP;
import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.PPG;
import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@Autonomous
public class GoalAuton extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;

    //public static final double FIRINGPERFECTY=90;
    //public static final double FIRINGLEEWAYY=10;
    //public static final double FIRINGPERFECTX=-60;
    public static final double FIRINGLEEWAYX=5;
    public static final double TURNLEEWAY = 2.5;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        runtime.reset();
        double speed=0.5;
        int rest=100;

        waitForStart();
        if(opModeIsActive()) {
            while(opModeIsActive()) {
                    if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH)<=6){
                        johnny9.moveForwardInches(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - 3, speed);
                        johnny9.Led.setPosition((johnny9.BLUEPOS));
                    } else {
                        sleep(rest);
                        johnny9.Led.setPosition(johnny9.GREENPOS);
                        johnny9.runIntakeBallSnatch(1);
                        sleep(10000);
                    }
                telemetry.update();
            }
        }
        johnny9.visionPortal.close();

    }
}
