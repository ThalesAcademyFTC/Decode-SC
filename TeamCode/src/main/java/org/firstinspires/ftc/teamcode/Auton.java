package org.firstinspires.ftc.teamcode;

//import static org.firstinspires.ftc.teamcode.Johnny9.java.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous
public class Auton  extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime = new ElapsedTime();
    //public Johnny9.java.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";

    @Override
    public void runOpMode() {
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        runtime.reset();
        double speed = .5;
        int rest = 100;

        waitForStart();
        // This will start on the wall of the goal(red)
        //move back from the wall(edit)
        johnny9.moveBackwardInches(2,speed);

        sleep(rest);
        /// n
        johnny9.runIntakeBallSnatch(speed);

        sleep(1200);

        johnny9.runIntakeBallSnatch(speed);

        sleep(1200);

        johnny9.runIntakeBallSnatch(speed);

        sleep(1200);

        johnny9.moveBackwardInches(15,speed);

        sleep(rest);

        johnny9.turnRightDegrees(180,speed);

        sleep(rest);

        johnny9.moveForwardInches(10,speed);

        sleep(rest);

        johnny9.intakeSystem(speed);

        johnny9.moveForwardInches(2,speed);

        johnny9.intakeSystem(speed);

        sleep(rest);

        johnny9.moveBackwardInches(10,speed);

        sleep(rest);

        johnny9.turnLeftDegrees(195,speed);

        sleep(rest);

        johnny9.moveLeftInches(20,speed);

        sleep(rest);

        johnny9.runIntakeBallSnatch(speed);

        sleep(1200);

        johnny9.runIntakeBallSnatch(speed);

        sleep(1200);

        johnny9.runIntakeBallSnatch(speed);

        sleep(1200);

        johnny9.runIntakeBallSnatch(speed);



        //robot.explode();


    }
}
