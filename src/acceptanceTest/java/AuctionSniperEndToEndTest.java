import org.junit.After;
import org.junit.Test;

import auctionsniper.ApplicationRunner;
import auctionsniper.Constants;
import auctionsniper.FakeAuctionServer;

public class AuctionSniperEndToEndTest {
	FakeAuctionServer auction = new FakeAuctionServer("54321");
	ApplicationRunner application = new ApplicationRunner();
@Test
public void sniperJoinsActionUntilActionCloses() throws Exception {
	auction.startSellingItem();
	
	application.startBiddingIn(auction);
	auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
	System.out.println("Report price");
	auction.reportPrice(1000,98,"other bidder");
	
	application.hasShownSniperIsBidding();
	
	System.out.println("New bit received");
	auction.hasReceivedBid(1098, Constants.SNIPER);
	auction.announceClosed();
	application.showSniperHasLostAuction();
}

@After
public void stopAuction(){
	auction.stop();
}
@After
public void stopApplication(){
	application.stop();
}
}
