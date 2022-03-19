package software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

import observers.BanknoteCheckerObserver;
import observers.CoinCheckerObserver;


public class StationInteractor {
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation scs;
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private BigDecimal paidAmountWithCoins = BigDecimal.ZERO;
	private int paidAmountWithBanknote = 0;
	
	public StationInteractor(int maxWeight, int sensitivity) {
		int noteDenomination[] = {5, 10, 20, 50, 100};
		BigDecimal coinDenomination[] = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2)};
		
		scs = new SelfCheckoutStation(Currency.getInstance("CAD"), noteDenomination, coinDenomination, maxWeight, sensitivity);
	}
	
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
			
			while((float) paidAmountWithBanknote < totalBill.floatValue()) {
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
		if(checker.checkValid()) {
			paidAmountWithCoins.add(checker.getValue());
		}
		
		
	}
	
	private void addBanknote(Banknote note) throws DisabledException {
		BanknoteCheckerObserver checker = new BanknoteCheckerObserver();
		scs.banknoteValidator.attach(checker);
		scs.banknoteValidator.accept(note);
		if(checker.checkValid()) {
			paidAmountWithBanknote += checker.getValue();
		}
		
	}
	

}
