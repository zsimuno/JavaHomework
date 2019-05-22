package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

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
	 * Creates a menu bar.
	 * 
	 * @return newly created menu bar.
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
		file.add(new JMenuItem(actions.closeDocument));
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

		JMenu tools = new JMenu("Tools");
		mb.add(tools);
		JMenu changeCase = new JMenu("Change case");
		tools.add(changeCase);
		changeCase.add(new JMenuItem(actions.toUppercase));
		changeCase.add(new JMenuItem(actions.toLowercase));
		changeCase.add(new JMenuItem(actions.invertCase));
		JMenu sort = new JMenu("Sort");
		tools.add(sort);
		sort.add(new JMenuItem(actions.ascendingSort));
		sort.add(new JMenuItem(actions.descendingSort));
		tools.add(new JMenuItem(actions.removeDuplicates));

		return mb;

	}

	/**
	 * Creates a toolbar.
	 * 
	 * @return newly created toolbar.
	 */
	public JToolBar createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(actions.newDocument));
		tb.add(new JButton(actions.openDocument));
		tb.addSeparator();
		tb.add(new JButton(actions.saveDocument));
		tb.add(new JButton(actions.saveAsDocument));
		tb.add(new JButton(actions.closeDocument));
		tb.addSeparator();
		tb.add(new JButton(actions.cutSelectedPart));
		tb.add(new JButton(actions.copySelectedPart));
		tb.add(new JButton(actions.pasteFromClipboard));
		tb.addSeparator();
		tb.add(new JButton(actions.statisticalInfo));
		tb.add(new JButton(actions.exitApplication));

		return tb;
	}

	/**
	 * Creates a status bar.
	 * 
	 * @return newly created status bar.
	 */
	public JPanel createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 3));
		statusBar.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.gray));
		JLabel length = new JLabel("  length: 0");
		JLabel other = new JLabel("  Ln: 0    Col:0    Sel:0");
		other.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.lightGray));
		JLabel time = new JLabel("Now ");// TODO time on status
		time.setHorizontalAlignment(JLabel.RIGHT);
		statusBar.add(length);
		statusBar.add(other);
		statusBar.add(time);

		CaretListener listener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea textArea = (JTextArea) e.getSource();

				int len = textArea.getText().length();
				int sel = Math.abs(e.getDot() - e.getMark());

				int position = textArea.getCaretPosition();
				int ln = 0;
				int col = 0;
				try {
					ln = textArea.getLineOfOffset(position);
					col = position - textArea.getLineStartOffset(ln);
				} catch (BadLocationException ignorable) {
				}

				col++;
				ln++;

				length.setText("  length:" + len);
				other.setText("  Ln:" + ln + "    Col:" + col + "   Sel:" + sel);

			}
		};
		documentModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				model.getTextComponent().removeCaretListener(listener);
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				model.getTextComponent().addCaretListener(listener);

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				previousModel.getTextComponent().removeCaretListener(listener);
				currentModel.getTextComponent().addCaretListener(listener);

			}
		});
		return statusBar;
	}

}
