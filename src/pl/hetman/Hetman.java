package pl.hetman;

public class Hetman {
	
	public static void main(String[] args) {
		Logic logic = new Logic();
		ViewPresenter presenter = new ViewPresenter();
		logic.logic(6);
		presenter.setHetmansSpots(logic.getHetmansSpots());
		presenter.showView();
	}

}
