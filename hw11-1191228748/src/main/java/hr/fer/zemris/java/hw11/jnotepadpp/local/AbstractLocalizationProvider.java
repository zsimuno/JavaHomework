package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.HashSet;

/**
 * Abstract class localization provider that only implements the communication
 * with listeners.
 * 
 * @author Zvonimir Šimunović
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Listeners to this localization provider.
	 */
	HashSet<ILocalizationListener> listeners = new HashSet<>();

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners.
	 */
	void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}
