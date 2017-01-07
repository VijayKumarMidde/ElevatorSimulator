package com.mesosphere.elevator;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ElevatorControlSystemImpl implements ElevatorControlSystem {

    final static Logger LOG = Logger.getLogger(ElevatorControlSystemImpl.class);

    public final static int MAX_ELEVATOR_SIZE = 16;
    private List<Elevator> elevators;

    public ElevatorControlSystemImpl(int elevatorSize) throws ElevatorException {

        if (elevatorSize >= MAX_ELEVATOR_SIZE) {
            throw new ElevatorException("Exceeds max number of elevator size.");
        }

        elevators = new ArrayList<Elevator>(elevatorSize);
        for (int i = 0; i < elevatorSize; i++) {
            Elevator e = new Elevator(i);
            elevators.add(e);
        }
    }

    public List<ElevatorStatus> getStatus() {
        List<ElevatorStatus> statuses = new ArrayList<ElevatorStatus>();
        for (Elevator elevator : elevators) {
            statuses.add(elevator.getStatus());
        }
        return statuses;
    }

    public void update(int elevatorId, int floorNum, List<Integer> goalFloorNums)
            throws ElevatorException {
        getElevatorById(elevatorId).update(floorNum, goalFloorNums);
    }

    public void pickup(int pickupFloorNum, int goalFloorNum, ElevatorDirection direction)
            throws ElevatorException {

        if (direction == ElevatorDirection.IDLE ||
                (direction == ElevatorDirection.DOWN && pickupFloorNum < goalFloorNum) ||
                (direction == ElevatorDirection.UP && pickupFloorNum > goalFloorNum)) {
            throw new ElevatorException("Invalid direction.");
        }

        PickUpRequest request = new PickUpRequest(pickupFloorNum, goalFloorNum);
        int minCost = Integer.MAX_VALUE;
        Elevator pickupElevator = null;

        for (Elevator elevator : elevators) {
            if (elevator.isFull()) {
                continue;
            }
            int cost = elevator.getPickupCost(pickupFloorNum, direction);
            if (minCost > cost) {
                minCost = cost;
                pickupElevator = elevator;
            }
        }
        if (pickupElevator != null) {
            pickupElevator.pickUp(request);
        } else {
            LOG.debug("No elevator going in that direction or all elevators " +
                    "are full. Try after sometime.");
            // TODO(Vijay): Add this request to a queue and later when an elevator
            // becomes idle update that elevator to that queue.
        }
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public Elevator getElevatorById(int elevatorId) throws ElevatorException {
        for (Elevator e : elevators) {
            if (e.getId() == elevatorId) {
                return e;
            }
        }
        throw new ElevatorException("Elevator Not Found with Id : " + elevatorId);
    }

    public boolean isRunning() {
        for (Elevator e : elevators) {
            if (!e.isIdle()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (ElevatorStatus status : getStatus()) {
            sb.append(status.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}