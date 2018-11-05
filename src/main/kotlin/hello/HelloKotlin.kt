package hello

import lejos.hardware.BrickFinder

fun main(args: Array<String>) {
    val LCD = BrickFinder.getLocal().textLCD
    LCD.clear()
    LCD.drawString("Hello Kotlin! It's mst", 3, 3)
    BrickFinder.getLocal().keys.waitForAnyPress()
}