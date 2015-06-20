import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {
	FakeAuctionServer auction = new FakeAuctionServer("54321");
	ApplicationRunner application = new ApplicationRunner();
@Test
public void sniperJoinsActionUntilActionCloses() throws Exception {
	auction.startSellingItem();
	application.startBiddingIn(auction);
	auction.hasReceivedJoinRequestFromSniper();
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
