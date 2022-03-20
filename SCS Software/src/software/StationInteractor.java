package software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import observers.ScanCheckerObserver;

import org.lsmr.selfcheckout.products.*;

import observers.ScanCheckerObserver;

public class StationInteractor implements BarcodeScannerObserver {
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation selfCheckoutStation;
	private PurchasableItem[] scannedItems = new PurchasableItem[MAX_OBJECTS];
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private int numberOfScannedItems;
	public Barcode itemBarcode;
	public double itemWeight;
	//public List<PurchasableItem> itemCatalog = new ArrayList<PurchasableItem>();
	public Map<Barcode, BarcodedItem> map = new HashMap<Barcode, BarcodedItem>();
	public BarcodedItem matchingBarcodedItem = null;

	
	
	public StationInteractor(SelfCheckoutStation scs) {
		selfCheckoutStation = scs;
		scs.scanner.attach(this);
		
		
	}
	
	public void addItemToCatalog(PurchasableItem purchasableItem) {
				map.put(purchasableItem.getCode(), purchasableItem.item);
	}
	
	

	public void scanItem(PurchasableItem purchasableItem) throws SimulationException{
		selfCheckoutStation.scanner.scan(purchasableItem.item);
		matchingBarcodedItem = map.get(itemBarcode);
		
		if (matchingBarcodedItem != null) {
			itemWeight = matchingBarcodedItem.getWeight();
			scannedItems[numberOfScannedItems] = purchasableItem;
			numberOfScannedItems++;
		}
		else {
			throw new SimulationException("Item not found in the catalog");
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
	}

}
