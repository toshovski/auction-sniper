import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame{
	public static final String SNIPER_STATUS_NAME = "sniper status";
	private final JLabel sniperStatus = createLabel(Status.JOINING);

	public MainWindow(){
		super(Main.MAIN_WINDOW_NAME);
		setSize(150, 50);
		setName(Main.MAIN_WINDOW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(sniperStatus);
		setVisible(true);
	}
	
	
	private JLabel createLabel(Status joining) {
		JLabel result = new JLabel(joining.getText());
		result.setName(SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.BLACK));
		return result;
	}



	public void showStatus(Status status) {
		sniperStatus.setText(status.getText());
	}

}
