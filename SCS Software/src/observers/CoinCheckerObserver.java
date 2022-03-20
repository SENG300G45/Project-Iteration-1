package observers;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

public class CoinCheckerObserver implements CoinValidatorObserver{
	private BigDecimal value;
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
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		this.value = value;
		this.isValid = true;
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		this.isValid = false;
		
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public boolean checkValid() {
		return isValid;
		
	}

}
