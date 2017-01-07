package com.mesosphere.elevator;

import org.apache.log4j.Logger;

import java.util.*;

public class Elevator {

    final static Logger LOG = Logger.getLogger(Elevator.class);

    public static final int MAX_CAPACITY = 10;

    private int id;
    private int floor;
    private ElevatorDirection direction;
    private List<PickUpRequest> requests;

    public Elevator(int id) {
        this.id = id;
        this.floor = (PickUpRequest.MIN_FLOOR + PickUpRequest.MAX_FLOOR) / 2;
        this.direction = ElevatorDirection.IDLE;
        this.requests = new LinkedList<PickUpRequest>();
    }

    /**
     * Schedule a pickup request for a passenger
     *
     * @param request
     */
    public void pickUp(PickUpRequest request) {
        if (requests.size() >= MAX_CAPACITY) {
            LOG.error("Elevator capacity overloaded.");
            return;
        }
        int index = 0;
        while (index < requests.size() && requests.get(index).getGoalFloor() < request
                .getGoalFloor()) {
            index++;
        }
        requests.add(index, request);

        if (isIdle()) {
            // TODO(Vijay): Don't change floor magically.
            // Make this elevator move step by step to pickup floor
            floor = request.getStartFloor();
            direction = (request.getStartFloor() - request.getGoalFloor() > 0) ?
                    ElevatorDirection.DOWN : ElevatorDirection.UP;
        }
    }

    /**
     * Drop passengers if they have reached their destination
     */
    public void drop() {
        Iterator<PickUpRequest> it = requests.iterator();
        while (it.hasNext()) {
            PickUpRequest request = it.next();
            if (request.getGoalFloor() == floor) {
                LOG.info("Request completed: " + request.getStartFloor() + "->" +
                        request.getGoalFloor());
                it.remove();
            }
        }
    }

    /**
     * Drop passengers and goto next floor
     */
    public void step() {
        if (direction == ElevatorDirection.UP) {
            floor++;
        }
        if (direction == ElevatorDirection.DOWN) {
            floor--;
        }

        drop();

        if (floor >= PickUpRequest.MAX_FLOOR) {
            direction = ElevatorDirection.DOWN;
        }
        if (floor <= PickUpRequest.MIN_FLOOR) {
            direction = ElevatorDirection.UP;
        }
        if (requests.isEmpty()) {
            direction = ElevatorDirection.IDLE;
            // TODO(Vijay): if an elevator stays for more than some
            // threshold time idle then move the elevator to the middle floor
        }
    }

    /**
     * Update status of this elevator
     *
     * @param floorNum
     * @param goalFloorNums
     * @throws ElevatorException
     */
    public void update(int floorNum, List<Integer> goalFloorNums) throws
            ElevatorException {
        requests.clear();

        floor = floorNum;
        direction = ElevatorDirection.IDLE;
        Collections.sort(goalFloorNums);
        for (int goalFloor : goalFloorNums) {
            if (goalFloor == floorNum) {
                goalFloorNums.remove(goalFloor);
                continue;
            }
            requests.add(new PickUpRequest(floorNum, goalFloor));
        }
        if (goalFloorNums.size() > 0) {
            int goal = goalFloorNums.get(0);
            goal = floorNum - goal;
            direction = (goal > 0) ? ElevatorDirection.DOWN : ElevatorDirection.UP;
        }
    }

    public ElevatorStatus getStatus() {
        List<Integer> goalFloorNums = new ArrayList<Integer>();
        for (PickUpRequest request : requests) {
            goalFloorNums.add(request.getGoalFloor());
        }
        return new ElevatorStatus(id, floor, goalFloorNums);
    }

    public boolean isFull() {
        return requests.size() >= MAX_CAPACITY;
    }

    /**
     * Elevator running in same direction?
     *
     * @param d
     * @return
     */
    public boolean isAlongSide(ElevatorDirection d) {
        return direction == d;
    }

    public boolean isIdle() {
        return direction == ElevatorDirection.IDLE;
    }

    /**
     * Calculate Pickup cost to pickup a user from the floor pickupFloorNum
     *
     * @param pickupFloorNum
     * @param d
     * @return
     * @throws ElevatorException
     */
    public int getPickupCost(int pickupFloorNum, ElevatorDirection d) throws
            ElevatorException {
        int cost = Integer.MAX_VALUE;
        if (isIdle() || isAlongSide(d)) {
            cost = Math.abs(floor - pickupFloorNum);
        }
        return cost;
    }

    public int getId() {
        return id;
    }
}