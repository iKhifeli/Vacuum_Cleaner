/*
 * @AUTHOR: GELU POPA, gelu1.popa@gmail.com
 */

public class Environment {

	private Object[][] grid;
	private int size;
	private int dirtySpots;
	
	public Environment(int size) {
		this.size = size;
		grid = new Object[size][size];
		this.delimit();
		do {
			this.dirtySpots=this.getNumberOfDirtySpots();
		}while(dirtySpots == 0);
	}
	
	public void delimit() {
		for(int i = 0; i < size; i++) {
			grid[i][0]='X';
			grid[i][size-1]='X';
			for(int j = 0; j < size; j++) {
				grid[0][j]='X';
				grid[size-1][j]='X';
				if(grid[i][j]==null) {
					if(Math.random() < 0.25) {
						grid[i][j]='*'; // giving every spot a chance of 12,5% of being dirty
					}
					else {
						if(Math.random() < 0.075) {
							grid[i][j]='X'; // adding aditional obstacles
						}
						else grid[i][j]=' '; // clean spot
					}
				}
			}
		}
	}
	
	public int getNumberOfDirtySpots() {
		int counter = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(grid[i][j].equals('*')) {
					counter ++;
				}
			}
		}
		return counter;
	}
	
	private void reduceNumberOfDirtySpots() {
		if(dirtySpots>0) {
			dirtySpots--;
		}
	}
	
	public void printEnvironment() {
		for(int i = 0; i<size; i++) {
			for(int j = 0; j<size; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
	public boolean isSpotDirty(int x, int y) {
		return grid[x][y].equals('*');
	}
	
	public boolean isSpotAObstacle(int x, int y) {
		return grid[x][y].equals('X');
	}
	
	public int getSize() {
		return size;
	}
	
	public void suckDirt(int x, int y) {
		if(grid[x][y].equals('*')) {
			grid[x][y]=' ';
		}
		this.reduceNumberOfDirtySpots();
	}
	public void setText(int x, int y,char c) {
		grid[x][y]=c;
	}
}
