package software;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;


public class ItemInBaggingArea {
	private SelfCheckoutStation scs;
	
	public ItemInBaggingArea(SelfCheckoutStation scs) {
		this.scs = scs;
	}
	
	public void PlaceItem(BarcodedItem item) {
		scs.scale.add(item);
	}
}