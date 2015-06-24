package auctionsniper.test;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import auctionsniper.Auction;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;

@RunWith(JMock.class)
public class AuctionSniperTest {
	private final Mockery context = new Mockery();
	SniperListener  listener = context.mock(SniperListener.class);
	private final Auction auction = context.mock(Auction.class);
	private final AuctionSniper sniper = new AuctionSniper(auction, listener);
	
	
	@Test
	public void reportsLostWhenAuctionCloses() throws Exception {
		context.checking(new Expectations(){{
			one(listener).sniperLost();
		}});
		sniper.auctionClosed();
	}
	
	@Test
	public void bidsHigherAndReportsBiddingWhenNewPriceArrives() throws Exception {
		final int price = 1001;
		final int increment = 25;
		context.checking(new Expectations(){{
			one(auction).bid(price+increment);
			atLeast(1).of(listener).sniperBidding();
		}});
	}
}
