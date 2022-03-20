package software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.lsmr.selfcheckout.Item;
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
	private BigDecimal totalBill = BigDecimal.valueOf(0);
	private int numberOfPlacedItems;
	private double currentWeightOnScale;
	private boolean isOverloaded;
	
	//private StationInteractorObserver stationObserver;
	
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
		currentWeightOnScale += weightInGrams;
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
		
		//Case where scale is overloaded
		if (isOverloaded == true) {
			//error
		}
		
		//Case where placed item does not match item's suppose weight
		if (isOverloaded == false && currentWeightOnScale != purchasableItem.getWeight()) {
			//error
		}
		
		if (isOverloaded == false && currentWeightOnScale == purchasableItem.getWeight()) {
			placedItems[numberOfPlacedItems] = purchasableItem;
			numberOfPlacedItems++;
			totalBill = totalBill.add(purchasableItem.getPrice());
		}
			
		
	}
	
		
		
	


	


}
