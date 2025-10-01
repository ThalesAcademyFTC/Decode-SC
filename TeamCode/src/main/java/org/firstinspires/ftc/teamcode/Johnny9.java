package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

//Setup for Johnny9 class AKA BigJ.
public class Johnny9 {
    private HardwareMap hwMap;
    LinearOpMode auton;
    OpMode teleop;

    //Creates drivetrains, including JOHNNY9 AKA BIGJ
    public enum Drivetrain {
        MECHANUM,
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
    NormalizedColorSensor colorSensor;

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

                colorSensor = hwMap.get(NormalizedColorSensor.class, "colorSensor");
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
                motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
                motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);

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
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }

    public void move(double x, double y, double turn){
        switch(drive){
            //Johnny 9 movement code AKA BigJ
            case JOHNNY9:

                break;

            case MECHANUM:

                break;

            case TEST:

                break;
        }
    }
}
