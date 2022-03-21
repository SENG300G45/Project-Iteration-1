package testing;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;

import static org.junit.Assert.assertEquals;

import software.PurchasableItem;


public class PurchasableItemTest {
	public PurchasableItem testApple;
	public Barcode b;
	public double testAppleWeight; 
	public BigDecimal testApplePrice;
	public String desc;
	
	@Before
	public void setup() {
		Numeral numeral[] = {Numeral.one, Numeral.two};
		b = new Barcode(numeral);
		testAppleWeight = 10.0;
		BarcodedItem testAppleBarcodedItem = new BarcodedItem(b, testAppleWeight);
		testApplePrice = new BigDecimal("1.50");
		desc = "red apple";
		testApple = new PurchasableItem(testAppleBarcodedItem,testApplePrice, desc);
	}
	
	@Test
	public void testGetCode() {
		assertEquals(testApple.getCode(), b);
	}
	
	@Test
	public void testGetPrice() {
		assertEquals(testApple.getPrice(), testApplePrice);
	}
	
	@Test
	public void testGetWeight() {
		assertEquals(testApple.getWeight(),  testAppleWeight, 0);
	}
	
	@Test
	public void testGetDescription() {
		assertEquals(testApple.getDescription(), desc);
	}
	
		
}
