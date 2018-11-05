package hello

import lejos.hardware.BrickFinder

fun main(args: Array<String>) {
    val localBrick = BrickFinder.getLocal()
    val lcd = localBrick.textLCD
    lcd.clear()
    lcd.drawString("Hello Ev3 Kotlin!", 1, 3)
    localBrick.keys.waitForAnyPress()
}