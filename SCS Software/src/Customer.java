import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;


public class Customer {
	
	private Banknote B;
	private Coin C;
	private SelfCheckoutStation scs;
	
	private BarcodedItem item;
	private Barcode barcode;

	private BarcodedProduct product;
	
	public void Customer() {
		Numeral code[] = {Numeral.one, Numeral.two};
		barcode = new Barcode(code);
		item = new BarcodedItem(barcode, 10.0);
		
		BigDecimal price = new BigDecimal(100);
		product = new BarcodedProduct(barcode, "", price);
		
		
		Currency currency;
		currency = Currency.getInstance("CAD");
		int noteDenomination[] = {5, 10, 20, 50, 100};
		BigDecimal coinDenomination[] = {new BigDecimal(0.05), new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2)};
		scs = new SelfCheckoutStation(currency, noteDenomination , coinDenomination, 100, 1);
		
		scs.scanner.scan(item);
		}

		
	}
	
