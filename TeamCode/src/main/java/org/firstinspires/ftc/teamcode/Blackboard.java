package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.*;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@TeleOp(name="Blackboard")
//@Disabled
public  class Blackboard extends OpMode  {
    private Johnny9 johnny9;
    public static final String TIMES_STARTED_KEY = "Times started";
    public static final String OBELISK_VALUE_STRING="obelisk";
    public Object obValue;


    @Override
    public void init(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        Object timeStarted=blackboard.getOrDefault(TIMES_STARTED_KEY,0);
        blackboard.put(TIMES_STARTED_KEY, (int) timeStarted + 1);
        telemetry.addData("OpMode started times", blackboard.get(TIMES_STARTED_KEY));
        obValue = blackboard.get(OBELISK_VALUE_STRING);
    }
    @Override
    public void loop(){
        if (obValue == GPP) {
            telemetry.addLine("found green, purple, purple");

        } else if (obValue == PGP) {
            telemetry.addLine("found purple, green, purple");

        } else if (obValue == PPG) {
            telemetry.addLine("found purple, purple, green");
        } else if (obValue == UNKNOWN) {
            telemetry.addLine("null");

        }

        telemetry.update();
    }

}
