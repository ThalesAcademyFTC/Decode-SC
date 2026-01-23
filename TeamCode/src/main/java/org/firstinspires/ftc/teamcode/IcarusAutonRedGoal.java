package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
@Autonomous
public class IcarusAutonRedGoal extends LinearOpMode {
    private Johnny9 johnny9;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;

    public static final double LEEWAYY =6;
    public static final double LEEWAYX =3;
    public static final double TURNLEEWAY = 2.5;
    public static final double DISTANCELEEWAYY =2;
    public static final double DISTANCEPERFECTY =40;
    public static final double INTAKEPERFECTY=40;
    public static final double INTAKEPERFECTX=40;
    public static final double INTAKESETUPTURN=-45;
    public static final double INTAKEPERFECTTURN=0;


    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        johnny9.findLauncherZero();
        runtime.reset();
        double speed=0.5;
        int rest=100;
        boolean sightToogle=false;
        boolean taskToogle =false;

        waitForStart();
        if(opModeIsActive()) {
            johnny9.Led.setPosition(johnny9.GREENPOS);
            johnny9.moveLeftInches(3,.5);
            while (johnny9.launcherMotor.getMode() == DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
                johnny9.runIntakeBallSnatch(1);
            }
            johnny9.Led.setPosition(johnny9.BLUEPOS);
            while (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - DISTANCEPERFECTY >= DISTANCELEEWAYY || johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - DISTANCEPERFECTY <= DISTANCELEEWAYY) {
                johnny9.moveRightInches(johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - DISTANCEPERFECTY, 1);
            }
            AprilTagPoseFtc ftcPose=johnny9.getPos(24);
            if(ftcPose != null) {
                johnny9.turnLeftDegrees(ftcPose.yaw - INTAKESETUPTURN, .7);
            }
            while (!taskToogle) {
                telemetry.addData("Distance %f", johnny9.distanceSensor.getDistance(DistanceUnit.INCH));
                telemetry.update();
                ftcPose = johnny9.getPos(24);
                if (ftcPose != null) {
                    if (ftcPose.y-INTAKEPERFECTY>LEEWAYY|| ftcPose.y-INTAKEPERFECTY<-LEEWAYY) {
                        johnny9.moveRightInches(ftcPose.y-INTAKEPERFECTY, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } /*else if (ftcPose.x > LEEWAYX || ftcPose.x < -LEEWAYX) {
                        johnny9.moveBackwardInches(ftcPose.x, speed);
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) > LEEWAYY) {
                        sightToogle = true;
                        if (johnny9.distanceSensor.getDistance(DistanceUnit.INCH) >= 300) {
                            johnny9.moveRightInches(50, speed);
                        } else {
                            johnny9.moveRightInches((johnny9.distanceSensor.getDistance(DistanceUnit.INCH) - FIRINGLEEWAYY), speed);
                        }
                        johnny9.Led.setPosition(johnny9.BLUEPOS);
                    } else {
                        taskToogle = true;
                    }*/
                } else {
                    // No tag detected
                    johnny9.Led.setPosition(johnny9.REDPOS);
                    sleep(rest);
                }
            }

            johnny9.visionPortal.close();
        }

    }
}
