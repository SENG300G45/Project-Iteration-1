package software;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class PaysWithCoins {
	private SelfCheckoutStation scs;
	
	public PaysWithCoins(SelfCheckoutStation scs) {
		this.scs = scs;
	}
	
	public void pay(Coin coin) throws DisabledException {
		scs.coinSlot.accept(coin);
	}

}
