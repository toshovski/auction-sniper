package auctionsniper;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class FakeAuctionServer {

	private String auctionId;
	private XMPPConnection connection;
	private Chat currentChat;
	private SingleMessageListener messageListener = new SingleMessageListener();

	public FakeAuctionServer(String auctionId) {
		this.setAuctionId(auctionId);
		
		 ConnectionConfiguration connConfig = new ConnectionConfiguration(Constants.XMPP_HOST);
		 connConfig.setSASLAuthenticationEnabled(false);
		 connection = new XMPPConnection(connConfig);
		    
	}

	public void startSellingItem() throws XMPPException {
		connection.connect();
		connection.login(String.format(Constants.AUCTION_ID, auctionId), Constants.AUCTION_PASSWORD);
		connection.getChatManager().addChatListener(new ChatManagerListener() {

			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});
	}

	public void hasReceivedJoinRequestFrom(String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage(Main.CLOSE_COMMAND_FORMAT);
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public String getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}
	
	 private void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher) throws InterruptedException {
	        messageListener.receivesAMessage(messageMatcher);
	        assertThat(currentChat.getParticipant(), equalTo(sniperId));
	  }
	
	public void reportPrice(int currentPrice, int bidIncrease, String bidder) throws XMPPException {
		currentChat.sendMessage(String.format(Main.BID_COMMAND_FORMAT, currentPrice, bidIncrease, bidder));
	}

	public void hasReceivedBid(int bid, String sniper) throws InterruptedException {
		receivesAMessageMatching(sniper,equalTo(String.format(Main.BID_COMMAND_FORMAT,bid)));
	}

}
