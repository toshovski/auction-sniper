
public class ApplicationRunner {

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + "localhost" + "/"
            + Main.AUCTION_RESOURCE;
	private AuctionSniperDriver driver;

	public void startBiddingIn(FakeAuctionServer auction) {
		Thread thread = new Thread("Fake Application"){
			public void run() {
				try{
					
					Main.main(Constants.XMPP_HOST,Constants.SNIPER,Constants.SNIPER_PASSWORD, auction.getAuctionId());
				}catch(Exception exception){
					exception.printStackTrace();
				}
			};
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		driver.showSniperStatus(Status.JOINING);
	}

	public void showSniperHasLostAuction() {
		driver.showSniperStatus(Status.LOST);
		
	}

	public void stop() {
		if(driver!=null){
			driver.dispose();
		}
		
	}

	public void hasShownSniperIsBidding() {
		driver.showSniperStatus(Status.BIDDING);
	}

}
