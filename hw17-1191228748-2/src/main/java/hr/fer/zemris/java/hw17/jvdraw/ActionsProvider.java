package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.Action;
import javax.swing.JFrame;

/**
 * Provides actions needed in the {@link JVDraw} application.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ActionsProvider {

	/** Parent of all these actions that must be set before use. */
	public JFrame drawApp;

	/**
	 * Constructor that sets the parent for all these actions.
	 * 
	 * @param app parent of the all the buttons.
	 */
	public ActionsProvider(JVDraw app) {
		this.drawApp = app;
		initMenuActions();
	}

	/**
	 * Initializes actions that are in the menu.
	 */
	private void initMenuActions() {
		// TODO Auto-generated method stub

	}

	/** Opens a new file from the path that user chooses. */
	public Action open;

	/** Saves the currently open file. */
	public Action save;

	/** Saves file with the given file name. */
	public Action saveAs;

	/** Export current file. */
	public Action export;

	/** Exits the application. */
	public Action exitApplication;

}
