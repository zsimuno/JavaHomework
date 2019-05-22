package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides localization using a single instance of this class.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Single instance of this localization provider.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Current language.
	 */
	private String language;

	/**
	 * Resource bundle with which we get the translation.
	 */
	private ResourceBundle bundle;

	/**
	 * Constructs a new provider with the default "en" language.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}

	/**
	 * Returns the single instance of this localization provider.
	 * 
	 * @return instance of this localization provider.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Set the language of the provider to the given one.
	 * 
	 * @param lan language to be used now.
	 */
	public void setLanguage(String lan) {
		this.language = lan;
		Locale locale = Locale.forLanguageTag(this.language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translation", locale);
		this.fire();
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
