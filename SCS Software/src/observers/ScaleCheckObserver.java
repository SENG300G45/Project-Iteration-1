package observers;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class ScaleCheckObserver implements ElectronicScaleObserver{
	private double weightInGrams;
	private boolean isFull;
	
	public ScaleCheckObserver() {
		isFull = false;
		weightInGrams = 0.0;
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		this.weightInGrams = weightInGrams;
		
	}

	@Override
	public void overload(ElectronicScale scale) {
		isFull = true;
		
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		isFull = false;
		
	}
	
	public boolean checkFull() {
		return isFull;
	}
	
	public double getLastWeight() {
		return weightInGrams;
	}

}
