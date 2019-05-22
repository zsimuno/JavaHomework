package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class that is a localization provider to a frame. It connects the frame to a
 * localization provider when it opens and disconnects when it closes.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor that adds a listener to the given frame that connects/disconnects
	 * based on the frame being open/closed.
	 * 
	 * @param parent parent of this provider.
	 * @param frame  frame in which the provider is used.
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
