/* Max Kulicke
 * mkulicke
 * recitation 201
 * 
 * Snake
 * This class governs the snake object. 
 * 
 * the snake object in its essence is really just
 * a Head Sector, a Tail sector and a direction of 
 * motion.
 * 
 * the Snake object does not track which or how many
 * Sectors are currently snake sectors. it has no need to.
 * 
 * all it does is move forward in whichever direction it is
 * currently facing, by setting the nextTo sector in that
 * direction as it's Head, and scrubbing it's current tail
 * and moving it's tail one in.
 * 
 * the Snake eats, and Adds but both of those are handled 
 * entirely by the head and tail sectors
 * 
 */

package snake;

public class Snake {
    
  private char direction;
  private Sector head;
  private Sector tail;
  private boolean justAte;
  private boolean crash;
  
  /*
   * Constructor
   */
  public Snake(Sector head, char direction) {
      this.head = head;
      this.tail = head;
      this.direction = direction;
      this.justAte = false;
      this.crash = false;
      this.head.makeSnake(direction);
      for (int i = 0; i < 7; i++) {
          this.add();
      }
  }
  
  /*
   * Move
   * 
   * extremely important, moves the snake around
   * by changing the head to the next sector in the
   * snake's current direction, and moving the tail
   * and clearing the old tail
   */
  public void move() {
      this.justAte = false;
      Sector next = this.head.getNext(this.direction); 
      Sector nextHead = this.head.getNext(this.direction); 
      if (nextHead.isSnake() || nextHead.isBoundary()) { 
          this.crash();
          return;
      }
      nextHead.makeSnake(this.direction); 
      setHead(nextHead); 
      Sector nextTail = this.tail.getNext(this.tail.getDirection());
      this.tail.clear();
      setTail(nextTail);
      if (next.isFood()) {                            
          next.clearFood();          
          this.eat();    
      }
  }
  
  /*
   * Turn
   * 
   * turns the snake 90 degrees, restricts the motion
   * so it can't turn 180
   */
  public void turn(char newDirection) {
      char currentDirection = this.direction;
      if (currentDirection == 'N' && newDirection == 'S') {
          return;
      } else if (currentDirection == 'S' && newDirection == 'N') {
          return;
      } else if (currentDirection == 'E' && newDirection == 'W') {
          return;
      } else if (currentDirection == 'W' && newDirection == 'E') {
          return;
      }
      this.direction = newDirection;
      this.head.setDirection(newDirection);
  }
  
  /*
   * add
   * 
   * adds a sector segment to the tail of the snake
   * 
   * does not work if the next tail segment is a
   * boundary Sector
   */
  public void add() {
      Sector tail = this.tail;
      char tailDirection = tail.getDirection();
      char addDirection;
      if (tailDirection == 'N') {
          addDirection = 'S';
      } else if (tailDirection == 'S') {
          addDirection = 'N';
      } else if (tailDirection == 'E') {
          addDirection = 'W';
      } else {
          addDirection = 'E';
      }
      Sector nextTail = tail.getNext(addDirection);
      if (nextTail.isBoundary()) {
          return;
      }
      nextTail.makeSnake(tailDirection);
      this.setTail(nextTail);
  }
  
  /*
   * crash
   * 
   * sets the snake's crash boolean to true
   */
  public void crash() {
      this.crash = true;
  }
  
  /*
   * didCrash
   * 
   * returns the boolean state of crash
   */
  public boolean didCrash() {
      return this.crash;
  }
  
  /*
   * setTail 
   * 
   * sets a sector to be the tail of the snake
   * used by move and add
   */
  public void setTail(Sector tail) {
      this.tail = tail;
  }
  
  /*
   * setHead
   * 
   * sets a sector to be the tail of the snake
   */
  public void setHead(Sector head) {
      this.head = head;
  }
  
  /*
   * eat
   * 
   * kind of a tricky one, since it is called from
   * move(), but also calls move()
   * 
   * adds two segments
   */
  public void eat() {
      this.justAte = true;
      this.add();                   
      this.move();
      this.add();
  }
  
  /*
   * justAte
   * 
   * returns the justAte boolean of the snake
   */
  public boolean justAte() {
      return this.justAte;
  }
}
