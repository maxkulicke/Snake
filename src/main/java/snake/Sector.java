/* Max Kulicke
 * mkulicke
 * recitation 201
 * 
 * Sector: 
 * this class governs the behavior of Sectors, an object
 * that is the building block of my Snake game. Think about
 * it as a pixel basically, which maintains an internal state,
 * interacts with other Sectors around it, and draws itself
 * in different colors depending on its state.
 * 
 * One of the most important field types that Sectors store
 * it their NextTo, which is the field directly adjacent to
 * them, in any direction (not diagonal). this field is what
 * the Snake object relies on to move around on top of the 
 * Sectors and know which way to go.
 * 
 * Sectors also maintain their "snake directionality," meaning
 * that if a snake is going up when it moves over a sector,
 * that sector retains the up direction, even if the snake
 * subsequently changes direction.
 * 
 * Sectors make up the Grid object 2D array, which is the visual
 * display of the Snake and Food objects, both of which are really
 * just Sector(s).
 * 
 * 
 */

package snake;

public class Sector {
    
    
  private boolean isBoundary;
  private boolean isSnake;
  private boolean isFood;
  private boolean isOccupied;
  private char snakeDirection;
  private final int locationX;
  private final int locationY;
  private Sector nextToN;
  private Sector nextToS;
  private Sector nextToE;
  private Sector nextToW;
  
  
  //Constructor
  public Sector(int locationX, int locationY) {
      this.locationX = locationX;
      this.locationY = locationY;
  }
  
  
  //null Constructor
  public Sector() {
      this.locationX = 0;
      this.locationY = 0;
  }
  
  /*
   * setNextToN
   * 
   * setter for the nextToN sector
   */
  public void setNextToN(Sector next) {
      this.nextToN = next;
  }
  
  /*
   * setNextToN
   * 
   * setter for the nextToN sector
   */
  public void setNextToS(Sector next) {
      this.nextToS = next;
  }
  
  /*
   * setNextToN
   * 
   * setter for the nextToN sector
   */
  public void setNextToE(Sector next) {
      this.nextToE = next;
  }
  
  /*
   * setNextToN
   * 
   * setter for the nextToN sector
   */
  public void setNextToW(Sector next) {
      this.nextToW = next;
  }
  
  
  /*
   * Draw
   * 
   * the Sector draws itself as a small square,
   * its color reflects its state. 
   * black: empty
   * pink: food
   * blurple: snake
   * teal: boundary
   */
  public void draw() {
      if (isBoundary) {
          PennDraw.setPenColor(0, 172, 178);
      } else if (isSnake) {
          PennDraw.setPenColor(94, 99, 255);
      } else if (isFood) {
          PennDraw.setPenColor(255, 94, 174);
      } else {
          PennDraw.setPenColor(0, 0, 0);
      }        
      PennDraw.filledSquare(locationX, locationY, 0.5);
  }
  
  
  /*
   * makeBoundary
   * 
   * sets the occupied and boundary states of
   * the Sector to true
   */
  public void makeBoundary() {
      this.isBoundary = true;
      this.isOccupied = true;
  }
  
  
  /*
   * isBoundary
   * 
   * returns the boolean state of isBoundary
   * true if it is, false if not
   */
  public boolean isBoundary() {
      return this.isBoundary;
  }
  
  
  /*
   * getNext
   * 
   * super crucial method, returns whichever 
   * Sector abuts the calling Sector in the
   * specified direction
   * 
   * essential function to the movement of
   * the snake
   */
  public Sector getNext(char direction) {
      Sector next;
      if (direction == 'N') {
          next = this.nextToN;
      } else if (direction == 'S') {
          next = this.nextToS;
      } else if (direction == 'E') {
          next = this.nextToE;
      } else {
          next = this.nextToW;
      }
      return next;
  }
  
  
  /*
   * makeSnake
   * 
   * sets the isOccupied and isSnake states
   * to true. additionally, stores the direction
   * the snake was moving when it moved over this
   * Sector
   */
  public void makeSnake(char direction) {
      this.snakeDirection = direction;
      this.isSnake = true;
      this.isOccupied = true;
  }
  
  
  /*
   * isSnake
   * 
   * returns the boolean state of isSnake
   * true if it is,
   * false if not
   */
  public boolean isSnake() {
      return this.isSnake;
  }
  
  
  /*
   * makeFood
   * 
   * sets the isFood and isOccupied states
   * to true, making the Sector Food
   */
  public void makeFood() {
      this.isFood = true;
      this.isOccupied = true;
  }
  
  
  /*
   * isFood
   * 
   * returns the boolean state of isFood
   * true if it is,
   * false if not
   */
  public boolean isFood() {
      return this.isFood;
  }
  
  
  /*
   * getDirection
   * 
   * returns the char that is the direction
   * the snake was travelling when it 
   * made this Sector a snake Sector
   */
  public char getDirection() {
      return this.snakeDirection;
  }
  
  
  /*
   * getDirection
   * 
   * sets the char that is the direction
   * the snake was travelling when it 
   * made this Sector a snake Sector
   */
  public void setDirection(char direction) {
      this.snakeDirection = direction;
  }
  
  
  /*
   * clear
   * 
   * returns the Sector back to its null state
   * 
   * is called when a snake or a food leaves
   * a sector.
   * 
   * note, the isBoundary state can't be cleared
   */
  public void clear() {
      this.isFood = false;
      this.isSnake = false;
      //this.isBoundary = false;
      this.isOccupied = false;
      this.snakeDirection = '\0';
  }
  
  
  /*
   * clearFood
   * 
   * reverts a Food Sector back to a null Sector
   */
  public void clearFood() {
      this.isFood = false;
      this.isOccupied = false;
  }
  
  
  /*
   * isFree
   * 
   * simply returns the isOccupied boolean state
   * of the calling Sector
   * 
   * true if it is occupied
   * false if not
   */
  public boolean isFree() {
      if (this.isOccupied) {
          return false;
      } else {
          return true;
      }
  }
  
  
}