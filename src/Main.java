/*
 * @AUTHOR: GELU POPA, gelu1.popa@gmail.com
 */

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Environment e = new Environment(7);
		
		//System.out.println(e.isSpotAObstacle(3, 1));
		CleaningAgent agent = new CleaningAgent(e);
		System.out.println(e.getNumberOfDirtySpots());
		agent.move();
		System.out.println(e.getNumberOfDirtySpots());
		e.printEnvironment();
	}

}
