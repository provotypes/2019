package frc.robot.autotasks;

import java.util.List;

public interface RoutineInterface extends TaskInterface {
    List<TaskInterface> getTasks();
    
    void addTask(TaskInterface task);
}
