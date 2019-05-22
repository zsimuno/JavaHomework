package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class represents a bridge between a localization provider and frames.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Is this provider connected to the parent.
	 */
	private boolean connected;

	/**
	 * Listener to the parent.
	 */
	private ILocalizationListener listener;

	/**
	 * Parent of this provider.
	 */
	private ILocalizationProvider parent;

	/** Current language in the bridge. */
	private String currentLanguage;

	/**
	 * Constructor.
	 * 
	 * @param parent parent of this provider.
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		super();
		this.parent = parent;
		currentLanguage = parent.getCurrentLanguage();
		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	/**
	 * Connect to parent.
	 */
	public void connect() {
		if (connected)
			return;

		connected = true;
		parent.addLocalizationListener(listener);
		if (currentLanguage != parent.getCurrentLanguage()) {
			currentLanguage = parent.getCurrentLanguage();
			fire();
		}
	}

	/**
	 * Disconnect from parent.
	 */
	public void disconnect() {
		connected = false;
		parent.removeLocalizationListener(listener);
		currentLanguage = parent.getCurrentLanguage();
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return currentLanguage;
	}

}
