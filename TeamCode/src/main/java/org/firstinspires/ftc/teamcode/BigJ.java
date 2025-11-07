package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Johnny9.Obelisk.UNKNOWN;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp(name="BigJ")
public class BigJ extends LinearOpMode {
    private Johnny9 johnny9;
    AprilTagPoseFtc ftcPose = null;
    Pose3D robotPose = null;
    static final double CYLINDERSPINNY = .33;
    static final double ADJUSTMENTSPINNY = .05;

    private ElapsedTime runtime=new ElapsedTime();
    public Johnny9.Obelisk obValue = UNKNOWN;
    public static final String OBELISK_VALUE_STRING = "obelisk";
    @Override
    public void runOpMode(){
        johnny9 = new Johnny9(this, Johnny9.Drivetrain.JOHNNY9);
        johnny9.initAprilTag();
        runtime.reset();
        double cylinderPos = 0;
        double cylinderChange = 0;
        boolean ftcTag = true;
        boolean ftcToogle = true;
        boolean spinToogle = true;
        boolean spinny = false;
        boolean intakeOffset = false;


        waitForStart();
        if(opModeIsActive()) {
            while(opModeIsActive()) {
                // Movement
                double y = gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double turn = gamepad1.right_stick_x / 2;

                telemetry.update();

                y *= y;
                if (gamepad1.left_stick_y > 0){
                    y =- y;
                }
                x *= x;
                if (gamepad1.left_stick_x < 0){
                    x =- x;
                }
                johnny9.move(x, y, turn);
                // Special functions
                if (gamepad2.right_trigger > 0 /*&& johnny9.Led.getPosition() == Johnny9.GREENPOS*/) {
                    johnny9.barrelFire(.3);
                } else {
                    johnny9.barrelFire(0);
                }
                if (gamepad2.left_trigger > 0) {
                    johnny9.intakeSystem(.3);
                } else {
                    johnny9.intakeSystem(0);
                }
                if (gamepad2.dpad_up || gamepad2.dpad_down || gamepad2.x) {
                    if (spinToogle){
                        spinny = true;
                        spinToogle = false;
                        if (gamepad2.dpad_up){
                            cylinderChange = CYLINDERSPINNY;
                        } else if (gamepad2.dpad_down){
                            cylinderChange = -CYLINDERSPINNY;
                        } else {
                            intakeOffset = !intakeOffset;
                            if (intakeOffset){
                                cylinderChange = -ADJUSTMENTSPINNY;
                            } else {
                                cylinderChange = ADJUSTMENTSPINNY;
                            }
                        }
                    }
                } else {
                    spinToogle = true;
                }
                if (spinny) {
                    cylinderPos = johnny9.cylinderSpin(cylinderPos, cylinderChange);
                    spinny = false;
                }
                // Localization
                if (gamepad1.b) {
                    if (ftcToogle){
                        ftcTag = !ftcTag;
                        ftcToogle = false;
                    }
                } else {
                    ftcToogle = true;
                }
                if (ftcTag) {
                    ftcPose = johnny9.getPos();
                    telemetry.addLine("\nMode: ftcPose");
                } else {
                    robotPose = johnny9.getRobotPos();
                    telemetry.addLine("\nMode: robotPose");
                }
                telemetry.update();
            }
        }
        johnny9.visionPortal.close();

    }
}
