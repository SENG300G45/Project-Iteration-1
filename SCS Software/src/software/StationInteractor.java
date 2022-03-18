package software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class StationInteractor {
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation scs;
	private PurchasableItem[] scannedObjects = new PurchasableItem[MAX_OBJECTS];
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private int numberOfItems;
	
	public StationInteractor(int maxWeight, int sensitivity) {
		int noteDenomination[] = {5, 10, 20, 50, 100};
		BigDecimal coinDenomination[] = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2)};
		
		scs = new SelfCheckoutStation(Currency.getInstance("CAD"), noteDenomination, coinDenomination, maxWeight, sensitivity);
		numberOfItems = 0;
	}
	
	public void scanItem(PurchasableItem purchasableItem) {
		scs.scanner.scan(purchasableItem.item);
		scannedObjects[numberOfItems] = purchasableItem;
		numberOfItems++;
	}

}
