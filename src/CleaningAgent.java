/*
 * @AUTHOR: GELU POPA, gelu1.popa@gmail.com
 */

import java.util.Random;

public class CleaningAgent {
	private Environment environment;
	private int x, y, movements;
	private enum facing {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	}
	private enum direction {
		FORWARD,
		RIGHT,
		LEFT
	};
	private facing agent_facing = facing.NORTH;
	private Random rand = new Random();
	
	// creating the Agent, giving it a random position in the grid
	public CleaningAgent(Environment environment) {
		this.environment = environment;
		this.setInitialSpot();
	}
	
	public void setInitialSpot() {
		int x1,y1;
		//we set a random initial position for our agent, but if the position is an obstacle, we find another one, random as well.
		do{
			x1 = rand.nextInt(environment.getSize());
			y1 = rand.nextInt(environment.getSize());
		}while(environment.isSpotAObstacle(x1, y1));	
		x = x1;
		y = y1;
		if(environment.isSpotDirty(x, y)) {
			environment.suckDirt(x, y);
		}
		environment.setText(x, y, 'A');
	}
	// method that provides the current position of our agent
	public void getPosition() {
		System.out.println(x +", " + y);
	}
	
	public void setPosition(int newX, int newY) {
		environment.setText(x, y, ' ');
		this.x = newX;
		this.y = newY;
		environment.setText(x, y, 'A');
		
	}
	
	public void move() {
		while(environment.getNumberOfDirtySpots() > 0) {
			environment.printEnvironment();
			System.out.println(environment.getNumberOfDirtySpots());
			direction dir = decideWhereToMove();
			if(dir!=null) System.out.println(agent_facing.toString() + " " +dir.toString());
			if(dir == direction.FORWARD) {
				this.moveForward(agent_facing);
				movements++;
			}
			else {
				if(dir == direction.LEFT) {
					this.moveLeft(agent_facing);
					movements++;
				} else {
					if(dir == direction.RIGHT) {
						this.moveRight(agent_facing);
						movements++;
					} else {
						System.out.println("Dont know where to go! Movements : " + movements);
						return;
					}
				}
			}
		}
		System.out.println("Agent finished cleaning, shutting down! Movements : " + movements);
	}
	
	private direction decideWhereToMove() {
		if(environment.isSpotDirty(x-1, y)) {
			if(agent_facing==facing.NORTH) {
				return direction.FORWARD;
			}
			if(agent_facing==facing.SOUTH) {
				agent_facing=facing.NORTH;
				return direction.FORWARD;
			}
			if(agent_facing==facing.WEST) {
				return direction.RIGHT;
			}
			if(agent_facing==facing.EAST) {
				return direction.LEFT;
			}
		}
		if(environment.isSpotDirty(x, y+1)) {
			if(agent_facing==facing.NORTH) {
				return direction.RIGHT;
			}
			if(agent_facing==facing.SOUTH) {
				return direction.LEFT;
			}
			if(agent_facing==facing.WEST) {
				agent_facing=facing.EAST;
				return direction.FORWARD;
			}
			if(agent_facing==facing.EAST) {
				return direction.FORWARD;
			}
		}
		if(environment.isSpotDirty(x, y-1)) {
			if(agent_facing==facing.NORTH) {
				return direction.LEFT;
			}
			if(agent_facing==facing.SOUTH) {
				return direction.RIGHT;
			}
			if(agent_facing==facing.WEST) {
				return direction.FORWARD;
			}
			if(agent_facing==facing.EAST) {
				agent_facing=facing.WEST;
				return direction.FORWARD;
			}
		}
		if(environment.isSpotDirty(x+1, y)) {
			if(agent_facing==facing.NORTH) {
				agent_facing=facing.SOUTH;
				return direction.FORWARD;
			}
			if(agent_facing==facing.SOUTH) {
				return direction.FORWARD;
			}
			if(agent_facing==facing.WEST) {
				return direction.LEFT;
			}
			if(agent_facing==facing.EAST) {
				return direction.RIGHT;
			}
		}
		// No dirt nearby so we choose a random direction to go to.
		int r = rand.nextInt(3);
		if(environment.isSpotAObstacle(x-1, y) && environment.isSpotAObstacle(x, y-1)) {
			/*
			 * X X
			 * X A
			 */
			if(agent_facing == facing.NORTH) {
				if(rand.nextBoolean()) {
					return direction.RIGHT;
				} else {
					agent_facing = facing.SOUTH;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.EAST) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					return direction.RIGHT;
				}
			}
			if(agent_facing == facing.WEST) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					agent_facing = facing.EAST;
					return direction.FORWARD;
				}
			}
		}
		if(environment.isSpotAObstacle(x-1, y) && environment.isSpotAObstacle(x, y+1)) {
			/*
			 * X X
			 * A X
			 */
			if(agent_facing == facing.NORTH) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					agent_facing = facing.SOUTH;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(rand.nextBoolean()) {
					return direction.RIGHT;
				} else {
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.EAST) {
				if(rand.nextBoolean()) {
					return direction.RIGHT;
				} else {
					agent_facing = facing.WEST;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.WEST) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					return direction.FORWARD;
				}
			}
		}
		if(environment.isSpotAObstacle(x+1, y) && environment.isSpotAObstacle(x, y-1)) {
			/*
			 * X A
			 * X X
			 */
			if(agent_facing == facing.NORTH) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					return direction.RIGHT;
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					agent_facing = facing.NORTH;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.EAST) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					return direction.LEFT;
				}
			}
			if(agent_facing == facing.WEST) {
				if(rand.nextBoolean()) {
					return direction.RIGHT;
				} else {
					agent_facing = facing.EAST;
					return direction.FORWARD;
				}
			}
		}
		if(environment.isSpotAObstacle(x+1, y) && environment.isSpotAObstacle(x, y+1)) {
			/*
			 * A X
			 * X X
			 */
			if(agent_facing == facing.NORTH) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					return direction.LEFT;
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(rand.nextBoolean()) {
					return direction.RIGHT;
				} else {
					agent_facing = facing.NORTH;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.EAST) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					agent_facing = facing.WEST;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.WEST) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					return direction.RIGHT;
				}
			}
		}
		if(environment.isSpotAObstacle(x+1, y) && environment.isSpotAObstacle(x-1, y)) {
			/*
			 * X
			 * A
			 * X
			 */
			if(agent_facing == facing.NORTH) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					return direction.RIGHT;
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					return direction.RIGHT;
				}
			}
			if(agent_facing == facing.EAST) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					agent_facing = facing.WEST;
					return direction.FORWARD;
				}				
			}
			if(agent_facing == facing.WEST) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					agent_facing = facing.EAST;
					return direction.FORWARD;
				}
			}
		}
		if(environment.isSpotAObstacle(x+1, y) && environment.isSpotAObstacle(x-1, y)) {
			/*
			 * X A X
			 */
			if(agent_facing == facing.NORTH) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					agent_facing = facing.SOUTH;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(rand.nextBoolean()) {
					return direction.FORWARD;
				} else {
					agent_facing = facing.NORTH;
					return direction.FORWARD;
				}
			}
			if(agent_facing == facing.EAST) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					return direction.RIGHT;
				}				
			}
			if(agent_facing == facing.WEST) {
				if(rand.nextBoolean()) {
					return direction.LEFT;
				} else {
					return direction.RIGHT;
				}
			}
		}
		if(environment.isSpotAObstacle(x-1, y) && environment.isSpotAObstacle(x, y+1) && environment.isSpotAObstacle(x, y-1)) {
			/*
			 * X X X
			 * X A X
			 */
			if(agent_facing == facing.NORTH) {
				agent_facing = facing.SOUTH;
				return direction.FORWARD;
			}
			if(agent_facing == facing.SOUTH) {
				return direction.FORWARD;				
			}
			if(agent_facing == facing.EAST) {
				return direction.RIGHT;
			}
			if(agent_facing == facing.WEST) {
				return direction.LEFT;
			}
		}
		if(environment.isSpotAObstacle(x+1, y) && environment.isSpotAObstacle(x, y+1) && environment.isSpotAObstacle(x, y-1)) {
			/*
			 * X A X
			 * X X X
			 */
			if(agent_facing == facing.NORTH) {
				return direction.FORWARD;
			}
			if(agent_facing == facing.SOUTH) {
				agent_facing = facing.NORTH;
				return direction.FORWARD;				
			}
			if(agent_facing == facing.EAST) {
				return direction.LEFT;
			}
			if(agent_facing == facing.WEST) {
				return direction.RIGHT;
			}
		}
		if(environment.isSpotAObstacle(x-1, y) && environment.isSpotAObstacle(x+1, y) && environment.isSpotAObstacle(x, y+1)) {
			/*
			 * X X
			 * A X
			 * X X
			 */
			if(agent_facing == facing.NORTH) {
				return direction.LEFT;
			}
			if(agent_facing == facing.SOUTH) {
				return direction.RIGHT;	
			}
			if(agent_facing == facing.EAST) {
				agent_facing = facing.WEST;
				return direction.FORWARD;
			}
			if(agent_facing == facing.WEST) {
				return direction.FORWARD;
			}
		}
		if(environment.isSpotAObstacle(x-1, y) && environment.isSpotAObstacle(x+1, y) && environment.isSpotAObstacle(x, y-1)) {
			/*
			 * X X
			 * X A
			 * X X
			 */
			if(agent_facing == facing.NORTH) {
				return direction.RIGHT;
			}
			if(agent_facing == facing.SOUTH) {
				return direction.LEFT;
			}
			if(agent_facing == facing.EAST) {
				return direction.FORWARD;
			}
			if(agent_facing == facing.WEST) {
				agent_facing = facing.EAST;
				return direction.FORWARD;	
			}
		}
		if(environment.isSpotAObstacle(x-1, y)){
			/*
			 *  X X X
			 *    A 
			 */
			if(agent_facing == facing.NORTH) {
				if(r == 0) {
					agent_facing = facing.SOUTH;
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
			if(agent_facing == facing.EAST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							agent_facing = facing.WEST;
							return direction.FORWARD;	
						}
					}
				}
			}
			if(agent_facing == facing.WEST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.LEFT;
					} else {
						if(r == 2) {
							agent_facing = facing.EAST;
							return direction.FORWARD;
						}
					}
				}
			}
		}
		if(environment.isSpotAObstacle(x+1, y)){
			/*
			 *    A
			 *  X X X
			 */
			if(agent_facing == facing.NORTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.LEFT;
					} else {
						if(r == 2) {
							return direction.RIGHT;
						}
					}
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(r == 0) {
					agent_facing = facing.NORTH;
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.LEFT;
					} else {
						if(r == 2) {
							agent_facing = facing.EAST;
							return direction.FORWARD;
						}
					}
				}
			}
			if(agent_facing == facing.EAST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.LEFT;
					} else {
						if(r == 2) {
							agent_facing = facing.WEST;
							return direction.FORWARD;
						}
					}
				}
			}
			if(agent_facing == facing.WEST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							agent_facing = facing.EAST;
							return direction.FORWARD;
						}
					}
				}
			}
		}
		if(environment.isSpotAObstacle(x, y+1)){
			/*
			 *      X
			 *    A X
			 *      X
			 */
			if(agent_facing == facing.NORTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.LEFT;
					} else {
						if(r == 2) {
							agent_facing = facing.SOUTH;
							return direction.FORWARD;
						}
					}
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							agent_facing = facing.NORTH;
							return direction.FORWARD;
						}
					}
				}
			}
			if(agent_facing == facing.EAST) {
				if(r == 0) {
					agent_facing = facing.WEST;
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
			if(agent_facing == facing.WEST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
		}
		if(environment.isSpotAObstacle(x, y-1)){
			/*
			 *  X
			 *  X A
			 *  X
			 */
			if(agent_facing == facing.NORTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							agent_facing = facing.SOUTH;
							return direction.FORWARD;
						}
					}
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.LEFT;
					} else {
						if(r == 2) {
							agent_facing = facing.NORTH;
							return direction.FORWARD;
						}
					}
				}
			}
			if(agent_facing == facing.EAST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
				
			}
			if(agent_facing == facing.WEST) {
				if(r == 0) {
					agent_facing = facing.EAST;
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
		}
		// if there's nothing around, neither a dirt spot or an obstacle, we choose a random direction to go
		if(!environment.isSpotAObstacle(x-1, y) && !environment.isSpotAObstacle(x+1, y) && !environment.isSpotAObstacle(x, y+1) && !environment.isSpotAObstacle(x, y-1)) {
			if(agent_facing == facing.NORTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
			if(agent_facing == facing.SOUTH) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
			if(agent_facing == facing.EAST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
			if(agent_facing == facing.WEST) {
				if(r == 0) {
					return direction.FORWARD;
				} else {
					if(r == 1) {
						return direction.RIGHT;
					} else {
						if(r == 2) {
							return direction.LEFT;
						}
					}
				}
			}
		}
		return null;
	}
	
	private boolean moveForward(facing val) {
		if(val==facing.NORTH) {
			if(!environment.isSpotAObstacle(x-1, y)) {
				this.setPosition(x-1, y);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.EAST) {
			if(!environment.isSpotAObstacle(x, y+1)) {
				this.setPosition(x, y+1);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.WEST) {
			if(!environment.isSpotAObstacle(x, y-1)) {
				this.setPosition(x, y-1);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.SOUTH) {
			if(!environment.isSpotAObstacle(x+1, y))
				this.setPosition(x+1, y);
			if(environment.isSpotDirty(x, y)) {
				environment.suckDirt(x,y);
			}
				return true;
		}
		return false;
	}
	private boolean moveLeft(facing val) {
		if(val==facing.NORTH) {
			if(!environment.isSpotAObstacle(x, y-1)) {
				this.setPosition(x, y-1);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.EAST) {
			if(!environment.isSpotAObstacle(x-1, y)) {
				this.setPosition(x-1, y);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.WEST) {
			if(!environment.isSpotAObstacle(x+1, y)) {
				this.setPosition(x+1, y);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.SOUTH) {
			if(!environment.isSpotAObstacle(x, y+1))	
				this.setPosition(x, y+1);
			if(environment.isSpotDirty(x, y)) {
				environment.suckDirt(x,y);
			}
				return true;
		}
		return false;
	}
	private boolean moveRight(facing val) {
		if(val==facing.NORTH) {
			if(!environment.isSpotAObstacle(x, y+1)) {
				this.setPosition(x, y+1);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.EAST) {
			if(!environment.isSpotAObstacle(x+1, y)) {
				this.setPosition(x+1, y);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.WEST) {
			if(!environment.isSpotAObstacle(x-1, y)) {
				this.setPosition(x-1, y);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		if(val==facing.SOUTH) {
			if(!environment.isSpotAObstacle(x, y-1)) {
				this.setPosition(x, y-1);
				if(environment.isSpotDirty(x, y)) {
					environment.suckDirt(x,y);
				}
				return true;
			}
		}
		return false;
	}
	
}
