/* Max Kulicke
 * mkulicke
 * recitation 201
 * 
 * PlaySnake
 * 
 * playSnake is really the game runner, and is the class
 * you Run to actually play the game. playSnake instantiates
 * the Grid object (called gameGrid), which in turn is told to
 * instantiate a Snake and Food object.
 * 
 * playSnake has several draw methods, which draw the scoreboards,
 * display the game rules, display the controls, and also draws a
 * Game Over sign when the snake crashes.
 * 
 * playSnake tracks the current score, the highest score of each
 * run of the program, and the current value of the Food object, which
 * increases over time
 * 
 * the Main of playSnake has some nested while loops that govern
 * key controls, game restarts, gameGrid draw calls, snake and food
 * repopulations and snake crashes, and the overall animation work
 * being done in PennDraw. Main is really where all of the pieces
 * start to work together in harmony.
 */

package snake;

public class PlaySnake {
     
  private static int foodValue = 50;
  private static int score = 0;
  private static int hiScore = 0;
  
  /*
   * drawScoreboard
   * 
   * draws the scoreboard that displays the player's
   * current score
   */
  public static void drawScoreboard(int score) {
      PennDraw.setPenColor(183, 0, 137);
      PennDraw.filledRectangle(67, 48, 8, 4);
      PennDraw.setPenColor(0, 0, 0);
      PennDraw.filledRectangle(67, 48, 7, 3);
      PennDraw.setPenColor(94, 99, 255);
      PennDraw.setFontSize(30);
      String scoreString = String.valueOf(score);
      PennDraw.text(67, 47, scoreString);
      PennDraw.text(67, 54, "Score");
  }
  
  /*
   * drawHiScore
   * 
   * draws the scoreboard that displays the highest score
   * of the current run of the program
   */
  public static void drawHiScore(int hiScore) {
      PennDraw.setPenColor(183, 0, 137);
      PennDraw.filledRectangle(87, 48, 8, 4);
      PennDraw.setPenColor(0, 0, 0);
      PennDraw.filledRectangle(87, 48, 7, 3);
      PennDraw.setPenColor(94, 99, 255);
      PennDraw.setFontSize(30);
      String hiScoreString = String.valueOf(hiScore);
      PennDraw.text(87, 47, hiScoreString);
      PennDraw.text(87, 54, "High Score");
  }
  
  /*
   * drawRules
   * 
   * draws the rules of game play and key commands
   * as text
   */
  public static void drawRules() {
      PennDraw.setPenColor(94, 99, 255);
      PennDraw.setFontSize(23);
      PennDraw.textLeft(60, 37, "press 'A' to go left");
      PennDraw.textLeft(60, 33, "press 'D' to go right");
      PennDraw.textLeft(60, 29, "press 'W' to go up");
      PennDraw.textLeft(60, 25, "press 'S' to go down");
      PennDraw.textLeft(60, 19, "don't let Snake touch the walls");
      PennDraw.textLeft(60, 15, "don't let Snake touch itself");
      PennDraw.setFontSize(27);
      PennDraw.setPenColor(183, 0, 137);
      PennDraw.textLeft(60, 8, "press any key to start");
  }
  
  /*
   * gameOver
   * 
   * draws the game over text that flashes when the snake
   * crashes
   */
  public static void gameOver() {
      PennDraw.setPenColor(183, 0, 137);
      PennDraw.setFontSize(75);
      PennDraw.text(29, 30, "GAME OVER");
      PennDraw.setPenColor(94, 99, 255);
      PennDraw.setFontSize(25);
      PennDraw.text(29, 25, "press any key for new game");
  }
  
  // MAIN
  
  public static void main(String[] args) {
      PennDraw.setCanvasSize(1000, 600);
      PennDraw.setXscale(0, 100);
      PennDraw.setYscale(0, 60);
      Grid gameGrid = new Grid(50, 50, 5, 55);
      Snake snake = gameGrid.makeSnake();
      Food food = gameGrid.makeFood(foodValue);
      PennDraw.enableAnimation(16);
      boolean play = false;
      while (true) {
          PennDraw.advance();
          PennDraw.clear(0, 0, 0);
          drawScoreboard(score);
          drawHiScore(hiScore);
          drawRules();
          gameGrid.draw();
          if (PennDraw.hasNextKeyTyped()) {
              play = true;
              while (play) {
                  PennDraw.advance();
                  snake.move();
                  while (snake.didCrash()) {
                      gameOver();
                      PennDraw.advance();
                      play = false;
                      if (PennDraw.hasNextKeyTyped()) {
                          gameGrid.clearSnake();
                          gameGrid.clearFood();
                          score = 0;
                          foodValue = 50;
                          snake = gameGrid.makeSnake();
                          food = gameGrid.makeFood(foodValue);
                      }
                  }
                  boolean isFood = gameGrid.hasFood();
                  if (isFood == false) {
                      score += foodValue;
                      foodValue *= 1.05;
                      gameGrid.makeFood(foodValue);
                  }
                  
                  if (score > hiScore) {
                      hiScore = score;
                  }
                  
                  if (PennDraw.hasNextKeyTyped()) {
                      char direction = PennDraw.nextKeyTyped();
                      if (direction == 'a' || direction == 'A') {
                          snake.turn('W');
                      } else if (direction == 'd' || direction == 'D') {
                          snake.turn('E');
                      } else if (direction == 's' || direction == 'S') {
                          snake.turn('S');
                      } else if (direction == 'w' || direction == 'W') {
                          snake.turn('N');
                      }
                  }
                  PennDraw.clear(0, 0, 0);
                  drawScoreboard(score);
                  drawHiScore(hiScore);
                  drawRules();
                  gameGrid.draw();
              }
          }
      }
  }
}