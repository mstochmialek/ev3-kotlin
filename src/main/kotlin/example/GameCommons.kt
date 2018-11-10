package example

import lejos.hardware.lcd.GraphicsLCD

data class Dimensions(val width: Int, val height: Int)
data class Position(var x: Int, var y: Int, private val board: Dimensions) {
    fun up() {
        if (y > 0) {
            y--
        }
    }
    fun down() {
        if (y +1 < board.height) {
            y++
        }
    }
    fun left() {
        if (x > 0) {
            x--
        }
    }
    fun right() {
        if (x + 1 < board.width) {
            x++
        }
    }
}
enum class GameState {
    WIN,
    LOSS,
    TIE,
    PLAYING,
    ABORT
}

class Screen (private val graphicsLCD: GraphicsLCD, private val boardSize: Dimensions) {
    private val fieldHeight = graphicsLCD.height / boardSize.height
    private val fieldWidth = graphicsLCD.width / boardSize.width

    fun clear() {
        graphicsLCD.clear()
    }
    fun drawMessage(msg: String) {
        graphicsLCD.drawString(msg, graphicsLCD.width / 2, graphicsLCD.height / 2, GraphicsLCD.HCENTER)
    }
    fun drawRect(pos: Position) {
        graphicsLCD.drawRect(pos.x * fieldWidth, pos.y * fieldHeight, fieldWidth, fieldHeight)
    }
    fun drawCross(pos: Position) {
        graphicsLCD.drawLine(pos.x * fieldWidth, pos.y * fieldHeight,
                pos.x * fieldWidth + fieldWidth, pos.y * fieldHeight + fieldHeight )
        graphicsLCD.drawLine(pos.x * fieldWidth, pos.y * fieldHeight + fieldHeight,
                pos.x * fieldWidth + fieldWidth, pos.y * fieldHeight )
    }
    fun drawRoundRect(pos: Position) {
        graphicsLCD.drawRoundRect(pos.x * fieldWidth + 5, pos.y * fieldHeight + 5,
                fieldWidth - 10, fieldHeight - 10, 10, 10)
    }
    fun drawCircle(pos: Position) {
        graphicsLCD.drawRoundRect(pos.x * fieldWidth + 5, pos.y * fieldHeight + 5,
                fieldWidth - 10, fieldHeight - 10, fieldWidth, fieldHeight)
    }
}

