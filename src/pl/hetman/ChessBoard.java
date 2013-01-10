package pl.hetman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

public class ChessBoard extends JPanel {
	private static final long serialVersionUID = 1326851020168238996L;

	private int coordinateX = 0;
	private int coordinateY = 0;
	private int squareSize = 0;
	private int nrOfSquares = 0;
	private Map<Integer, Integer> ovalPositions;

	public ChessBoard(int squareSize, int nrOfSquares, Map<Integer, Integer> ovalPositions) {
		this.squareSize = squareSize;
		this.nrOfSquares = nrOfSquares;
		this.ovalPositions = ovalPositions;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		coordinateX = 0;
		coordinateY = 0;
		boolean firstColor = true;
		for (int i = 0; i < nrOfSquares; i++) {

			coordinateX = 0;
			for (int j = 0; j < nrOfSquares; j++) {
				firstColor = changeColor(g, firstColor);
				g.fill3DRect(coordinateX, coordinateY, squareSize, squareSize,
						true);
				
				
				if(ovalPositions.get(i)==j) {
					g.setColor(Color.BLACK);
					g.fillOval(coordinateX, coordinateY, squareSize, squareSize);
					
				}
				coordinateX += squareSize;
			}
			if (nrOfSquares % 2 == 0) {
				firstColor = changeColor(g, firstColor);
			}
			coordinateY = coordinateY + squareSize;
		}
	}

	private boolean changeColor(Graphics g, boolean firstColor) {
		if (firstColor) {
			g.setColor(Color.GRAY);

		} else {
			g.setColor(Color.LIGHT_GRAY);
		}

		return !firstColor;
	}
	
	public void setConfig(int squareSize, int nrOfSquares, Map<Integer, Integer> ovalPositions) {
		this.nrOfSquares = nrOfSquares;
		this.squareSize = squareSize;
		this.ovalPositions = ovalPositions;
	}
}
