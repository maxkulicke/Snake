/* Max Kulicke
 * mkulicke
 * recitation 201
 * 
 * Grid
 * 
 * this class governs the Grid object, which contains a
 * 2D array of Sectors. the Grid is sort of the management
 * object of the game. it populates snakes, food, and boundary
 * Sectors, and additionally is responsible for telling each 
 * Sector when to draw itself
 * 
 * 
 */

public class Grid {
    
  private final int numColumns;
  private final int numRows;
  private Sector[][] gameGrid;
  
  /*
   * Constructor
   * 
   * a pretty complicated constructor, I've toyed with the
   * idea of allocating some of the boundary assignment
   * and nextTo assignment to separate methods, but since
   * it works well now, i'm not going to toy with it.
   * 
   * really, the hard work is getting each sector into the
   * right spot within the 2D array, and then correctly assigning
   * each Sector's nextTo in al 4 directions. it's a really
   * crucial step, since the nextTo's are essential to the move
   * eat and add functions of the Snake.
   */
  public Grid(int numColumns, int numRows, int topLeftX, int topLeftY) {
      this.numColumns = numColumns;
      this.numRows = numRows;
      this.gameGrid = new Sector[numColumns][numRows];
      int currentX = topLeftX;
      for (int column = 0; column < this.numColumns; column++) {
          int currentY = topLeftY;
          for (int row = 0; row < this.numRows; row++) {
              Sector newSector = new Sector(currentX, currentY);
              gameGrid[column][row] = newSector;
              if (column == 0 || column == this.numColumns - 1) {
                  newSector.makeBoundary();
              } else if (row == 0 || row == this.numRows - 1) {
                  newSector.makeBoundary();
              }
              currentY--;
          }
          currentX++;
      }
      //this section sets all of the nextTo's
      //pretty convoluted, but very important
      for (int column = 0; column < this.numColumns; column++) {
          for (int row = 0; row < this.numRows; row++) {
              Sector current = gameGrid[column][row];
              //if W boundary
              if (column == 0) {
                  current.setNextToW(gameGrid[column + 1][row]);
                  if (row == 0) {
                      current.setNextToS(gameGrid[column][row + 1]);
                  } else if (row == numRows - 1) {
                      current.setNextToN(gameGrid[column][row - 1]);
                  } else {
                      current.setNextToS(gameGrid[column][row + 1]);
                      current.setNextToN(gameGrid[column][row - 1]);
                  }
              }
              //if E boundary
              else if (column == numColumns - 1) {
                  current.setNextToW(gameGrid[column - 1][row]);
                  if (row == 0) {
                      current.setNextToS(gameGrid[column][row + 1]);
                  } else if (row == numRows - 1) {
                      current.setNextToN(gameGrid[column][row - 1]);
                  } else {
                      current.setNextToS(gameGrid[column][row + 1]);
                      current.setNextToN(gameGrid[column][row - 1]);
                  }
              } 
              //if N boundary
              else if (row == 0) {
                  current.setNextToS(gameGrid[column][row + 1]);
                  if (column == 0) {
                      current.setNextToE(gameGrid[column + 1][row]);
                  } else if (column == numColumns - 1) {
                      current.setNextToW(gameGrid[column - 1][row]);
                  } else {
                      current.setNextToW(gameGrid[column - 1][row]);
                      current.setNextToE(gameGrid[column + 1][row]);
                  }
              } 
              //if S boundary
              else if (row == numRows - 1) {
                  current.setNextToN(gameGrid[column][row - 1]);
                  if (column == 0) {
                      current.setNextToE(gameGrid[column + 1][row]);
                  } else if (column == numColumns - 1) {
                      current.setNextToW(gameGrid[column - 1][row]);
                  } else {
                      current.setNextToW(gameGrid[column - 1][row]);
                      current.setNextToE(gameGrid[column + 1][row]);
                  }
              } 
              //not a boundary
              else {
                  current.setNextToN(gameGrid[column][row - 1]);
                  current.setNextToS(gameGrid[column][row + 1]);
                  current.setNextToE(gameGrid[column + 1][row]);
                  current.setNextToW(gameGrid[column - 1][row]);
              }
          }
      }
  }
  
  /*
   * draw
   * 
   * iterates through each Sector in the 2D array and
   * calls that sector's draw function
   */
  public void draw() {
      for (int column = 0; column < this.numColumns; column++) {
          for (int row = 0; row < this.numRows; row++) {
              Sector current = this.gameGrid[column][row];
              //System.out.println(current.locationX + ", " + current.locationY);
              current.draw();
          }
      }
  }
  
  /*
   * makeSnake
   * 
   * makes a new Snake object. the Snake
   * starts in the same spot each time.
   * (11/24 is my birthday)
   */
  public Snake makeSnake() {
      Snake snake = new Snake(gameGrid[11][24], 'E');
      return snake;
  }
  
  /*
   * clearSnake
   * 
   * clears all Sectors of a true isSnake boolean
   * 
   * this method is called when a snake crashes and the
   * game needs to restart
   */
  public void clearSnake() {
      for (int column = 0; column < this.numColumns; column++) {
          for (int row = 0; row < this.numRows; row++) {
              Sector temp = gameGrid[column][row];
              if (temp.isSnake()) {
                  temp.clear();
              }
          }
      }
  }
  
  /*
   * clearFood
   * 
   * clears all Sectors of a true isFood boolean
   * 
   * this method is called when a snake crashes and
   * the game needs to restart and replace the old
   * food with a new one
   */
  public void clearFood() {
      for (int column = 0; column < this.numColumns; column++) {
          for (int row = 0; row < this.numRows; row++) {
              Sector temp = gameGrid[column][row];
              if (temp.isFood()) {
                  temp.clear();
              }
          }
      }
  }
  
  /*
   * makeFood
   * 
   * populates the grid with one Food Sector
   */
  public Food makeFood(int value) {
      int randomColumn = numColumns;
      randomColumn *= Math.random();
      int randomRow = numRows;
      randomRow *= Math.random();
      Sector newFood = gameGrid[randomColumn][randomRow];
      if (newFood.isFree()) {
          Food food = new Food(value, newFood);
          return food;
      } else {
          return makeFood(value);
      }
  }
  
  /*
   * getGameGrid
   * 
   * getter for the 2D array
   */
  public Sector[][] getGameGrid() {
      return this.gameGrid;
  }
  
  /*
   * getNumColumns
   * 
   * getter for numColumns
   */
  public int getNumColumns() {
      return this.numColumns;
  }
  
  /*
   * getNumRows
   * 
   * getter for numRows
   */
  public int getNumRows() {
      return this.numRows;
  }
  
  /*
   * hasFood
   * 
   * simply finds out if any Sectors are currently food,
   * used to determine if it needs to make a new one
   */
  public boolean hasFood() {
      boolean hasFood = false;
      for (int column = 0; column < this.numColumns; column++) {
          for (int row = 0; row < this.numRows; row++) {
              if (gameGrid[column][row].isFood()) {
                  hasFood = true;
              }
          }
      }
      return hasFood;
  }
  
}