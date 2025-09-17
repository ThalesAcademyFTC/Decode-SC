package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

//Setup for Johnny9 class AKA BigJ.
public class Johnny9 {
    private HardwareMap hwMap;
    LinearOpMode auton;
    OpMode teleop;

    //Creates drivetrains, including JOHNNY9 AKA BIGJ
    public enum Drivetrain {
        JOHNNY9,
        TEST
    }

    private Drivetrain drive;
    private Telemetry telem;

    public DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;

    public DcMotor[] allDriveMotors;

    public IMU imu;
    private IMU.Parameters parameters;
    public WebcamName webcamName;
    NormalizedRGBA colorSensor;

    static final double X_INCH_TICKS = 45;
    static final double Y_INCH_TICKS = 45;
    static final double X_DEGREE_TICKS = 11.1;
    static final double Y_DEGREE_TICKS = 11.1;

    //Setup for the Johnny9 teleop AKA BigJ
    public Johnny9(OpMode opmode, Drivetrain drivetrain){
        this.teleop = opmode;
        this.hwMap = opmode.hardwareMap;
        this.drive = drivetrain;
        this.telem = opmode.telemetry;
        setupHardware();
    }

    //Setup for the Johnny9 auton AKA BigJ
    public Johnny9(LinearOpMode opmode, Drivetrain type){
        this.auton = opmode;
        this.hwMap = opmode.hardwareMap;
        this.drive = type;
        setupHardware();
    }

    //More general setup for Johnny9 AKA BigJ
    public Johnny9(HardwareMap hardwareMap, Drivetrain drivetrain){
        this.hwMap = hardwareMap;
        this.drive = drivetrain;
        setupHardware();
    }

    private void setupHardware(){
        switch(drive) {
            //Hardware setup and defining for Johnny9 AKA BigJ
            case JOHNNY9:
                motorFrontLeft = hwMap.dcMotor.get("motorFrontLeft");
                motorFrontRight = hwMap.dcMotor.get("motorFrontRight");
                motorBackLeft = hwMap.dcMotor.get("motorBackLeft");
                motorBackRight = hwMap.dcMotor.get("motorBackRight");

                allDriveMotors = new DcMotor[]{motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};
                motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

                colorSensor = hwMap.get(NormalizedRGBA.class, "colorSensor");
                imu = hwMap.get(IMU.class, "imu");

                if (colorSensor instanceof SwitchableLight) {
                    ((SwitchableLight) colorSensor).enableLight(true);
                }
                parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
                imu.initialize(parameters);
                break;

            case TEST:
                motorFrontLeft = hwMap.dcMotor.get("motorFrontLeft");
                motorFrontRight = hwMap.dcMotor.get("motorFrontRight");
                motorBackLeft = hwMap.dcMotor.get("motorBackLeft");
                motorBackRight = hwMap.dcMotor.get("motorBackRight");

                allDriveMotors = new DcMotor[]{motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};
                for (DcMotor x: allDriveMotors) {
                    x.setDirection(DcMotorSimple.Direction.REVERSE);
                }

                imu = hwMap.get(IMU.class, "imu");
                webcamName = hwMap.get(WebcamName.class, "webcam");

                parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
                imu.initialize(parameters);
                break;
        }
    }


    public void rest(){
        for (DcMotor x: allDriveMotors){
           x.setPower(0);
        }
    }

    public void move(double x, double y, double turn){
        double denominator;
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        switch(drive){
            //Johnny9 movement code AKA BigJ
            case JOHNNY9:
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(turn), 1);

                frontLeftPower = (y + x + turn) / denominator;
                frontRightPower = (y - x - turn) /denominator;
                backLeftPower = (y - x + turn) / denominator;
                backRightPower = (y + x - turn) / denominator;
                telem.addLine("frontLeft:" + frontLeftPower);
                telem.addLine("FrontRight:" + frontRightPower);
                telem.addLine("BackLeft:" + backLeftPower);
                telem.addLine("BackRight:" + backRightPower);

                telem.addData("front left encoder:", getRotationFL());
                telem.addData("front right encoder:", getRotationFR());
                telem.addData("back left encoder:", getRotationBL());
                telem.addData("back right encoder:", getRotationBR());

                motorFrontLeft.setPower(frontLeftPower);
                motorFrontRight.setPower(frontRightPower);
                motorBackLeft.setPower(backLeftPower);
                motorBackRight.setPower(backLeftPower);
                break;
            // This is identical for now, but in case if needed is a different test.
            case TEST:
                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(turn), 1);

                frontLeftPower = (y + x + turn) / denominator;
                frontRightPower = (y - x - turn) /denominator;
                backLeftPower = (y - x + turn) / denominator;
                backRightPower = (y + x - turn) / denominator;
                telem.addLine("frontLeft:" + frontLeftPower);
                telem.addLine("FrontRight:" + frontRightPower);
                telem.addLine("BackLeft:" + backLeftPower);
                telem.addLine("BackRight:" + backRightPower);

                telem.addData("front left encoder:", getRotationFL());
                telem.addData("front right encoder:", getRotationFR());
                telem.addData("back left encoder:", getRotationBL());
                telem.addData("back right encoder:", getRotationBR());

                motorFrontLeft.setPower(frontLeftPower);
                motorFrontRight.setPower(frontRightPower);
                motorBackLeft.setPower(backLeftPower);
                motorBackRight.setPower(backLeftPower);
                break;
        }
    }

    public enum Color{VIOLET, RED, ORANGE, GREEN, YELLOW, BLUE, WHITE, BLACK, UNKNOWN};
    public Color getColorSensorColor(){
        NormalizedRGBA rgba = getColorSensorRGB();
        final float[] hsvValues = new float[3];
        android.graphics.Color.colorToHSV(rgba.toColor(), hsvValues);
        float hue = hsvValues[0];
        float saturation = hsvValues[1];
        if (saturation < 0.05 || hsvValues[2] > 0.9) {
            return Color.WHITE;
        } else if (saturation > .95){
            return Color.BLACK;
        } else if (hue < 32 || hue >= 340) {
            return Color.RED;
        } else if (hue < 45) {
            return Color.ORANGE;
        } else if (hue < 86) {
            return Color.YELLOW;
        } else if (hue < 150) {
            return Color.GREEN;
        } else if (hue < 240) {
            return Color.BLUE;
        } else if (hue < 340) {
            return Color.VIOLET;
        } else {
            return Color.UNKNOWN;
        }
    }

     void addSensorTelemetry(){
        telem.addData("motor front left position ", this::getRotationFL);
        telem.addData("motor front right position", this::getRotationFR);
        telem.addData("motor back left position", this::getRotationBL);
        telem.addData("motor back right position", this::getRotationBR);
     }

    public void resetYaw() {imu.resetYaw();}
    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public void waitForMotors(){
        boolean finished = false;
        while (auton.opModeIsActive() && !finished && !auton.isStopRequested()) {
            if (motorFrontLeft.isBusy() || motorFrontRight.isBusy() || motorBackLeft.isBusy() || motorBackRight.isBusy()){
                telem.addData("front left encoder: ", getRotationFL());
                telem.addData("front right encoder: ", getRotationFR());
                telem.addData("back left encoder: ", getRotationBL());
                telem.addData("back right encoder: ", getRotationBR());
                telem.update();
            } else {
                finished = true;
            }
        }
    }

    public void resetDriveEncoders(){
        for (DcMotor x: allDriveMotors){
            x.setPower(0);
            x.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            x.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    double getRotationFL() {
        return motorFrontLeft.getCurrentPosition();
    }
    double getRotationFR() {return motorFrontLeft.getCurrentPosition();}
    double getRotationBL() {
        return motorBackLeft.getCurrentPosition();
    }
    double getRotationBR() {
        return motorBackRight.getCurrentPosition();
    }

}
