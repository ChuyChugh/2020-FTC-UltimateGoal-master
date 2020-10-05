package org.firstinspires.ftc.teamcode.TeleOp;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Yawn")
public class FirstTest extends OpMode {
    private Motor fL, bL, fR, bR;
    private MecanumDrive drive;
    private RevIMU imu;
    //this is just for testing
    private static double multiplier = 0.5;
    private GamepadEx gm1;
    @Override
    public void init() {
        fL = new Motor(hardwareMap, "fL");
        fR = new Motor(hardwareMap, "fR");
        bL = new Motor(hardwareMap, "bL");
        bR = new Motor(hardwareMap, "bR");
        drive = new MecanumDrive(fL, fR, bL, bR);
        imu = new RevIMU(hardwareMap);
        gm1 = new GamepadEx(gamepad1);
    }

    @Override
    public void loop() {

        if(gm1.getButton(GamepadKeys.Button.DPAD_UP)){
            multiplier += 0.05;
            if(multiplier > 1){
                multiplier = 1;
            }
        }
        if(gm1.getButton(GamepadKeys.Button.DPAD_DOWN)){
            multiplier -= 0.05;
            if(multiplier < 0){
                multiplier = 0;
            }
        }
        drive.driveFieldCentric(
                -gamepad1.left_stick_x * multiplier,
                -gamepad1.left_stick_y * multiplier,
                -gamepad1.right_stick_x * multiplier,
                Math.toRadians(imu.getHeading())
        );
        telemetry.addData("Power", multiplier);
        telemetry.update();
    }
}
