package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Represents providers for localization that return the localized string for
 * the given key.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface ILocalizationProvider {
	/**
	 * Return the localized string for the given key.
	 * 
	 * @param key key word that we use to find the translation.
	 * @return localized string.
	 */
	String getString(String key);

	/**
	 * Adds the given listener to this provider.
	 * 
	 * @param l listener to this provider.
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes the given listener to this provider.
	 * 
	 * @param l listener to this provider.
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Returns the current language in string form.
	 * 
	 * @return current language.
	 */
	String getCurrentLanguage();
}
