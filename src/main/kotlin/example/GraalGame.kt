package example

import lejos.hardware.BrickFinder
import lejos.hardware.Keys
import kotlin.random.Random

fun main(args: Array<String>) {
    val brick = BrickFinder.getLocal()
    val board = Dimensions(3, 3)
    val screen = Screen(brick.graphicsLCD, board)
    val player = Position(0, 0, board)
    val treasure = Position(Random.nextInt(board.height), Random.nextInt(board.width), board)
    var tested = emptyList<Position>()
    var gameState = GameState.PLAYING
    val maxNumberOfTries = 5

    fun drawScreen(gameState: GameState) {
        screen.clear();
        screen.drawRect(player);
        tested.forEach {
            screen.drawCross(it)
        }
        when(gameState) {
            GameState.WIN -> {
                screen.drawRoundRect(treasure)
                screen.drawMessage("You won!")
            }
            GameState.LOSS -> {
                screen.drawMessage("Game over!")
            }
        }
    }

    while(gameState != GameState.ABORT) {
        drawScreen(gameState)

        val key = brick.keys.waitForAnyPress()
        when(key) {
            Keys.ID_ESCAPE -> gameState = GameState.ABORT
            Keys.ID_RIGHT -> player.right()
            Keys.ID_LEFT -> player.left()
            Keys.ID_UP -> player.up()
            Keys.ID_DOWN -> player.down()
            Keys.ID_ENTER -> {
                gameState = when {
                    treasure == player -> GameState.WIN
                    tested.size == maxNumberOfTries -> GameState.LOSS
                    else -> GameState.PLAYING
                }
                if (gameState != GameState.WIN) {
                    tested = tested.plus(player.copy())
                }
            }
        }
    }
}