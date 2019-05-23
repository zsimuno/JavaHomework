package hr.fer.zemris.java.hw11.jnotepadpp.elements;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Caret;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
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

	/** Actions that are used in bars constructed in this class. */
	private NotepadActions actions;

	/** Model that contains multiple documents open in this application. */
	private MultipleDocumentModel documentModel;

	/** Localization provider for this application. */
	private FormLocalizationProvider flp;

	/**
	 * Constructor.
	 * 
	 * @param notepad       Application in which we set the bars.
	 * @param documentModel Document model in which the files are stored.
	 */
	public NotepadBars(JNotepadPP notepad, MultipleDocumentModel documentModel, FormLocalizationProvider flp) {
		this.documentModel = documentModel;
		this.flp = flp;
		actions = new NotepadActions(notepad, documentModel, flp);

	}

	/**
	 * Creates a menu bar.
	 * 
	 * @return newly created menu bar.
	 */
	public JMenuBar createMenus() {
		JMenuBar mb = new JMenuBar();

		LJMenu file = new LJMenu("file", flp);
		mb.add(file);
		file.add(new JMenuItem(actions.newDocument));
		file.add(new JMenuItem(actions.openDocument));
		file.addSeparator();
		file.add(new JMenuItem(actions.closeDocument));
		file.addSeparator();
		file.add(new JMenuItem(actions.saveDocument));
		file.add(new JMenuItem(actions.saveAsDocument));
		file.addSeparator();
		file.add(new JMenuItem(actions.exitApplication));

		LJMenu edit = new LJMenu("edit", flp);
		mb.add(edit);
		edit.add(new JMenuItem(actions.cutSelectedPart));
		edit.add(new JMenuItem(actions.copySelectedPart));
		edit.add(new JMenuItem(actions.pasteFromClipboard));

		LJMenu info = new LJMenu("info", flp);
		mb.add(info);
		info.add(new JMenuItem(actions.statisticalInfo));

		LJMenu tools = new LJMenu("tools", flp);
		mb.add(tools);
		LJMenu changeCase = new LJMenu("changecase", flp);
		tools.add(changeCase);
		changeCase.add(new JMenuItem(actions.toUppercase));
		changeCase.add(new JMenuItem(actions.toLowercase));
		changeCase.add(new JMenuItem(actions.invertCase));
		LJMenu sort = new LJMenu("sort", flp);
		tools.add(sort);
		sort.add(new JMenuItem(actions.ascendingSort));
		sort.add(new JMenuItem(actions.descendingSort));
		tools.add(new JMenuItem(actions.unique));

		LJMenu lan = new LJMenu("language", flp);
		mb.add(lan);
		lan.add(new JMenuItem(actions.hr));
		lan.add(new JMenuItem(actions.en));
		lan.add(new JMenuItem(actions.de));

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
		tb.add(new JButton(actions.closeDocument));
		tb.addSeparator();
		tb.add(new JButton(actions.saveDocument));
		tb.add(new JButton(actions.saveAsDocument));
		tb.addSeparator();
		tb.add(new JButton(actions.cutSelectedPart));
		tb.add(new JButton(actions.copySelectedPart));
		tb.add(new JButton(actions.pasteFromClipboard));
		tb.addSeparator();
		tb.add(new JButton(actions.statisticalInfo));
		tb.add(new JButton(actions.exitApplication));

		return tb;
	}

	/** Label that shows the length of the current file. */
	private JLabel length;
	/** Label that shows various values connected to the carot. */
	private JLabel other;

	/**
	 * Creates a status bar.
	 * 
	 * @return newly created status bar.
	 */
	public JPanel createStatusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 3));
		statusBar.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.gray));

		length = new JLabel(String.format(flp.getString("statuslength"), 0));
		length.setBorder(new EmptyBorder(0, 20, 0, 0));

		other = new JLabel(String.format(flp.getString("statusother"), 1, 1, 0));
		other.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.lightGray));

		JLabel time = new JLabel(DateFormat.getDateTimeInstance().format(new Date()));
		time.setBorder(new EmptyBorder(0, 0, 0, 20));
		time.setHorizontalAlignment(JLabel.RIGHT);

		final DateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				time.setText(timeFormat.format(new Date()));

			}
		});

		timer.setInitialDelay(0);
		timer.start();

		statusBar.add(length);
		statusBar.add(other);
		statusBar.add(time);

		// Change depending on language
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				// If no documents
				if (documentModel.getNumberOfDocuments() == 0) {
					length.setText(String.format(flp.getString("statuslength"), 0));
					other.setText(String.format(flp.getString("statusother"), 1, 1, 0));
					return;
				}

				setStatusBar(documentModel.getCurrentDocument().getTextComponent());
			}
		});

		CaretListener listener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				JTextComponent textComp = (JTextComponent) e.getSource();
				setStatusBar(textComp);
			}
		};

		// Change depending on current document 
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
				setStatusBar(currentModel.getTextComponent());
			}
		});
		return statusBar;
	}

	/**
	 * Sets the first two thirds of the status bar.
	 * 
	 * @param textComp text component on which we base the numbers in the bar.
	 */
	private void setStatusBar(JTextComponent textComp) {
		Caret caret = textComp.getCaret();
		Element root = textComp.getDocument().getDefaultRootElement();

		int len = textComp.getText().length();
		int sel = Math.abs(caret.getDot() - caret.getMark());

		int position = textComp.getCaretPosition();
		int ln = root.getElementIndex(position);
		int col = position - root.getElement(ln).getStartOffset();

		col++;
		ln++;

		length.setText(String.format(flp.getString("statuslength"), len));
		other.setText(String.format(flp.getString("statusother"), ln, col, sel));

	}

}
