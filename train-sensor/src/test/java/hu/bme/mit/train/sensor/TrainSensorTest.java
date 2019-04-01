package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    private TrainController mockController;
    private TrainUser mockUser;
    private TrainSensor trainSensor;

    @Before
    public void before() {
        // TODO Add initializations
        mockController = mock(TrainController.class);
        mockUser = mock(TrainUser.class);
        trainSensor = new TrainSensorImpl(mockController, mockUser);
    }

    @Test
    public void setSpeedLimitTo501RaiseAlarm() {

        
        when(mockController.getReferenceSpeed()).thenReturn(100);

        
        trainSensor.overrideSpeedLimit(501);

        
        verify(mockUser, atLeastOnce()).setAlarmState(true);
    }

    @Test
    public void setSpeedLimitToLowerThanRaiseAlarm() {
        
        when(mockController.getReferenceSpeed()).thenReturn(100);
        
        trainSensor.overrideSpeedLimit(-1);

        
        verify(mockUser, atLeastOnce()).setAlarmState(true);
    }

    @Test
    public void setSpeedLimitBetweenBoundaries() {

        
        when(mockController.getReferenceSpeed()).thenReturn(100);

        
        trainSensor.overrideSpeedLimit(200);

        
        verify(mockUser, never()).setAlarmState(true);
    }

    @Test
    public void setSpeedLimitViolateRelativeMarginRaiseAlarm() {

        
        when(mockController.getReferenceSpeed()).thenReturn(150);

        
        trainSensor.overrideSpeedLimit(50);

        
        verify(mockUser, atLeastOnce()).setAlarmState(true);
    }
}