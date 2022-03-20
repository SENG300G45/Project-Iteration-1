package software;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.Product;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

import observers.BanknoteCheckerObserver;
import observers.CoinCheckerObserver;


public class StationInteractor implements CoinValidatorObserver, BanknoteValidatorObserver {
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation selfCheckoutStation;
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private BigDecimal paidAmountWithCoins = BigDecimal.ZERO;
	private int paidAmountWithBanknote = 0;
	public Map<Barcode, BarcodedProduct> map = new HashMap<Barcode, BarcodedProduct>();
	public int banknoteValue;
	public BigDecimal coinValue;
	public StationInteractor(SelfCheckoutStation scs) {
		selfCheckoutStation = scs;
		scs.coinValidator.attach(this);
		scs.banknoteValidator.attach(this);
		
		//List of Purchasableitems in catalog
		Numeral numeral[] = {Numeral.one, Numeral.two};
		Barcode b = new Barcode(numeral);
		BigDecimal redApplePrice = new BigDecimal("1.50");
		BarcodedProduct redAppleBarcodedProduct = new BarcodedProduct(b, "red apple", redApplePrice);
		//PurchasableItem redApple = new PurchasableItem(redAppleBarcodedItem, redApplePrice, "red apple");
		
		//itemCatalog.add(redApple);
		map.put(b, redAppleBarcodedProduct);
	}
	
	public void checkout(boolean isPayingWithCoins) {
		if(isPayingWithCoins) {
			
			while(paidAmountWithCoins.compareTo(totalBill) < 0) {
				Coin dollar = new Coin(selfCheckoutStation.coinDenominations.get(3));

				try {
					addCoin(dollar);
				} catch (DisabledException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			
			while((float) paidAmountWithBanknote < totalBill.floatValue()) {
				Banknote fiveDollarBill = new Banknote(Currency.getInstance("CAD"), selfCheckoutStation.banknoteDenominations[0]);
				try {
					addBanknote(fiveDollarBill);
				} catch (DisabledException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	private void addCoin(Coin coin) throws DisabledException {
		
//		CoinCheckerObserver checker = new CoinCheckerObserver();
//		scs.coinValidator.attach(checker);
//		scs.coinValidator.accept(coin);
//		if(checker.checkValid()) {
//			paidAmountWithCoins.add(checker.getValue());
//		}
		paidAmountWithCoins = paidAmountWithCoins.add(coinValue);
		
	}
	
	private void addBanknote(Banknote note) throws DisabledException {
//		BanknoteCheckerObserver checker = new BanknoteCheckerObserver();
//		scs.banknoteValidator.attach(checker);
//		scs.banknoteValidator.accept(note);
//		if(checker.checkValid()) {
//			paidAmountWithBanknote += checker.getValue();
//		}
		paidAmountWithBanknote += banknoteValue;
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		
	}

	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		banknoteValue = value;
	}

	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		
	}

	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		coinValue = value;
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		
	}
	

}
