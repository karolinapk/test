package pl.hetman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Logic {

	private ArrayList<ArrayList<Integer>> board;
	private Map<Integer, List<Integer>> hetmansSpots;
	private Map<Integer , Integer> lastHetmansSpot;
	private boolean isBeginning = true;
	private int boardSize = 5;
	private int bannedSpotRow = -1;
	private List<Integer> listOfBannedColumns;

	public void logic(int boardSize) {
		this.boardSize = boardSize;
		hetmansSpots = new HashMap<Integer, List<Integer>>();
		lastHetmansSpot = new HashMap<Integer, Integer>();
		for(int i = 0 ; i < boardSize ; i++) {
			hetmansSpots.put(i, new ArrayList<Integer>());
		}
		listOfBannedColumns = new ArrayList<Integer>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < boardSize; i++) {
			if (isBeginning) {
				isBeginning = false;
				Random rand = new Random();
				int hetmanSpot = rand.nextInt(boardSize);
				System.out.println(hetmanSpot);
				board = new ArrayList<ArrayList<Integer>>();
				fillStartBoard(hetmanSpot);
			} else {
				ArrayList<Integer> list = new ArrayList<Integer>();
				map.clear();
				for (int j = 0; j < boardSize; j++) {
					if (board.get(i).get(j) == 0) {
						int tmp = heuristicFunction(i, j);
						if (tmp != -1) {
							list.add(heuristicFunction(i, j));
							map.put(list.get(list.size() - 1), j);
						}
					}
				}

				Collections.sort(list);
				int bestHetmanSpot = -1;
				if (map.size() > 0) {
					bestHetmanSpot = map.get(list.get(0));
				}

				if (bestHetmanSpot != -1) {
					setHetmanOnBoard(i, bestHetmanSpot);
				} else {
					back(i, hetmansSpots.get(i - 1));
					i = i  - 2;
				}
			}
		}
	}

	private void fillStartBoard(int hetmanSpot) {
		for (int i = 0; i < boardSize; i++) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < boardSize; j++) {
				if ((i == 0)
						|| (j == hetmanSpot)
						|| (j == (Math.abs((hetmanSpot - i))) && hetmanSpot >= i)
						|| (j == (Math.abs((hetmanSpot + i))) && hetmanSpot <= boardSize)) {
					row.add(1);
				} else {
					row.add(0);
				}

				if ((i == 0 && j == hetmanSpot)) {
					hetmansSpots.get(i).add(j);
					lastHetmansSpot.put(i, j);
				}
			}
			board.add(row);
		}
	}

	private int heuristicFunction(int row, int hetmanSpot) {
		int availableSpot = 0;
		if (bannedSpotRow == row && listOfBannedColumns.contains(hetmanSpot)) {
		} else {
			int k = 0;
			for (int i = row; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {

					if (((i == row) || (j == hetmanSpot)
							|| (j == (hetmanSpot - k)) || (j == (hetmanSpot + k)))
							&& board.get(i).get(j) == 0) {
						availableSpot++;
					}
				}
				k++;
			}

			return availableSpot;
		}
		return -1;
	}

	private void setHetmanOnBoard(int row, int column) {
		int k = 0;
		for (int i = row; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (((i == row) || (j == column) || (j == (column - k)) || (j == (column + k)))
						&& board.get(i).get(j) == 0) {
					board.get(i).set(j, row + 1);
				}

				if ((i == row && j == column) && board.get(i).get(j) == row + 1) {
					if((i+1)<hetmansSpots.size()) {
						hetmansSpots.get(i+1).clear();
					}
					hetmansSpots.get(i).add(j);
					lastHetmansSpot.put(i, j);
				}
			}
			k++;
		}
	}

	private void back(int row, List<Integer> columns) {
		bannedSpotRow = row - 1;
		listOfBannedColumns = columns;
		for (int i = boardSize - 1; i >= row - 1; i--) {
			for (int j = 0; j < boardSize; j++) {
				if (board.get(i).get(j) > row - 1) {
					board.get(i).set(j, 0);
				}
			}
		}
	}

	public Map<Integer, Integer> getHetmansSpots() {
		return lastHetmansSpot;
	}
}
