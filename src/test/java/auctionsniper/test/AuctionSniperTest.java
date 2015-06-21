package auctionsniper.test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;

@RunWith(JMock.class)
public class AuctionSniperTest {
	private final Mockery context = new Mockery();
	SniperListener  listener = context.mock(SniperListener.class);
	AuctionSniper sniper = new AuctionSniper(listener);
	
	
	@Test
	public void reportsLostWhenAuctionCloses() throws Exception {
		context.checking(new Expectations(){{
			one(listener).sniperLost();
		}});
		sniper.auctionClosed();
	}
}
