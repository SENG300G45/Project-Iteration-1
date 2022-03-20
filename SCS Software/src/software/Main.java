package software;

import java.math.BigDecimal;
import java.util.Random;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;

public class Main {
	private static final int MAX_OBJECTS = 50;
	
	public static void main(String args[]) {
		StationInteractor station = new StationInteractor(10000, 1);
		PurchasableItem sampleItem = GenerateItem(100, new BigDecimal(20), "ABC Soap");
		
		station.placeItem(sampleItem);
		
	}
	
	private static PurchasableItem GenerateItem(double weight, BigDecimal price, String desc) {
		/**
		 * Generates an item with a random barcode.
		 *  
		 * @param weight
		 *            Assign a weight to the random item
		 */
		final int MAX_CODES = 12;
		
		PurchasableItem item;
		Numeral code[] = new Numeral[MAX_CODES];
		Random rand = new Random();
		
		for(int i=0; i < MAX_CODES; i++) {
			code[i] = Numeral.valueOf((byte) rand.nextInt(10));
		}
		
		Barcode barcode = new Barcode(code);
		return item = new PurchasableItem(new BarcodedItem(barcode,weight), price, desc);
	}

}
