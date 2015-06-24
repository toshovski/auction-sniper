package auctionsniper;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import sun.reflect.generics.tree.FieldTypeSignature;

public class AuctionMessageTranslator implements MessageListener {

	private AuctionEventListener listener;

	public AuctionMessageTranslator(AuctionEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		AuctionEvent event = AuctionEvent.from(message.getBody());
		String type = event.type();
		switch (type) {
		case "CLOSE":
			listener.auctionClosed();
			break;
		case "PRICE":
			listener.currentPrice(event.currentPrice(),event.increment());
			break;
		default:
			break;
		}
	}

	private static class AuctionEvent {
		private final Map<String, String> fields = new HashMap<String, String>();
		
		static AuctionEvent from(String messageBody){
			AuctionEvent event = new AuctionEvent();
			for(String field: fieldsIn(messageBody)){
				event.addField(field);
			}
			return event;
		}
		
		private static String[] fieldsIn(String messageBody) {
			return messageBody.split(";");
		}

		public String type() {
			return get("Event");
		};

		private String get(String string) {
			return fields.get(string);
		};

		private int currentPrice() {
			return getInt("CurrentPrice");
		};

		private int getInt(String string) {
			return Integer.parseInt(get(string));
		}

		private void addField(String field) {
			String[] pair = field.split(":");
			fields.put(pair[0].trim(), pair[1].trim());
		}

		private int increment() {
			return getInt("Increment");
		};

	}

}
