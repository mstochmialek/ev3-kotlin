package example

import lejos.hardware.BrickFinder
import lejos.hardware.Keys


enum class Turn { CROSSES, NOUGHTS }

fun main(args: Array<String>) {
    val brick = BrickFinder.getLocal()
    val board = Dimensions(3, 3)
    val screen = Screen(brick.graphicsLCD, board)
    val cursor = Position(0, 0, board)

    var crosses = emptyList<Position>()
    var noughts = emptyList<Position>()
    var gameState = GameState.PLAYING
    var turn = Turn.CROSSES


    fun drawScreen(gameState: GameState) {
        screen.clear();
        screen.drawRect(cursor);
        crosses.forEach {
            screen.drawCross(it)
        }
        noughts.forEach {
            screen.drawCircle(it)
        }
        screen.drawMessage(when(gameState) {
            GameState.WIN -> "You won!"
            GameState.LOSS -> "Game over!"
            GameState.TIE -> "It's a tie!"
            else -> ""
        })
    }

    fun haveWon(marks: List<Position>) =
        (0..2).any { marks.count { mark -> mark.x == it } == 3}
        || (0..2).any { marks.count { mark -> mark.y == it } == 3}
        || marks.count { mark -> mark.y == mark.x } == 3
        || marks.count { mark -> mark.y == 2 - mark.x } == 3

    fun calculateGameState(crosses: List<Position>, noughts: List<Position>):GameState =
        when {
            haveWon(crosses) -> GameState.WIN
            haveWon(noughts) -> GameState.LOSS
            crosses.plus(noughts).size == 9 -> GameState.TIE
            else -> GameState.PLAYING
        }

    while(gameState != GameState.ABORT) {
        drawScreen(gameState)

        val key = brick.keys.waitForAnyPress()
        when(key) {
            Keys.ID_ESCAPE -> gameState = GameState.ABORT
            Keys.ID_RIGHT -> cursor.right()
            Keys.ID_LEFT -> cursor.left()
            Keys.ID_UP -> cursor.up()
            Keys.ID_DOWN -> cursor.down()
            Keys.ID_ENTER -> {
                val emptyField = crosses.all { it != cursor } && noughts.all { it != cursor }
                if (gameState == GameState.PLAYING && emptyField) {
                    when (turn) {
                        Turn.CROSSES -> {
                            crosses = crosses.plus(cursor.copy())
                            turn = Turn.NOUGHTS
                        }
                        Turn.NOUGHTS -> {
                            noughts = noughts.plus(cursor.copy())
                            turn = Turn.CROSSES
                        }
                    };
                    gameState = calculateGameState(crosses, noughts)
                }
            }
        }
    }
}