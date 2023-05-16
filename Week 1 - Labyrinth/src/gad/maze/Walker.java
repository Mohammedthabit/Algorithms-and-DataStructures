package gad.maze;

public class Walker {

	Result result;
	boolean[][] maze;

	public Walker(boolean[][] maze, Result result) {
		this.maze = maze;
		this.result = result;
	}

	public enum Direction {
		RIGHT, LEFT, UP, DOWN
	}

	public boolean walk() {

		int currentColumn = 1;
		int currentRow = 0;
		Direction direction = null;

		result.addLocation(currentColumn, currentRow);
		direction = Direction.DOWN;

		while(true){

			if (direction == Direction.RIGHT) {
				if (!maze[currentColumn][currentRow + 1]) {
					currentRow++;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.DOWN;
				} else if (!maze[currentColumn + 1][currentRow]) {
					currentColumn++;
					direction = Direction.RIGHT;
					result.addLocation(currentColumn, currentRow);
				} else if (!maze[currentColumn][currentRow - 1]) {
					currentRow--;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.UP;
				} else if (!maze[currentColumn - 1][currentRow]) {
					currentColumn--;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.LEFT;
				}
			}

			if (direction == Direction.UP) {
				if (!maze[currentColumn + 1][currentRow]) {
					currentColumn++;
					direction = Direction.RIGHT;
					result.addLocation(currentColumn, currentRow);
				} else if (!maze[currentColumn][currentRow - 1]) {
					currentRow--;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.UP;
				} else if (!maze[currentColumn - 1][currentRow]) {
					currentColumn--;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.LEFT;
				} else if (!maze[currentColumn][currentRow + 1]) {
					currentRow++;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.DOWN;
				}
			}

			if (direction == Direction.LEFT) {
				if (!maze[currentColumn][currentRow - 1]) {
					currentRow--;
					direction = Direction.UP;
					result.addLocation(currentColumn, currentRow);
				} else if (!maze[currentColumn - 1][currentRow]) {
					currentColumn--;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.LEFT;
				} else if (!maze[currentColumn][currentRow + 1]) {
					currentRow++;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.DOWN;
				} else if (!maze[currentColumn + 1][currentRow]) {
					currentColumn++;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.RIGHT;
				}
			}

			if (direction == Direction.DOWN) {
				if (!maze[currentColumn - 1][currentRow]) {
					currentColumn--;
					direction = Direction.LEFT;
					result.addLocation(currentColumn, currentRow);
				} else if (!maze[currentColumn][currentRow + 1]) {
					currentRow++;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.DOWN;
				} else if (!maze[currentColumn + 1][currentRow]) {
					currentColumn++;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.RIGHT;
				} else if (!maze[currentColumn][currentRow - 1]) {
					currentRow--;
					result.addLocation(currentColumn, currentRow);
					direction = Direction.UP;
				}
			}

			if (currentColumn == 0 || currentColumn == maze.length - 1 || currentRow == maze[0].length){
				return true;
			} else if (currentColumn == 1 && currentRow == 0) {
				return false;
			}

		}

	}

	public static void main(String[] args) {
		boolean[][] maze = Maze.generateStandardMaze(10, 10);
		StudentResult result = new StudentResult();
		Walker walker = new Walker(maze, result);
		System.out.println(walker.walk());
		Maze.draw(maze, result);
	}

}
