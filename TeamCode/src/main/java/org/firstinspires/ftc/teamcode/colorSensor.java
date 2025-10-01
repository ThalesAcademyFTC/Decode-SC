package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@TeleOp(name="color_sensor")
@Disabled
public class colorSensor extends LinearOpMode {
    NormalizedColorSensor colorsensor;
    private Johnny9 johnny9;
    @Override
    public void runOpMode(){
    johnny9 = new Johnny9(this,Johnny9.Drivetrain.JOHNNY9);
    waitForStart();
    if(opModeIsActive()){
        while(opModeIsActive()){
            getColor();
            telemetry.addData("color:","%.3f",getColor());
        }
    }

    }
    public String getColor(){
        final float[] hsvValues = new float[3];//hue=0 saturation=1 value=2

        String color = "";
        NormalizedRGBA colors = johnny9.colorSensor.getNormalizedColors();//gets color sensor values
        Color.colorToHSV(colors.toColor(), hsvValues);//update hsv values from the color sensor
        float hue=hsvValues[0];
        float sat=hsvValues[1];
        float value=hsvValues[2];
        telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0])//Hue
                .addData("Saturation", "%.3f", hsvValues[1])//Saturation
                .addData("Value", "%.3f", hsvValues[2]);//Value
        telemetry.addData("Alpha", "%.3f", colors.alpha);//Light
        if(hue>=285 && hue<=289 && sat>=56 && sat<=53 && value>=41 && value<=45){
            color="PURPLE";
        }
        else if(hue>=105 && hue<=102 && sat>=66 && sat<=70 && value>=25 && value<=29){
            color="GREEN";
        }

        return color;

    }


}

