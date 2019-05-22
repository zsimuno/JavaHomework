package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Menu (abstract) that uses localization for this menu's name.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LJMenu extends JMenu {

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
	 * Constructs a new menu that changes depending on localization change.
	 * 
	 * @param key key value which we use to retrieve translation.
	 * @param lp  location provider that provides the translations.
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		this.prov = lp;

		updateMenuText();

		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				updateMenuText();
			}
		};

		prov.addLocalizationListener(listener);

	}

	/**
	 * Updates label based on localization.
	 */
	private void updateMenuText() {
		this.setText(this.prov.getString(key));
	}
}
