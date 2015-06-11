import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.junit.Assert;


public class FakeAuctionServer {

	private String auctionId;
	private XMPPConnection connection;
	private Chat currentChat;
	private SingleMessageListener messageListener = new SingleMessageListener();

	public FakeAuctionServer(String auctionId) {
		this.setAuctionId(auctionId);
		connection = new XMPPConnection(Constants.XMPP_HOST);
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

	public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
		messageListener.recievesMessage();
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage(new Message());
		
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
	
	
	public class SingleMessageListener implements MessageListener{
		ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);
		@Override
		public void processMessage(Chat arg0, Message message) {
			messages.add(message);
		}
		
		public void recievesMessage() throws InterruptedException{
			Assert.assertThat("Message",messages.poll(5,TimeUnit.SECONDS),Matchers.is(Matchers.notNullValue()));
		}
		
	}

}
