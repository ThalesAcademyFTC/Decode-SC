package org.firstinspires.ftc.teamcode;

//import static org.firstinspires.ftc.teamcode.Johnny9.java.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous

public class AutoBlue extends LinearOpMode {
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
        //telemetry.addData("Distance(MM): ",()->{return johnny9.distanceSensor.getDistance(DistanceUnit.MM);});
        waitForStart();
        // This will start on the wall of the goal(red)
        //move back from the wall(edit)

        /* runs the intake system if there no ball is detected,
         if there is a ball detected, then it will fire the ball*/
        johnny9.moveLeftInches(24,speed);




        //robot.explode();


    }
}

