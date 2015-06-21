package auctionsniper;
import org.hamcrest.Matchers;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import auctionsniper.ui.MainWindow;

public class AuctionSniperDriver extends JFrameDriver {

	public AuctionSniperDriver(int timeoutMilis) {
		super(new GesturePerformer(),JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME), showingOnScreen()), new AWTEventQueueProber(timeoutMilis,100));
	}

	public void showSniperStatus(Status statusText) {
		new JLabelDriver(this, named(MainWindow.SNIPER_STATUS_NAME)).hasText(Matchers.equalTo(statusText.getText()));
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
