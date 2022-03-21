package observers;

import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

import software.StationInteractor;

/**
 * Observes events emanating from the station interactor.
 */
public interface StationInteractorObserver extends ElectronicScaleObserver {
	void notifyAttendant(StationInteractor station);
	
}
