package game.world

import com.soywiz.korge.view.xy
import game.ai.OpponentAI
import game.ai.PlayerAI
import math.Matrix2d
import math.Rect
import math.Vec2
import tileSize
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


private val ROOM_MAX_SIZE = 12
private val ROOM_MIN_SIZE = 6
private val MAX_ROOMS = 20
private val MAX_ROOM_MONSTERS = 2
private val MAX_ROOM_ITEMS = 2


fun d(max: Int) = (1..max).random()
fun coinFlip() = Random.nextBoolean()

suspend fun generateMap(): World {

    val player = Entity(Vec2(7, 7), CritterType.PLAYER, PlayerAI(), player = true)
    val map = Matrix2d(64, 64) { x, y -> Cell(x, y, TileType.DIRT) }

    val rooms = mutableListOf<Rect>()
    val entities = mutableListOf(player)

    (1..MAX_ROOMS).forEach {
        val w = (ROOM_MIN_SIZE..ROOM_MAX_SIZE).random()
        val h = (ROOM_MIN_SIZE..ROOM_MAX_SIZE).random()

        val roomX = (0 until map.xSize - w).random()
        val roomY = (0 until map.ySize - h).random()

        val newRoom = Rect(roomX, roomY, w + roomX, h + roomY)
        if (rooms.any { newRoom.intersect(it) })
            return@forEach

        when (d(10)) {
            1 -> pillarRoom(
                    map, newRoom, d(3) + 1,
                    if (d(6) == 0) TileType.WATER_SHALLOW else TileType.WALL,
                    TileType.FLOOR,
                    if (coinFlip()) TileType.WATER_SHALLOW else null
            )
            2 -> digPool(map, newRoom, TileType.WATER_SHALLOW, TileType.WATER_DEEP)
            else -> createRoom(map, newRoom)
        }

        val center = newRoom.center()
        if (rooms.size == 0) {
            //set player start
            player.pos = center
            player.sprite.xy(center.x * tileSize, center.y * tileSize)
        } else {
            //TODO: add objects
            //placeObjects(map, newRoom,  MAX_ROOM_MONSTERS, MAX_ROOM_ITEMS)

            entities += Entity(center, enemies().random(), OpponentAI())

            val prev = rooms[(rooms.size - 1)].center()

            if (coinFlip()) {
                createHTunnel(map, prev.x, center.x, prev.y)
                createVTunnel(map, prev.y, center.y, center.x)
            } else {
                createVTunnel(map, prev.y, center.y, prev.x)
                createHTunnel(map, prev.x, center.x, center.y)
            }
        }
        rooms.add(newRoom)
    }

    map.forEach { cell -> cell.rebuild() }

    return World(
            tiles = map,
            entities = entities,
            player = player
    )

}


/**
 * Paint a room onto the map's tiles
 * @return
 */
private fun createRoom(map: Matrix2d<Cell>, room: Rect) {
    ((room.x1)..(room.x2)).forEach { x ->
        ((room.y1)..(room.y2)).forEach { y ->
            map[x, y].tileType = if (room.onEdge(x, y))
                TileType.WALL
            else
                TileType.FLOOR
        }
    }
}

private fun createHTunnel(map: Matrix2d<Cell>, x1: Int, x2: Int, y: Int) {
    (min(x1, x2)..max(x1, x2)).forEach { x ->

        when (map[x, y].tileType) {
            TileType.WALL -> {
                map[x, y].tileType = TileType.DOOR_CLOSED
            }
            TileType.DIRT -> {
                map[x, y].tileType = TileType.FLOOR
            }
        }

    }
}

private fun createVTunnel(map: Matrix2d<Cell>, y1: Int, y2: Int, x: Int) {
    (min(y1, y2)..max(y1, y2)).forEach { y ->
        when (map[x, y].tileType) {
            TileType.WALL -> {
                map[x, y].tileType = TileType.DOOR_CLOSED
            }
            TileType.DIRT -> {
                map[x, y].tileType = TileType.FLOOR
            }
        }
    }
}

private fun digPool(map: Matrix2d<Cell>, room: Rect, shallow: TileType, deep: TileType) {

    ((room.x1)..(room.x2)).forEach { x ->
        ((room.y1)..(room.y2)).forEach { y ->
            map[x, y].tileType =
                    if (x == room.x1 || x == room.x2 || y == room.y1 || y == room.y2) {
                        shallow
                    } else {
                        deep
                    }
        }
    }
}

private fun pillarRoom(map: Matrix2d<Cell>, room: Rect, spacing: Int, column: TileType, floor: TileType, edge: TileType? = null) {
    ((room.x1)..(room.x2)).forEach { x ->
        ((room.y1)..(room.y2)).forEach { y ->

            map[x, y].tileType =
                    if (edge != null && (x == room.x1 || x == room.x2 || y == room.y1 || y == room.y2)) {
                        edge
                    } else {
                        val center = room.center()


                        val hori = if (x == center.x || (x % 2 == 1 && x == center.x + 1)) {
                            false
                        } else {
                            if (x < center.x) {
                                (x - room.x1) % spacing == 0
                            } else {
                                (room.x2 - x) % spacing == 0
                            }
                        }

                        val vert = if (y == center.y || (y % 2 == 1 && y == center.y + 1)) {
                            false
                        } else {
                            if (y < center.y) {
                                (y - room.y1) % spacing == 0
                            } else {
                                (room.y2 - y) % spacing == 0
                            }
                        }

                        if (vert && hori) column else floor

                    }
        }
    }
}