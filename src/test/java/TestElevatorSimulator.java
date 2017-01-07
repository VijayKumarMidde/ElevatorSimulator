import com.mesosphere.elevator.ElevatorControlSystem;
import com.mesosphere.elevator.ElevatorControlSystemImpl;
import com.mesosphere.elevator.ElevatorDirection;
import com.mesosphere.elevator.ElevatorException;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;

public class TestElevatorSimulator {

    final static Logger LOG = Logger.getLogger(TestElevatorSimulator.class);

    @Test
    public void testElevatorControlSystemImpl() throws ElevatorException {
        ElevatorControlSystem ecs = new ElevatorControlSystemImpl(3);
        ecs.update(0, 1, new ArrayList<Integer>() {{
            add(5);
            add(8);
        }});
        ecs.pickup(3, 1, ElevatorDirection.DOWN);
        ecs.pickup(5, 6, ElevatorDirection.UP);
        ecs.pickup(2, 9, ElevatorDirection.UP);
        do {
            LOG.info(ecs);
            ecs.step();
        } while (ecs.isRunning());
        LOG.info(ecs);
    }
}
