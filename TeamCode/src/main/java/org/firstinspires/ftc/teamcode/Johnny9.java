package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

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
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

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

    public enum Obelisk {
        GPP,
        PGP,
        PPG
    }

    private Drivetrain drive;
    private Telemetry telem;

    public DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight;

    public DcMotor[] allDriveMotors;

    public IMU imu;
    private IMU.Parameters parameters;
    public WebcamName webcamName;
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    private static final boolean USE_WEBCAM = true;
    NormalizedColorSensor colorSensor;

    static final double X_INCH_TICKS = 45;
    static final double Y_INCH_TICKS = 45;
    static final double X_DEGREE_TICKS = 11.1;
    static final double Y_DEGREE_TICKS = 11.1;

    //Setup for the Johnny9 teleop AKA BigJ
    public Johnny9(OpMode opmode, Drivetrain drivetrain) {
        this.teleop = opmode;
        this.hwMap = opmode.hardwareMap;
        this.drive = drivetrain;
        this.telem = opmode.telemetry;
        setupHardware();
    }

    //Setup for the Johnny9 auton AKA BigJ
    public Johnny9(LinearOpMode opmode, Drivetrain type) {
        this.auton = opmode;
        this.hwMap = opmode.hardwareMap;
        this.drive = type;
        setupHardware();
    }

    //More general setup for Johnny9 AKA BigJ
    public Johnny9(HardwareMap hardwareMap, Drivetrain drivetrain) {
        this.hwMap = hardwareMap;
        this.drive = drivetrain;
        setupHardware();
    }

    private void setupHardware() {
        switch (drive) {
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

    public void rest() {
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }

    public void move(double x, double y, double turn) {
        switch (drive) {
            //Johnny 9 movement code AKA BigJ
            case JOHNNY9:

                break;

            case MECHANUM:

                break;

            case TEST:

                break;
        }
    }

    public void waitForMotors() {
        boolean finished = false;
        while (auton.opModeIsActive() && !finished && !auton.isStopRequested()) {
            if (motorFrontLeft.isBusy() || motorBackLeft.isBusy() || motorFrontRight.isBusy() || motorBackRight.isBusy()) {
                telem.addData("front left encoder:", motorFrontLeft.getCurrentPosition());
                telem.addData("front right encoder:", motorFrontRight.getCurrentPosition());
                telem.addData("back left encoder:", motorBackLeft.getCurrentPosition());
                telem.addData("back right encoder:", motorBackRight.getCurrentPosition());

                telem.update();
            } else {
                finished = true;
            }
        }
    }

    private void resetDriveEncoders() {
        for (DcMotor x: allDriveMotors) {
            x.setPower(0);
            x.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            x.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void moveForwardInches(double inches, double speed) {

        //Converts to integer by rounding. CASTS to int after rounding.
        int tickTarget = (int) Math.round(-inches * Y_INCH_TICKS);

        resetDriveEncoders();

        for (DcMotor x : allDriveMotors) {

            x.setTargetPosition(tickTarget);
            x.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        move(0, -speed, 0);

        waitForMotors();

        resetDriveEncoders();

    }

    public void moveBackwardInches(double inches, double speed) {
        moveForwardInches(-inches, -speed);
    }

    public void moveRightInches(double inches, double speed) {
        int tickTarget = (int) Math.round(inches * X_INCH_TICKS);
        resetDriveEncoders();

        motorFrontLeft.setTargetPosition(tickTarget);
        motorFrontRight.setTargetPosition(-tickTarget);
        motorBackLeft.setTargetPosition(-tickTarget);
        motorBackRight.setTargetPosition(tickTarget);

        for (DcMotor x : allDriveMotors) {

            x.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }

        move(speed, 0, 0);
        waitForMotors();
        resetDriveEncoders();
    }

    public void moveLeftInches(double inches, double speed) {

        moveRightInches(-inches, -speed);
    }

    public void initAprilTag() {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }
    }

    public int getTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                return detection.id;
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
            }
        }
        return 0;
    }

    public Obelisk getObelisk() {
        int tag = getTag();

        if (tag == 21) {
           return Obelisk.PPG;
        } else if (tag == 22) {
            return Obelisk.PGP;
        } else if (tag == 23) {
            return Obelisk.PPG;
        } else {
            return null;
        }
    }
}
