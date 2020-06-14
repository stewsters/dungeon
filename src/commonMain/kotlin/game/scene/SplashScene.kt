package game.scene

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launchImmediately

class SplashScene : Scene() {
    override suspend fun Container.sceneInit() {
        text("Dungeon")
        solidRect(100.0, 100.0, Colors.RED).position(100, 100).onClick {
            launchImmediately { sceneContainer.changeTo<SplashScene>() }
        }
    }
}