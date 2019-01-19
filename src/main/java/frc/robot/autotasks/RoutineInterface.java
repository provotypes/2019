package frc.robot.autotasks;

import java.util.Queue;

public interface RoutineInterface extends TaskInterface {
    Queue<TaskInterface> getTasks();
}
