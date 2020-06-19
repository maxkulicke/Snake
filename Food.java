/* Max Kulicke
 * mkulicke
 * recitation 201
 * 
 * Food:
 * 
 * this class governs the Food object,
 * a single Sector object that the Snake tries
 * to eat, accumulating points
 * 
 */

public class Food {
    
  private int value;
  private Sector location;
  
  /*
   * Constructor
   */
  public Food(int value, Sector location) {
      this.value = value;
      this.location = location;
      this.occupy(location);
  }
  
  /*
   * occupy
   * 
   * makes the Sector's food and occupy boolean 
   * true by calling the sector's makeFood function
   */
  public void occupy(Sector sector) {
      sector.makeFood();
  }
  
  /*
   * getValue
   * 
   * returns the int value of the food
   */
  public int getValue() {
      return this.value;
  }
  
  /*
   * getLocation
   * 
   * returns the Sector location of the food
   * 
   */
  public Sector getLocation() {
      return this.location;
  }
}