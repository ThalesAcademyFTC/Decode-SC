package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


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
        PPG,
        UNKNOWN
    }

    private Drivetrain drive;
    private Telemetry telem;

    public DcMotor motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight, barrelMotor;
    public Servo cylinderServo;
    public CRServo intakeServo0/*, intakeServo1, intakeServo2*/;

    public DcMotor[] allDriveMotors = {motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};
    //public CRServo[] allIntakeServos = {intakeServo0, intakeServo1, intakeServo2};

    public IMU imu;
    private IMU.Parameters parameters;
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;
    private static final boolean USE_WEBCAM = true;
    RevColorSensorV3 colorSensor;
    public Servo Led;

    static final double X_INCH_TICKS = 45;
    static final double Y_INCH_TICKS = 45;
    static final double X_DEGREE_TICKS = 11.1;
    static final double Y_DEGREE_TICKS = 11.1;

    static final double GREENPOS = 0.485;
    static final double REDPOS = 0.280;
    static final double WHITEPOS = 1.0;

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
        this.telem = opmode.telemetry;
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
                barrelMotor = hwMap.dcMotor.get("barrelMotor");
                cylinderServo = hwMap.servo.get("cylinderServo");
                intakeServo0 = hwMap.crservo.get("intakeServo0");
                //intakeServo1 = hwMap.crservo.get("intakeServo1");
                //intakeServo2 = hwMap.crservo.get("intakeServo2");


                allDriveMotors = new DcMotor[]{motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight};
                motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                //intakeServo1.setDirection(CRServo.Direction.REVERSE);

                colorSensor = hwMap.get(RevColorSensorV3.class ,"colorSensor");
                Led = hwMap.servo.get("LED");
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

                //imu = hwMap.get(IMU.class, "imu");
                //webcam = hwMap.get(WebcamName.class, "webcam");

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

    public void move(double x, double y, double turn){
        double denominator;
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        switch(drive){
            //Johnny 9 movement code AKA BigJ
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
            // Test is identical for now, but can be changed if needed.
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

    public void resetYaw() {imu.resetYaw();}
    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    void addSensorTelemetry(){
        telem.addData("motor front left position ", this::getRotationFL);
        telem.addData("motor front right position", this::getRotationFR);
        telem.addData("motor back left position", this::getRotationBL);
        telem.addData("motor back right position", this::getRotationBR);
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

        move(0, speed, 0);

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
    public void turnRightDegrees(double degrees, double speed){
        int tickTarget=(int)Math.round(degrees*X_DEGREE_TICKS);
        resetDriveEncoders();
        motorFrontLeft.setTargetPosition(tickTarget);
        motorFrontRight.setTargetPosition(-tickTarget);
        motorBackLeft.setTargetPosition(tickTarget);
        motorBackRight.setTargetPosition(-tickTarget);

        for(DcMotor x:allDriveMotors){
            x.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        move(0,0,speed);
        waitForMotors();
        resetDriveEncoders();
    }
    public void turnLeftDegrees(double degrees, double speed){
        turnRightDegrees(-degrees, -speed);
    }

    public double cylinderSpin(double pos, double change){
        pos += change;
        if (pos >= 1){pos -= 1;}
        if (pos < 0){pos += 1;}
        cylinderServo.setPosition(pos);
        return pos;
    }

    public void barrelFire(double speed){
        barrelMotor.setPower(speed);
    }

    public void intakeSystem(double speed){
        //for (CRServo x: allIntakeServos){
            intakeServo0.setPower(speed);
        //}
    }

    public void initAprilTag() {
        Position cameraPosition = new Position(DistanceUnit.INCH,
                0, 8.5, 3, 0);
        YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
                0, -15f, 0, 0);
        aprilTag = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                .build();

        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hwMap.get(WebcamName.class, "Eye of Johnny 9"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }
    }

    public int getTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {

                return detection.id;
            }
        }
        return 0;
    }

    public Obelisk getObelisk() {
        int tag = getTag();

        if (tag == 21) {
            return Obelisk.GPP;
        } else if (tag == 22) {
            return Obelisk.PGP;
        } else if (tag == 23) {
            return Obelisk.PPG;
        } else {
            return Obelisk.UNKNOWN;
        }
    }

    public AprilTagPoseFtc getPos(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telem.addData("# AprilTags Detected", currentDetections.size());
        AprilTagPoseFtc pose = null;
        if (currentDetections.isEmpty()){
            Led.setPosition(WHITEPOS);
        }
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.ftcPose.y <= 60){
                    Led.setPosition(GREENPOS);
                } else {
                    Led.setPosition(REDPOS);
                }
                telem.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                // Only use tags that don't have Obelisk in them
                if (!detection.metadata.name.contains("Obelisk")) {
                    pose = detection.ftcPose;
                    telem.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)",
                            detection.ftcPose.x,
                            detection.ftcPose.y,
                            detection.ftcPose.z));
                    telem.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)",
                            detection.ftcPose.pitch,
                            detection.ftcPose.roll,
                            detection.ftcPose.yaw));
                }
            } else {
                telem.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telem.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telem.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telem.addLine("PRY = Pitch, Roll & Yaw (Rotation on XYZ)");
        return pose;
    }
    public Pose3D getRobotPos(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telem.addData("# AprilTags Detected", currentDetections.size());
        Pose3D pose = null;

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telem.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                // Only use tags that don't have Obelisk in them
                if (!detection.metadata.name.contains("Obelisk")) {
                    pose = detection.robotPose;
                    telem.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)",
                            detection.robotPose.getPosition().x,
                            detection.robotPose.getPosition().y,
                            detection.robotPose.getPosition().z));
                    telem.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)",
                            detection.robotPose.getOrientation().getPitch(),
                            detection.robotPose.getOrientation().getRoll(),
                            detection.robotPose.getOrientation().getYaw()));
                }
            } else {
                telem.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telem.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telem.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telem.addLine("PRY = Pitch, Roll & Yaw (Rotation on XYZ)");
        return pose;
    }

    double getRotationFL() {return motorFrontLeft.getCurrentPosition();}
    double getRotationFR() {return motorFrontLeft.getCurrentPosition();}
    double getRotationBL() {return motorBackLeft.getCurrentPosition();}
    double getRotationBR() {return motorBackRight.getCurrentPosition();}
}