package pl.hetman;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewPresenter implements ActionListener {
	
	private Map<Integer , Integer> hetmansSpots;
	private JFrame outer;
	private ChessBoard chessboard;
	private JPanel config;
	private JTextField boardSize;
	private JButton recalculateBtn;
	private JLabel label;
	
	public void setHetmansSpots(Map<Integer, Integer> map) {
		this.hetmansSpots = map;
	}
	
	public void showView() {
		outer = new JFrame();
		outer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		outer.setLayout(new BorderLayout());
		outer.setSize(620, 700);
		
		label = new JLabel("Numbers of hetmans");
		
		boardSize = new JTextField("6");
		boardSize.setPreferredSize(new Dimension(100, 28));
		
		chessboard = new ChessBoard(100, 6, hetmansSpots);
		chessboard.setSize(600, 600);
		
		recalculateBtn = new JButton("Calculate");
		recalculateBtn.addActionListener(this);
		
		config = new JPanel();
		config.add(label);
		config.add(boardSize);
		config.add(recalculateBtn);
		outer.add(chessboard, BorderLayout.CENTER);
		outer.add(config, BorderLayout.PAGE_END);
		outer.setResizable(true);
		outer.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int nrOfSquares = Integer.parseInt(boardSize.getText());
		int squareSize = 600/nrOfSquares; 
		Logic logic = new Logic();
		logic.logic(nrOfSquares);
		outer.remove(chessboard);
		chessboard.setConfig(squareSize, nrOfSquares, logic.getHetmansSpots());
		chessboard.removeAll();
		chessboard.repaint();
		outer.add(chessboard);
		outer.repaint();
	}
}
