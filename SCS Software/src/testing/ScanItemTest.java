package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.*;

import software.PurchasableItem;
import software.StationInteractor;


public class ScanItemTest {
	private SelfCheckoutStation scs;
	private StationInteractor si;
	PurchasableItem testApple; 
	PurchasableItem testApple2; 

	@Before
	public void setup() {
		int maxWeight = 100;
		int sensitivity = 5;
		int noteDenomination[] = {5, 10, 20, 50, 100};
		BigDecimal coinDenomination[] = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2)};
		scs = new SelfCheckoutStation(Currency.getInstance("CAD"), noteDenomination, coinDenomination, maxWeight, sensitivity);
		si = new StationInteractor(scs);
		
		Numeral numeral[] = {Numeral.one, Numeral.two};
		Barcode b = new Barcode(numeral);
		BarcodedItem testAppleBarcodedItem = new BarcodedItem(b, 10.0);
		BigDecimal testApplePrice = new BigDecimal("1.50");
		testApple = new PurchasableItem(testAppleBarcodedItem,testApplePrice, "red apple");
		
		Numeral numeral2[] = {Numeral.three, Numeral.two};
		Barcode b2 = new Barcode(numeral2);
		BarcodedItem testAppleBarcodedItem2 = new BarcodedItem(b2, 10.0);
		BigDecimal testApplePrice2 = new BigDecimal("1.50");
		testApple2 = new PurchasableItem(testAppleBarcodedItem2,testApplePrice2, "red apple");
	
	}
	@Test
	public void testItemCodeNull() {
		si.scanItem(testApple2);
		System.out.print(testApple.getCode());
		assertNull(si.matchingBarcodedItem);
	}
	
	@Test
	public void testItemNotNull() {
		si.scanItem(testApple);
		assertEquals(testApple.getWeight(), si.itemWeight, 0);
	}


	}















