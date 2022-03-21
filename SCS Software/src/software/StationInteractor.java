package software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

import observers.BanknoteCheckerObserver;
import observers.CoinCheckerObserver;
import observers.StationInteractorObserver;

public class StationInteractor implements ElectronicScaleObserver, BarcodeScannerObserver, StationInteractorObserver {

	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation selfCheckoutStation;
	private PurchasableItem[] placedItems = new PurchasableItem[MAX_OBJECTS];
	public PurchasableItem[] itemCatalog = new PurchasableItem[10];
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private int numberOfPlacedItems;
	private double currentWeightOnScale;
	public boolean isOverloaded = false;
	public boolean itemWeightCorrect;
	private Map<Barcode, BarcodedItem> map = new HashMap<Barcode, BarcodedItem>();
	private BarcodedItem matchingBarcodedItem = null;
	public Barcode itemBarcode;
	public double itemWeight;
	private PurchasableItem[] scannedItems = new PurchasableItem[MAX_OBJECTS];
	private int numberOfScannedItems;
	public BigDecimal paidAmountWithCoins;
	public float paidAmountWithBanknote;
	public boolean attendantCalled;

	public StationInteractor(SelfCheckoutStation scs) {
		selfCheckoutStation = scs;
		selfCheckoutStation.scale.attach(this);
		selfCheckoutStation.scanner.attach(this);
		
		paidAmountWithCoins = BigDecimal.ZERO;
		paidAmountWithBanknote = 0;
		
		attendantCalled = false;
	}

	public void addToCatalog(BarcodedItem item) {
		map.put(item.getBarcode(), item);
	}

	public void scanItem(PurchasableItem purchasableItem) throws SimulationException {

		itemBarcode = null;
		while (itemBarcode == null) {
			selfCheckoutStation.scanner.scan(purchasableItem.item);
		}

		matchingBarcodedItem = null;
		matchingBarcodedItem = map.get(itemBarcode);

		if (matchingBarcodedItem != null) {
			itemWeight = matchingBarcodedItem.getWeight();
			scannedItems[numberOfScannedItems] = purchasableItem;
			numberOfScannedItems++;
		} else {
			throw new SimulationException("Item not in the catalog");
		}

	}

	public void placeInBaggingArea(PurchasableItem purchasableItem) {
		// scanItem(purchasableItem);
		selfCheckoutStation.scale.add(purchasableItem.item);

		// Case where scale is overloaded
		if (isOverloaded == true) {
			// error
			itemWeightCorrect = false;
			notifyAttendant(this);
		}

		else {
			// Case where placed item does match item's listed weight
			if (currentWeightOnScale == itemWeight) {

				itemWeightCorrect = true;

				placedItems[numberOfPlacedItems] = purchasableItem;
				numberOfPlacedItems++;
				totalBill = totalBill.add(purchasableItem.getPrice());

			}

			// Case where placed item does not match item's listed weight
			if (currentWeightOnScale != itemWeight) {
				itemWeightCorrect = false;
			}
		}

	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}

	/**
	 * The user wishes to checkout
	 * 
	 * @param isPayingWithCoins
	 *                          True if the user wants to pay with coins, and False
	 *                          when the user wants to pay with banknotes.
	 * @throws DisabledException 
	 */
	public void checkout(boolean isPayingWithCoins) throws DisabledException {
		if (isPayingWithCoins) {
			
			while (paidAmountWithCoins.compareTo(totalBill) < 0) {
				
				BigDecimal coinValue = selfCheckoutStation.coinDenominations.get(3);
				Coin dollar = new Coin(Currency.getInstance("CAD"),coinValue);
				addCoin(dollar);
			}
		} else {

			while ((float) paidAmountWithBanknote < totalBill.floatValue()) {
				Banknote fiveDollarBill = new Banknote(Currency.getInstance("CAD"), selfCheckoutStation.banknoteDenominations[0]);
				addBanknote(fiveDollarBill);
			}

		}

	}

	public void addCoin(Coin coin) throws DisabledException {

		CoinCheckerObserver checker = new CoinCheckerObserver();
		selfCheckoutStation.coinValidator.attach(checker);
		selfCheckoutStation.coinValidator.accept(coin);
		if (checker.checkValid()) {
			paidAmountWithCoins = paidAmountWithCoins.add(checker.getValue());
		}
		else {
			notifyAttendant(this);
		}

	}

	public void addBanknote(Banknote note) throws DisabledException {
		BanknoteCheckerObserver checker = new BanknoteCheckerObserver();
		selfCheckoutStation.banknoteValidator.attach(checker);
		selfCheckoutStation.banknoteValidator.accept(note);
		if (checker.checkValid()) {
			paidAmountWithBanknote += checker.getValue();
		}
		else {
			notifyAttendant(this);
		}

	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}

	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		isOverloaded = false;
		currentWeightOnScale = weightInGrams;
	}

	@Override
	public void overload(ElectronicScale scale) {
		isOverloaded = true;
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		// isOverloaded = false;
	}

	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		itemBarcode = barcode;
	}

	@Override
	public void notifyAttendant(StationInteractor station) {
		attendantCalled = true;
	}

}
