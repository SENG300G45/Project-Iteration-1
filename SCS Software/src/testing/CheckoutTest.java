package testing;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import software.PurchasableItem;
import software.StationInteractor;

public class CheckoutTest {
	private StationInteractor si;
	private SelfCheckoutStation scs;
	private PurchasableItem testApple;
	
	
	
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
		
		si.addToCatalog(testApple.item);
		si.scanItem(testApple);
		si.placeInBaggingArea(testApple);
	}
	
	@Test(expected = SimulationException.class)
	public void testPayWithNullCoins() throws DisabledException {
		si.addCoin(null);
	}
	
	@Test(expected = SimulationException.class)
	public void testPayWithNullBanknotes() throws DisabledException {
		si.addBanknote(null);
	}
	
	@Test
	public void testPayWithInvalidCoins() throws DisabledException {
		Coin coin = new Coin(Currency.getInstance("SGD"), BigDecimal.ONE);
		si.addCoin(coin);
		assertTrue(si.attendantCalled);
	}
	
	@Test
	public void testPayWithInvalidBanknotes() throws DisabledException {
		Banknote note = new Banknote(Currency.getInstance("SGD"), 1);
		si.addBanknote(note);
		assertTrue(si.attendantCalled);
	}
	
	@Test
	public void testPayWithCoins() throws DisabledException {
		// Paying with coins
		si.checkout(true);
		assertTrue(si.paidAmountWithCoins.compareTo(BigDecimal.ZERO)>0);
	}
	
	@Test
	public void testPaysWithBanknotes() throws DisabledException {
		// Paying with banknotes
		si.checkout(false);
		assertTrue(si.paidAmountWithBanknote>0);
	}
	
}
