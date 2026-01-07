package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous
public class Auton  extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime = new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";

    @Override
    public void runOpMode() {
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        runtime.reset();
        double speed = .5;
        int rest = 100;

        waitForStart();
//e
        johnny9.launchTime(speed);
        sleep(rest);
        johnny9.moveBackwardInches(24,speed);
        sleep(rest);
        johnny9.turnRightDegrees(45,speed);
        sleep(rest);
        johnny9.moveLeftInches(24,speed);




    }
}
