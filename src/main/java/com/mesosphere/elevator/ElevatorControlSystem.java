package com.mesosphere.elevator;

import java.util.List;

public interface ElevatorControlSystem {

    /**
     * Get status of all the elevators
     *
     * @return
     */
    List<ElevatorStatus> getStatus();

    /**
     * Update elevator status
     *
     * @param elevatorId
     * @param floorNum
     * @param goalFloorNums
     */
    void update(int elevatorId, int floorNum, List<Integer> goalFloorNums)
            throws ElevatorException;

    /**
     * Schedule a pickup request
     *
     * @param pickupFloorNum
     * @param direction
     */
    void pickup(int pickupFloorNum, int goalFloorNum, ElevatorDirection direction) throws
            ElevatorException;

    /**
     * time-step the simulation
     */
    void step();

    /**
     * return true if at least one elevator is not in idle state
     */
    boolean isRunning();

}