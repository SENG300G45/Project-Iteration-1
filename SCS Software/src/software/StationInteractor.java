package software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;

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
	public Map<Barcode, BarcodedItem> map = new HashMap<Barcode, BarcodedItem>();

	
	
	public StationInteractor(SelfCheckoutStation scs) {
		selfCheckoutStation = scs;
		scs.scanner.attach(this);		
	}
	
	public void addItemToCatalog(PurchasableItem purchasableItem) {
				map.put(purchasableItem.getCode(), purchasableItem.item);
	}
	
	/**
	 * The user scans an item.
	 * 
	 * @param PurchasabeItem
	 *            The item to scan.
	 */
	public void scanItem(PurchasableItem purchasableItem) {
		selfCheckoutStation.scanner.scan(purchasableItem.item);
		BarcodedItem matchingBarcodedItem = map.get(itemBarcode);
		
		if (matchingBarcodedItem != null) {
			itemWeight = matchingBarcodedItem.getWeight();
			scannedItems[numberOfScannedItems] = purchasableItem;
			numberOfScannedItems++;
		}
		else {
			throw new SimulationException("Item not found in the catalog");
		}
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		// TODO Auto-generated method stub
		
	}

}
