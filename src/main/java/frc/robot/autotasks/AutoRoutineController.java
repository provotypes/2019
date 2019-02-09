package frc.robot.autotasks;

import java.util.List;

import frc.robot.autotasks.TaskInterface;

public class AutoRoutineController implements TaskInterface {

    private boolean isFinished;

    List<TaskInterface> taskList;
    int curTaskIndex;

    public AutoRoutineController(List<TaskInterface> taskList){
        this.taskList = taskList;
    }

    public void addTask(TaskInterface task){
        taskList.add(task);
    }

    @Override
    public void start() {
        curTaskIndex = 0;
        taskList.get(curTaskIndex).start();
    }

    @Override
    public void execute() {
        if (!taskList.get(curTaskIndex).isFinished()){
            taskList.get(curTaskIndex).execute();
        } else {
            curTaskIndex += 1;
            if (taskList.size() > curTaskIndex){
                taskList.get(curTaskIndex).start();
            } else {
                end();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end() {
        isFinished = true;
    }


}