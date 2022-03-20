package testing.checkout;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

import software.StationInteractor;

public class LowSensitivity {
	
	private static int maxWeight = 10000;
	private static int sensitivity = 1;
	
@Before
public void setup() {
	int noteDenomination[] = { 5, 10, 20, 50, 100 };
	BigDecimal coinDenomination[] = { new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), 
			new BigDecimal(1), new BigDecimal(2)};

	SelfCheckoutStation scs = new SelfCheckoutStation(Currency.getInstance("CAD"), noteDenomination, coinDenomination, maxWeight,
			sensitivity);
	StationInteractor interactor = new StationInteractor(scs);
}

@Test
public void test() {} 

}

