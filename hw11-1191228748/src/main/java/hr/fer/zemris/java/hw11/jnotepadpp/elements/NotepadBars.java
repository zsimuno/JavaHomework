package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;

/**
 * Sets the bars of the notepad application.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class NotepadBars {

	/** Application in which we set the bars. */
	private JNotepadPP notepad;

	/** Actions that are used in bars constructed in this class. */
	private NotepadActions actions;

	/** Model that contains multiple documents open in this application. */
	private MultipleDocumentModel documentModel;

	/**
	 * Constructor.
	 * 
	 * @param notepad Application in which we set the bars.
	 */
	public NotepadBars(JNotepadPP notepad, MultipleDocumentModel documentModel) {
		this.notepad = notepad;
		this.documentModel = documentModel;
		actions = new NotepadActions(notepad, documentModel);
	}
	

	/**
	 * 
	 */
	public JMenuBar createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(actions.newDocument));
		file.add(new JMenuItem(actions.openDocument));
		file.addSeparator();
		file.add(new JMenuItem(actions.saveDocument));
		file.add(new JMenuItem(actions.saveAsDocument));
		file.addSeparator();
		file.add(new JMenuItem(actions.exitApplication));

		JMenu edit = new JMenu("Edit");
		mb.add(edit);
		edit.add(new JMenuItem(actions.cutSelectedPart));
		edit.add(new JMenuItem(actions.copySelectedPart));
		edit.add(new JMenuItem(actions.pasteFromClipboard));

		JMenu info = new JMenu("Info");
		mb.add(info);
		info.add(new JMenuItem(actions.statisticalInfo));
		
		return mb;

		
	}

	/**
	 * 
	 */
	public JToolBar createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(actions.newDocument));
		tb.add(new JButton(actions.openDocument));
		tb.add(new JButton(actions.saveDocument));
		tb.add(new JButton(actions.saveAsDocument));
		tb.add(new JButton(actions.exitApplication));
		tb.add(new JButton(actions.cutSelectedPart));
		tb.add(new JButton(actions.copySelectedPart));
		tb.add(new JButton(actions.pasteFromClipboard));

		return tb;
	}

}
