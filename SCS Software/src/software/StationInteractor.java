package software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import observers.ScanCheckerObserver;

import observers.ScaleCheckObserver;
import observers.StationInteractorObserver;

public class StationInteractor {
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation scs;
	private PurchasableItem[] placedItems = new PurchasableItem[MAX_OBJECTS];
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private int numberOfPlacedItems;

	private StationInteractorObserver stationObserver;

	public StationInteractor(int maxWeight, int sensitivity) {
		int noteDenomination[] = { 5, 10, 20, 50, 100 };
		BigDecimal coinDenomination[] = { new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25),
				new BigDecimal(1), new BigDecimal(2) };

		scs = new SelfCheckoutStation(Currency.getInstance("CAD"), noteDenomination, coinDenomination, maxWeight,
				sensitivity);
		stationObserver = new StationInteractorObserver();
		numberOfPlacedItems = 0;
	}

	/**
	 * The user places the item on the electronic scale.
	 * 
	 * @param PurchasableItem
	 *                        The item to scan.
	 */
	public void placeItem(PurchasableItem purchasableItem) {
		ScaleCheckObserver observer = new ScaleCheckObserver();
		scs.scale.attach(observer);

		// TODO Add a loop that checks if the item has been scanned
		if (!observer.checkFull()) {
			scs.scale.add(purchasableItem.item);
			placedItems[numberOfPlacedItems] = purchasableItem;
			numberOfPlacedItems++;

			// Adds the price of the item to the total bill
			totalBill = totalBill.add(purchasableItem.getPrice());
		} else {
			notifyScaleFull();
		}

	}

	/**
	 * The user scans an item.
	 * 
	 * @param PurchasabeItem
	 *            The item to scan.
	 */
	public void scanItem(PurchasableItem purchasableItem) {
		ScanCheckerObserver observer = new ScanCheckerObserver();
		scs.scanner.attach(observer);
		while(observer.getBarcode() == null) {
			scs.scanner.scan(purchasableItem.item);
		}
		
		scannedItems[numberOfScannedItems] = purchasableItem;
		numberOfScannedItems++;

	private void notifyScaleFull() {
		stationObserver.scaleOverloaded(this);
	}

}
