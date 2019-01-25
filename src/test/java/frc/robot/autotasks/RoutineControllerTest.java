package frc.robot.autotasks;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoutineControllerTest {

    @Test
    public void getRoutineTest(@Mocked RoutineInterface testRoutine) {
        RoutineController routineController = new RoutineController(testRoutine);
        assertEquals(testRoutine, routineController.getRoutine());
    }

    @Test
    public void initTest(@Mocked RoutineInterface testRoutine) {
        PriorityQueue<TaskInterface> queue = new PriorityQueue<>();

        new Expectations() {{
            testRoutine.getTasks(); result = (queue);
            times = 1;
            testRoutine.start();
            times = 1;
        }};

        RoutineController routineController = new RoutineController(testRoutine);
        routineController.init();
    }

    @Test
    public void runTest(@Mocked RoutineInterface testRoutine) {
        TestTask testTask = new TestTask();
        new Expectations() {{
            LinkedList<TaskInterface> queue = new LinkedList<>();
            queue.add(testTask);
            testRoutine.getTasks(); result = (queue);
            times = 1;
        }};

        RoutineController routineController = new RoutineController(testRoutine);
        for (int i = 0; i <= 11; i++) {
            routineController.periodic();
        }

        assertTrue(testTask.isEnded);
    }

    private class TestTask implements TaskInterface {

        private int counter = 0;
        private boolean isEnded;

        @Override
        public void start() {

        }

        @Override
        public void execute() {
            counter++;
        }

        @Override
        public boolean isFinished() {
            return counter >= 10;
        }

        @Override
        public void end() {
            isEnded = true;
        }
    }
}