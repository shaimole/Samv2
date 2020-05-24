package core;

public class execute {

	public static void main(String[] args) {
		try {
			Utils.setErrorCallBacks();
			GameEngine gameEng = new GameEngine("Sam",new DummyGame());
			gameEng.start();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}

}
