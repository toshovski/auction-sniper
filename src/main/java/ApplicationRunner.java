
public class ApplicationRunner {

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

}
