package software;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class PurchasableItem {
	private Barcode code;
	public BarcodedProduct product;
	public BarcodedItem item;
	
	/**PurchasableItem is an object that associates an item to a price and a description
	 * @param item
	 * @param price
	 * @param description
	 */
	public PurchasableItem(BarcodedItem item, BigDecimal price, String description) {
		this.item = item;
		this.code = item.getBarcode();
		this.product = new BarcodedProduct(code, description, price);		
	}
	
	//get barcode
	public Barcode getCode() {
		return this.code;
	}
	
	//get price of associated barcodedItem
	public BigDecimal getPrice() {
		return this.product.getPrice();
	}
	
	//get weight associated barcodedItem
	public double getWeight() {
		return this.item.getWeight();
	}
	
	//get description of associated barcodedItem
	public String getDescription() {
		return this.product.getDescription();
	}
	
}
