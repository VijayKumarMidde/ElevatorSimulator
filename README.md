# Elevator Simulator

## Task

Design and implement an elevator control system. What data structures, interfaces and algorithms will you need? Your elevator control system should be able to handle a few elevators — up to 16.

Your control system should provide an interface for:
* Querying the state of the elevators (what floor are they on and where they are going)
* receiving an update about the status of an elevator
* receiving a pickup request
* time-stepping the simulation

## Brief overview of my solution
* Each elevator can be in three states: UP, DOWN, IDLE
* Initially all elevators have idle state and start in the middle floor.
* Cost/time taken to travel one floor to an adjacent floor by an elevator is 1 unit.
* Whenever a pickup request comes, 
	1. Check if there are any idle elevators and calcuate the cost for pickup
	2. Check if there are any elevators going in that direction and calculate cost for pickup.
	3. Choose the elevator with the min cost from the above two and schedule request.
	4. If all elevators are full or no elevator is going in the required direction, then user has to wait/retry after sometime.
* Once there are no more requests pending, the corresponding elevator goes to idle state.

## How to build and run sample tests?
```
$ mvn clean install
```

## Modifications to the given API
```
    /**
     * Query status of all the elevators
     *
     * @return
     */
    List<ElevatorStatus> getStatus();

    /**
     * Update elevator status of an elevator
     *
     * @param elevatorId
     * @param floorNum
     * @param goalFloorNums
     */
    void update(int elevatorId, int floorNum, List<Integer> goalFloorNums)
            throws ElevatorException;

    /**
     * Receive a pickup request
     *
     * @param pickupFloorNum
     * @param direction
     */
    void pickup(int pickupFloorNum, int goalFloorNum, ElevatorDirection direction) throws
            ElevatorException;

    /**
     * Time-step the simulation
     */
    void step();

    /**
     * Return true if at least one elevator is not in idle state
     */
    boolean isRunning();
```

## TODO's
* In pickup request, if all elevators are full or no elevator is going in the required direction, add the request to a list and later when an elevator becomes idle update that elevator with this requests list.
* In the current implementation, while assigning a pickup request to an idle elevator, I'm updating the elevators floor number to the pickup request floor number. I need to modify this such that the elevator goes to the pickup floor step by step and picks user.
* Set some threshold limit of how long an elevator can stay in an idle position in a floor and once the threshold expires, move the lift to mid floor.
* Rather than creating a new ElevatorStatus object for every elevator everytime you query the status of the ElevatorControlSystem, Keep an ElevatorStatus object inside Elevator and reuse the same object by updating the status object appropriately.
* Add unit tests
* Add logger support like log4j
* Improve documentation
* Organize java package structure
* Setup CI, Code coverage, Git repo etc.
* Multithreading support??

