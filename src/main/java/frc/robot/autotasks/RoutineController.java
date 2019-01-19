package frc.robot.autotasks;

import java.util.Optional;
import java.util.Queue;

public class RoutineController {
    private final RoutineInterface routine;
    private final Queue<TaskInterface> tasks;
    private TaskInterface currentTask;

    public RoutineController(RoutineInterface routine) {
        this.routine = routine;
        this.tasks = routine.getTasks();
    }

    public RoutineInterface getRoutine() {
        return routine;
    }

    public void init() {
        routine.start();
    }

    public void periodic() {
        if (currentTask == null && tasks.isEmpty()) {
            return;
        }

        if (currentTask == null) {
            Optional.ofNullable(tasks.poll())
                    .ifPresent(t -> {
                            currentTask = t;
                            currentTask.start();
                    });
            return;
        }

        if (currentTask.isFinished()) {
            currentTask.end();
            Optional.ofNullable(tasks.poll())
                    .ifPresent(t -> {
                        currentTask = t;
                        currentTask.start();
                    });
            return;
        }

        Optional.ofNullable(currentTask)
                .ifPresent(TaskInterface::execute);
    }
}
