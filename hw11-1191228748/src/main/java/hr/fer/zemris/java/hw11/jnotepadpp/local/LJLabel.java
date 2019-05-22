package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Label (abstract) that uses localization for this label.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LJLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	/**
	 * Key value for using localization and getting strings.
	 */
	String key;
	/**
	 * Listener to localization changing.
	 */
	private ILocalizationListener listener;
	/**
	 * Provider for localization.
	 */
	private ILocalizationProvider prov;

	/**
	 * Constructs a new label that changes depending on localization change.
	 * 
	 * @param key key value which we use to retrieve translation.
	 * @param lp  location provider that provides the translations.
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		this.prov = lp;

		updateLabel();

		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				updateLabel();
			}
		};

		prov.addLocalizationListener(listener);

	}

	/**
	 * Updates label based on localization.
	 */
	private void updateLabel() {
		this.setText(this.prov.getString(key));
	}

}
