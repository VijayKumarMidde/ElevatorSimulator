package com.mesosphere.elevator;

import java.util.List;

public class ElevatorStatus {
    private int elevatorId;
    private int floorNum;
    private List<Integer> goalFloorNums;

    public ElevatorStatus(int elevatorId, int floorNum, List<Integer> goalFloorNums) {
        this.elevatorId = elevatorId;
        this.floorNum = floorNum;
        this.goalFloorNums = goalFloorNums;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public List<Integer> getGoalFloorNums() {
        return goalFloorNums;
    }

    public void setGoalFloorNums(List<Integer> goalFloorNums) {
        this.goalFloorNums = goalFloorNums;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + elevatorId);
        sb.append(", floor: " + floorNum);
        sb.append(", goalFloors: [");
        for (int i = 0; i < goalFloorNums.size() - 1; i++) {
            sb.append(goalFloorNums.get(i));
            sb.append(", ");
        }
        if (goalFloorNums.size() > 0) {
            sb.append(goalFloorNums.get(goalFloorNums.size() - 1));
        }
        sb.append("]");
        return sb.toString();
    }
}
