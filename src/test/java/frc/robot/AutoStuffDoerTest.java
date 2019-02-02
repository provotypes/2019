package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import static org.junit.Assert.*;

public class AutoStuffDoerTest {

    @Mocked DrivetrainInterface drivetrainInterface;
    @Mocked SendableChooser<String> sendableChooser;

    @Test
    public void autoInit() {

        new Expectations() {{
            sendableChooser.getSelected();
            times = 1;
        }};

        AutoChooser autoStuffDoer = new AutoChooser(drivetrainInterface, sendableChooser);
        autoStuffDoer.autoInit();

        assertEquals(0, autoStuffDoer.taskState);
        assertEquals(0, autoStuffDoer.taskCounter);
    }

    @Test
    public void runAuto() {
    }
}