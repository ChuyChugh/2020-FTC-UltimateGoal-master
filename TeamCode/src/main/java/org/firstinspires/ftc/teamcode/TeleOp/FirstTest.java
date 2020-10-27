package org.firstinspires.ftc.teamcode.TeleOp;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Yawn")
public class FirstTest extends OpMode {
    private Motor fL, bL, fR, bR;
    private MecanumDrive drive;
    private RevIMU imu;
    private GamepadEx driverOp;
    private ButtonReader speedUpToggle, speedDownToggle;
    //this is just for testing
    private static double multiplier = 0.5;
    @Override
    public void init() {
        fL = new Motor(hardwareMap, "fL");
        fR = new Motor(hardwareMap, "fR");
        bL = new Motor(hardwareMap, "bL");
        bR = new Motor(hardwareMap, "bR");
        drive = new MecanumDrive(fL, fR, bL, bR);
        driverOp = new GamepadEx(gamepad1);
        speedUpToggle = new ButtonReader(driverOp, GamepadKeys.Button.DPAD_UP);
        speedDownToggle = new ButtonReader(
          driverOp, GamepadKeys.Button.DPAD_DOWN
        );
        imu = new RevIMU(hardwareMap);
    }

    @Override
    public void loop() {
        if (speedUpToggle.wasJustPressed()) {
            if (multiplier < 0.95) {
              multiplier += 0.25;
            } else {
              multiplier = 0.25;
            }
        }
        if (speedDownToggle.wasJustPressed()) {
            if (multiplier > 0.05) {
              multiplier -= 0.25;
            } else {
              multiplier = 0.75;
            }
        }
        drive.driveFieldCentric(
                -driverOp.getLeftX() * multiplier * 1.5,
                -driverOp.getLeftY() * multiplier,
                -driverOp.getRightX() * multiplier,
                Math.toRadians(imu.getHeading())
        );
        telemetry.addData("Power", multiplier);
        telemetry.update();
    }
}
