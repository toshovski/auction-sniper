package auctionsniper.test;

import static org.junit.Assert.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;

import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionMessageTranslator;

@RunWith(JMock.class)
public class AuctionMessageTranslatorTest {
	public static final Chat UNUSED_CHAT = null;
	private final Mockery context = new Mockery();
	private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);
	
	
	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() throws Exception {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		context.checking(new Expectations(){{
			oneOf(listener).auctionClosed();
		}});
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	@Test
	public void notifiesBidDetailsWhenCurrentPriceMessageReceived() throws Exception {
		context.checking(new Expectations(){{
			exactly(1).of(listener).currentPrice(192,7);
		}});
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		translator.processMessage(UNUSED_CHAT, message);
	}
}
