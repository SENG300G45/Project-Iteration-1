package software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

public class StationInteractor {
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation scs;
	private PurchasableItem[] scannedItems = new PurchasableItem[MAX_OBJECTS];
	private BigDecimal totalBill = BigDecimal.valueOf(0);
<<<<<<< Updated upstream
	private int numberOfScannedItems;
=======
	private BigDecimal paidAmountWithCoins = BigDecimal.ZERO;
>>>>>>> Stashed changes
	
	public StationInteractor(int maxWeight, int sensitivity) {
		int noteDenomination[] = {5, 10, 20, 50, 100};
		BigDecimal coinDenomination[] = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2)};
		
		scs = new SelfCheckoutStation(Currency.getInstance("CAD"), noteDenomination, coinDenomination, maxWeight, sensitivity);
		numberOfScannedItems = 0;
	}
	
<<<<<<< Updated upstream
	public void scanItem(PurchasableItem purchasableItem) {
		scs.scanner.scan(purchasableItem.item);
		scannedItems[numberOfScannedItems] = purchasableItem;
		numberOfScannedItems++;
	}
=======
	public void checkout(boolean isPayingWithCoins) {
		if(isPayingWithCoins) {
			
			while(paidAmountWithCoins.compareTo(totalBill) < 0) {
				Coin dollar = new Coin(scs.coinDenominations.get(3));

				try {
					addCoin(dollar);
				} catch (DisabledException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			// Pay with banknotes
		}
		
	}
	
	private void addCoin(Coin coin) throws DisabledException {
		
		CoinChecker checker = new CoinChecker();
		scs.coinValidator.attach(checker);
		scs.coinValidator.accept(coin);
		if(checker.checkValid()) {
			paidAmountWithCoins.add(checker.getValue());
		}
		
		
	}
	
>>>>>>> Stashed changes

}
