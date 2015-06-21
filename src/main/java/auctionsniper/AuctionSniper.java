package auctionsniper;

public class AuctionSniper implements AuctionEventListener{
	private SniperListener listener;

	public AuctionSniper(SniperListener listener) {
		this.listener = listener;
	}

	public void auctionClosed() {
		listener.sniperLost();
	}

	@Override
	public void currentPrice(int price, int increment) {
		
	}

}
