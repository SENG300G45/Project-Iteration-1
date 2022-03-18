package software;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class PurchasableItem {
	private Barcode code;
	public BarcodedProduct product;
	public BarcodedItem item;
	
	public PurchasableItem(BarcodedItem item, BigDecimal price, String description) {
		this.item = item;
		this.code = item.getBarcode();
		this.product = new BarcodedProduct(code, description, price);		
	}
	
	public Barcode getCode() {
		return this.code;
	}
	
	public BigDecimal getPrice() {
		return this.product.getPrice();
	}
	
	public double getWeight() {
		return this.item.getWeight();
	}
	
	public String getDescription() {
		return this.product.getDescription();
	}
	
	public void displayInfo() {
		System.out.println("DESC.: " + this.getDescription());
		System.out.println("PRICE: " + this.getPrice());
	}
}
