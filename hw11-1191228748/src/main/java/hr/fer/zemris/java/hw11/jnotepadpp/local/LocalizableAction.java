package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Action (abstract) that uses localization for all string inputs of this
 * action.
 * 
 * @author Zvonimir Šimunović
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Key value for using localization and getting strings.
	 */
	private String key;

	/**
	 * Listener to localization changing.
	 */
	private ILocalizationListener listener;

	/**
	 * Provider for localization.
	 */
	private ILocalizationProvider prov;

	/**
	 * Constructs a new action that changes depending on localization change.
	 * 
	 * @param key key value which we use to retrieve translation.
	 * @param lp  location provider that provides the translations.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.prov = lp;
		updateValues();

		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				updateValues();
			}
		};

		prov.addLocalizationListener(listener);
	}

	/**
	 * Updates values based on localization.
	 */
	private void updateValues() {
		putValue(Action.NAME, prov.getString(key));
		putValue(Action.SHORT_DESCRIPTION, prov.getString(key + "desc"));
	}

}
