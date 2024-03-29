package gui;

import game.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import checker.moveSystem.Move;
import components.pieces.Piece;

public class ChessFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 773148790671349216L;
	private Action newGame, saveGame, loadGame, abandonGame, undo,about;
	private VictimPanel vp;
	private GraphicalBoard guiBoard;
	
	private Game game; JLabel tp;
	
	public ChessFrame() throws HeadlessException {}

	public ChessFrame(GraphicalBoard guiBoard) {
		this.guiBoard = guiBoard;
		setTitle("Chess Game");
		setSize(600, 600);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vp = new VictimPanel();
		
		add(vp, BorderLayout.EAST);
		guiBoard.setSize(guiBoard.getPreferredSize());
		guiBoard.paintComponent(getGraphics());
		this.game = guiBoard.getBoard().getGame();
		JPanel constraint = guiBoard.getConstraintPanel();
		JPanel tools = new JPanel();
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.PAGE_START);
		
		newGame 		= new AbstractAction("New"){
			private static final long serialVersionUID = 1L;
				
			@Override
			public void actionPerformed(ActionEvent e) {
				game.end();
				game = new Game();
				game.run();
			}
		};
		
		saveGame 		= new AbstractAction("Save"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				game.save();
			}
			
		};
		
		loadGame 		= new AbstractAction("Load"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				game.load();
			}
			
		};
		
		abandonGame 		= new AbstractAction("Resign"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = JOptionPane.showConfirmDialog(null, "Do you want to resign?", "Resign?", JOptionPane.YES_NO_OPTION);
				
				switch (x) {
				case JOptionPane.YES_OPTION:
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
					break;
				default:
					break;
				}
			}
			
		};
		
		undo 		= new AbstractAction("Undo"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Move m = game.popUndo();
				m.reverse();
				if(m.getVictim() != null){
					vp.removeLastPiece();
				}
				game.switchTurn();
				updateStatus();
				ChessFrame.this.game.requestVPRedraw();
				ChessFrame.this.guiBoard.redraw();
				game.updateGameState();
				if(game.isUndoEmpty())
					undo.setEnabled(false);
			}
			
		};
		
		about 		= new AbstractAction("About"){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "2 Player GUI Chess Game (JAVA Project)\nCreated by:"
						+ "\n- Manish Singh Bisht"
						+ "\n- Sahil Trivedi"
						+ "\n- Love Varshney"
						+ "\n- Ankur Harna", "About us", JOptionPane.INFORMATION_MESSAGE);
			}
			
		};
		
				
		newGame 	    .setEnabled(true);
		saveGame 	    .setEnabled(true);
		loadGame 	    .setEnabled(true);
		abandonGame     .setEnabled(true);
		undo     		.setEnabled(false);
		about     		.setEnabled(true);
		
		
		JButton newButton 	  = new JButton(newGame);
		JButton saveButton 	  = new JButton(saveGame);
		JButton loadButton 	  = new JButton(loadGame);
		JButton abandonButton = new JButton(abandonGame);
		JButton undoButton    = new JButton(undo);
		JButton aboutButton    = new JButton(about);
		
		toolbar.add(newButton);
		toolbar.add(saveButton);
		toolbar.add(loadButton);
		toolbar.addSeparator();
		toolbar.add(abandonButton);
		toolbar.addSeparator();
		toolbar.add(undoButton);
		toolbar.addSeparator();
		toolbar.add(aboutButton);
		tools.add(toolbar);
		add(tools, BorderLayout.NORTH);
		add(constraint, BorderLayout.CENTER);
		
		setMinimumSize(new Dimension(600, 600));
		
		JPanel statusBar = new JPanel();
		tp = new JLabel("2P Chess");
		statusBar.setSize(new Dimension(getWidth(), 230));
		statusBar.add(tp);
		
		add(statusBar, BorderLayout.SOUTH);
		
		this.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("icons" + File.separator + "wking.png"));
		
		pack();
		
	}
	
	public void updateStatus(){
		String status = game.getTurnColor() + "'s Turn";
		if(game.getGameState() != "None")
			status += "   ---   " + game.getGameState();
		tp.setText(status);
	}
	
	public void setUndoEnable(boolean enable){
		undo.setEnabled(enable);
	}
	public void setRedoEnable(boolean enable){
		//redo.setEnabled(enable);
	}
	public boolean getUndoState(){
		return undo.isEnabled();
	}
	public boolean getRedoState(){
		return false;
		//return redo.isEnabled();
	}
	
	public void requestAddVictim(Piece p){
		vp.addVictim(p);
	}

	public VictimPanel getVP() {
		return vp;
	}
	public void setVP(VictimPanel vp){
		this.vp = vp;
		add(vp);
	}
	

}
