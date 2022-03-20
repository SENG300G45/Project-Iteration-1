package software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;



public class StationInteractor implements ElectronicScaleObserver {
	//private ArrayList<Item> items = new ArrayList<>();
	private static final int MAX_OBJECTS = 50;
	private SelfCheckoutStation selfCheckoutStation;
	private PurchasableItem[] placedItems = new PurchasableItem[MAX_OBJECTS];
	public PurchasableItem[] itemCatalog = new PurchasableItem[10]; 
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private int numberOfPlacedItems;
	public double currentWeightOnScale;
	public boolean isOverloaded = false;
	public boolean itemWeightCorrect;

	public StationInteractor(SelfCheckoutStation scs) {
		selfCheckoutStation = scs;
		scs.scale.attach(this);

		
		
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
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
		isOverloaded = false;
	}
	
	public void placeInBaggingArea(PurchasableItem purchasableItem) {
		selfCheckoutStation.scale.add(purchasableItem.item);
		
		//Case where scale is overloaded		
		if (isOverloaded == true) {
			//error
			System.out.print("urmumgae");
			itemWeightCorrect = false;
		}
		
		else {
			//Case where placed item does match item's listed weight
			if (currentWeightOnScale == purchasableItem.getWeight()) {
				System.out.print("walrus");
				itemWeightCorrect = true;
				placedItems[numberOfPlacedItems] = purchasableItem;
				numberOfPlacedItems++;
				totalBill = totalBill.add(purchasableItem.getPrice());
			}
			
			//Case where placed item does not match item's listed weight
			if (currentWeightOnScale != purchasableItem.getWeight()) {
				itemWeightCorrect = false;
			}
		

		}
			
	}
	
		
		
	


	


}
