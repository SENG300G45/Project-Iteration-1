package software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

public class BanknoteChecker implements BanknoteValidatorObserver {
	private int value;
	private boolean isValid;
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		this.value = value; 
		this.isValid = true;
		
	}

	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		this.isValid = false;
		//it will switch
		
		
	}
	
	public int getValue() {
		return value;
	}
	
	
	public boolean checkValid() {
		return isValid;
		
	}

}
