package observers;

import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

import software.StationInteractor;

/**
 * Observes events emanating from the station interactor.
 */
public interface StationInteractorObserver extends ElectronicScaleObserver {
	
	//public StationInteractorObserver() {}
	
	void scaleOverloaded(StationInteractor station);
	
	void notifyAttendant(StationInteractor station);
	
}
