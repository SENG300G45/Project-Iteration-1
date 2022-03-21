package software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class StationInteractor implements ElectronicScaleObserver, BarcodeScannerObserver {
	// private ArrayList<Item> items = new ArrayList<>();
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation selfCheckoutStation;
	private PurchasableItem[] placedItems = new PurchasableItem[MAX_OBJECTS];
	public PurchasableItem[] itemCatalog = new PurchasableItem[10];
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private int numberOfPlacedItems;
	public double currentWeightOnScale;
	public boolean isOverloaded = false;
	public boolean itemWeightCorrect;
	public Map<Barcode, BarcodedItem> map = new HashMap<Barcode, BarcodedItem>();
	public BarcodedItem matchingBarcodedItem = null;
	public Barcode itemBarcode;
	public double itemWeight;
	private PurchasableItem[] scannedItems = new PurchasableItem[MAX_OBJECTS];
	private int numberOfScannedItems;

	public StationInteractor(SelfCheckoutStation scs) {
		selfCheckoutStation = scs;
		selfCheckoutStation.scale.attach(this);
		selfCheckoutStation.scanner.attach(this);

		// //List of Purchasableitems in catalog
		// Numeral numeral[] = {Numeral.one, Numeral.two};
		// Barcode b = new Barcode(numeral);
		// BarcodedItem redAppleBarcodedItem = new BarcodedItem(b, 10.0);
		// BigDecimal redApplePrice = new BigDecimal("1.50");
		// //PurchasableItem redApple = new PurchasableItem(redAppleBarcodedItem,
		// redApplePrice, "red apple");
		//
		// //itemCatalog.add(redApple);
		// map.put(b, redAppleBarcodedItem);
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
	 */
	public void checkout(boolean isPayingWithCoins) {
		if (isPayingWithCoins) {

			while (paidAmountWithCoins.compareTo(totalBill) < 0) {
				Coin dollar = new Coin(scs.coinDenominations.get(3));

				try {
					addCoin(dollar);
				} catch (DisabledException e) {
					e.printStackTrace();
				}
			}
		} else {

			while ((float) paidAmountWithBanknote < totalBill.floatValue()) {
				Banknote fiveDollarBill = new Banknote(Currency.getInstance("CAD"), scs.banknoteDenominations[0]);
				try {
					addBanknote(fiveDollarBill);
				} catch (DisabledException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private void addCoin(Coin coin) throws DisabledException {

		CoinCheckerObserver checker = new CoinCheckerObserver();
		scs.coinValidator.attach(checker);
		scs.coinValidator.accept(coin);
		if (checker.checkValid()) {
			paidAmountWithCoins.add(checker.getValue());
		}

	}

	private void addBanknote(Banknote note) throws DisabledException {
		BanknoteCheckerObserver checker = new BanknoteCheckerObserver();
		scs.banknoteValidator.attach(checker);
		scs.banknoteValidator.accept(note);
		if (checker.checkValid()) {
			paidAmountWithBanknote += checker.getValue();
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

}
