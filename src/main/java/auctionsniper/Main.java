package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import auctionsniper.ui.MainWindow;

public class Main implements SniperListener {
	public static final String MAIN_WINDOW_NAME = "Sniper Auction";

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID = 3;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";

	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %s;";
	public static final String REPORT_PRICE_FORMAT = "SOLVersion: 1.1; Event PRICE; CurrentPrice: %d; Increment: %d, Bidder: %s;";
	public static final String CLOSE_COMMAND_FORMAT = "SOLVersion: 1.1; Event: CLOSE;";

	private MainWindow ui;

	private Chat notToBeGCd;

	public static void main(String... args) throws Exception {
		Main main = new Main();
		main.joinAuction(connectTo(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]),
				new Integer(args[ARG_ITEM_ID]));

	}

	private void joinAuction(XMPPConnection connection, int itemId) throws XMPPException {
		disconnectWhenUICloses(connection);
		final Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection),null);

		Auction auction = new Auction(){
			public void bid(int amount) {
				try {
					chat.sendMessage(String.format(BID_COMMAND_FORMAT, amount));
				} catch (XMPPException e) {
					e.printStackTrace();
				}
			};
		};
		
		chat.addMessageListener(new AuctionMessageTranslator(new AuctionSniper(auction,this)));
		chat.sendMessage(JOIN_COMMAND_FORMAT);
		this.notToBeGCd = chat;
	}

	private void disconnectWhenUICloses(XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				connection.disconnect();
			}
		});
	}

	private static XMPPConnection connectTo(String hostname, String username, String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		return connection;
	}

	private static String auctionId(int itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}

	public Main() throws Exception {
		startUserInterface();
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow();
			}
		});
	}

	@Override
	public void sniperLost() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ui.showStatus(Status.LOST);
			}
		});
	}

	@Override
	public void sniperBidding() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ui.showStatus(Status.BIDDING);
			}
		});

	}
}
	