package com.mesosphere.elevator;

public class PickUpRequest {

    public static final int MIN_FLOOR = 1;
    public static final int MAX_FLOOR = 10;

    private int startFloor;
    private int goalFloor;

    public PickUpRequest(int startFloor, int goalFloor) throws ElevatorException {
        if (!isValidFloor(startFloor) || !isValidFloor(goalFloor)) {
            throw new ElevatorException("Invalid startFloor or goalFloor.");
        }
        this.startFloor = startFloor;
        this.goalFloor = goalFloor;
    }

    public int getStartFloor() {
        return startFloor;
    }

    public int getGoalFloor() {
        return goalFloor;
    }

    public static boolean isValidFloor(int floor) {
        return floor >= MIN_FLOOR && floor <= MAX_FLOOR;
    }
}
