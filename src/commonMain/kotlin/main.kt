import com.soywiz.klock.milliseconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.korau.sound.NativeSound
import com.soywiz.korau.sound.readMusic
import com.soywiz.korau.sound.readSound
import com.soywiz.korev.Key
import com.soywiz.korge.Korge
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.camera
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.Rectangle
import game.action.Walk
import game.world.CritterType
import game.world.TileType
import game.world.generateMap
import math.Vec2

val tileSize = 16
val mapWidth = 64
val mapHeight = 64
val east = Vec2(1, 0)
val north = Vec2(0, -1)
val west = Vec2(-1, 0)
val south = Vec2(0, 1)

lateinit var swing:NativeSound
lateinit var door:NativeSound

suspend fun main() = Korge(width = tileSize * mapWidth, height = tileSize * mapHeight, bgcolor = Colors["#2b2b2b"]) {
    // scale = 4.0
    textureWork()

    val music = resourcesVfs["music/RuinsOfEmpire.mp3"].readMusic()
//    val music = resourcesVfs["music/ursus.mp3"].readMusic()
    music.play()

    swing = resourcesVfs["sound/swing.wav"].readSound()
    door = resourcesVfs["sound/door.wav"].readSound()

    val world = generateMap()
    world.tiles.forEach { tile -> addChild(tile.sprite) }
    world.entities.forEach { addChild(it.sprite) }

    keys {
        down(Key.RIGHT) {
            world.player.ai?.setAction(Walk(east))
        }
        down(Key.UP) {
            world.player.ai?.setAction(Walk(north))
        }
        down(Key.LEFT) {
            world.player.ai?.setAction(Walk(west))
        }
        down(Key.DOWN) {
            world.player.ai?.setAction(Walk(south))
        }
    }

    addFixedUpdater(60.timesPerSecond) {

        // TODO: this doesnt work?
        camera {
            setTo(Rectangle(world.player.pos.x * tileSize, world.player.pos.y * tileSize, 64, 64))
        }

        // Get the next person who acts, do their act
        world.passTime()


        world.entities.forEach {

            // This does linear interpolation for
            val diff = Point(
                    (it.pos.x * tileSize).toDouble() - it.sprite.x,
                    (it.pos.y * tileSize).toDouble() - it.sprite.y
            )
            if (diff.length > 3) {
                diff.normalize()
                diff.mul(3.0)
            }

            it.sprite.x += diff.x
            it.sprite.y += diff.y

            if (it.isAlive())
                if (diff.length > 2) {
                    it.sprite.playAnimationLooped(it.type.moveAnimation, spriteDisplayTime = 250.milliseconds)
                } else
                    it.sprite.playAnimationLooped(it.type.standAnimation, spriteDisplayTime = 250.milliseconds)
            else
                it.sprite.stopAnimation()
        }
    }

}


suspend fun textureWork() {

    val spriteSheet = resourcesVfs["spritesheet.png"].readBitmap()

    CritterType.BAT.apply {
        standAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, 0, 0, 4, 1)
        moveAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, 0, 0, 4, 1)
    }
    CritterType.GOBLIN.apply {
        standAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, tileSize, 0, 6, 1)
        moveAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, 32, 0, 6, 1)
    }
    CritterType.JELLY.apply {
        standAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, 48, 0, 6, 1)
        moveAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, 64, 0, 6, 1)
    }
    CritterType.KNIGHT.apply {
        standAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, 80, 0, 6, 1)
        moveAnimation = SpriteAnimation(spriteSheet, tileSize, tileSize, 96, 0, 6, 1)
    }

    val tileMap = resourcesVfs["tilemap.png"].readBitmap()

    TileType.FLOOR.animation = SpriteAnimation(tileMap, tileSize, tileSize, 1 * tileSize, 3 * tileSize, 1, 1)
    TileType.WALL.animation = SpriteAnimation(tileMap, tileSize, tileSize, 4 * tileSize, 7 * tileSize, 1, 1)
    TileType.DIRT.animation = SpriteAnimation(tileMap, tileSize, tileSize, 2 * tileSize, 5 * tileSize, 1, 1)

    TileType.DOOR_CLOSED.animation = SpriteAnimation(tileMap, tileSize, tileSize, 5 * tileSize, 7 * tileSize, 1, 1)
    TileType.DOOR_OPEN.animation = SpriteAnimation(tileMap, tileSize, tileSize, 1 * tileSize, 3 * tileSize, 1, 1)
    TileType.WATER_DEEP.animation = SpriteAnimation(tileMap, tileSize, tileSize, 2 * tileSize, 5 * tileSize, 1, 1)
    TileType.WATER_SHALLOW.animation = SpriteAnimation(tileMap, tileSize, tileSize, 1 * tileSize, 2 * tileSize, 1, 1)
}

