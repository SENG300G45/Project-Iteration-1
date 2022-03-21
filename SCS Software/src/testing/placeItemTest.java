package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;

import observers.StationInteractorObserver;
import software.PurchasableItem;
import software.StationInteractor;

public class placeItemTest {
	private SelfCheckoutStation scs;
	private StationInteractor si;
	private ElectronicScale scale;
	private PurchasableItem bigApple;
	private PurchasableItem normalApple;
	private PurchasableItem notApple;
	private PurchasableItem testApple;
	private int found;

	
	private class PurchasableItemStub extends PurchasableItem {
		protected PurchasableItemStub (BarcodedItem item, BigDecimal price, String description) {
			super(item, price, description);
		}	
	}
	
	@Before
	public void setup() {
		//interactor = new StationInteractor(10, 10);
		Numeral numeral[] = {Numeral.one, Numeral.two};
		Barcode b = new Barcode(numeral);
		BarcodedItem testAppleBarcodedItem = new BarcodedItem(b, 10.0);
		BigDecimal testApplePrice = new BigDecimal("1.50");
		testApple = new PurchasableItem(testAppleBarcodedItem,testApplePrice, "red apple");
		
		BarcodedItem heavyItem = new BarcodedItem(b,110.0);
		BarcodedItem acceptableItem = new BarcodedItem(b,10.0);
		BarcodedItem unusualItem = new BarcodedItem(b, 11.0);
		
		bigApple = new PurchasableItemStub(heavyItem, BigDecimal.ONE, "apple");
		normalApple = new PurchasableItemStub(acceptableItem, BigDecimal.ONE, "apple");
		//notApple = new PurchasableItemStub(unusualItem, BigDecimal.ONE, "not an apple");
		
		notApple = new PurchasableItem(unusualItem, BigDecimal.ONE, "not an apple");
		
		int maxWeight = 100;
		int sensitivity = 5;
		int noteDenomination[] = {5, 10, 20, 50, 100};
		BigDecimal coinDenomination[] = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2)};
		scs = new SelfCheckoutStation(Currency.getInstance("CAD"), noteDenomination, coinDenomination, maxWeight, sensitivity);
		si = new StationInteractor(scs);
		
		si.addToCatalog(heavyItem);
		si.addToCatalog(acceptableItem);
	}
	
	
	@Test
	public void testItemNotNull() {
		si.scanItem(testApple);
		assertEquals(testApple.getWeight(), si.itemWeight, 0);
	}
	
	//Test if adding a heavy item overloads the scale
	@Test
	public void testItemWeightOverloads() {
		si.placeInBaggingArea(bigApple);
		assertEquals(true, si.isOverloaded);
	}
	
	//Test if weighed item does match item's listed weight in database
@Test
	public void testItemWeightMatches() {
		si.scanItem(testApple);
		si.placeInBaggingArea(testApple);
		
		//assertEquals(si.itemWeightCorrect, si.currentWeightOnScale);
		assertTrue(si.itemWeightCorrect);
	}
	
	//Test if weighed item does not match item's listed weight in database
	@Test
	public void testItemWeightNoMatch() {
		si.placeInBaggingArea(notApple);
		assertFalse(si.itemWeightCorrect);
	}
	

	
	
}
