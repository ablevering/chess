package boardGUIManager;

import java.awt.BorderLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tile extends JPanel {
	private boolean edited = false;
	
	public Tile(BorderLayout borderLayout) {
		super(borderLayout);
	}

	public boolean isEdited() {
		return edited;
	}
	
	public void edit() {
		edited = true;
	}
	
	public void clearEdit() {
		edited = false;
	}
}
