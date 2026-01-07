package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@Autonomous
//@Disabled
public class colorSensor extends LinearOpMode {

    private Johnny9 johnny9;
    String color="";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this,Johnny9.Drivetrain.JOHNNY9);
        waitForStart();
        if(opModeIsActive()){
            while(opModeIsActive()){
                String color=getColor();
                telemetry.addData("color:",color);
                telemetry.update();
                if(color=="GREEN"){
                    johnny9.Led.setPosition(0.500);
                }
                else if(color=="PURPLE"){
                    johnny9.Led.setPosition(0.722);
                }
                else{
                    johnny9.Led.setPosition(1.000);
                }
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
        if(hue>=220 && hue<=240 && sat>=.5 && sat<=1 && value>=0.001 && value<=0.080){
            color = "PURPLE";
        }
        else if(hue>=150 && hue<=180 && sat>=.75 && sat<=1 && value>=0.001 && value<=0.080){
            color = "GREEN";
        }else{
            color = "NONE";
        }

        return color;

    }


}

