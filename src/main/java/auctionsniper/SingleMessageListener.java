package auctionsniper;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SingleMessageListener implements MessageListener{
	ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);
	@Override
	public void processMessage(Chat arg0, Message message) {
		System.out.println("Received a message: "+message.getBody());
		messages.add(message);
	}

	public void receivesAMessage(Matcher<? super String> matcher) throws InterruptedException{
		final Message message = messages.poll(5,TimeUnit.SECONDS);
		System.out.println("Auction server receives a message "+message);
		assertThat("Message shoudn't be a null", message, is(notNullValue()));
		assertThat("Matching message format",message.getBody(), matcher);
	}
	
}