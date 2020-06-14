package game.world

import game.action.InProgress
import game.action.Succeeded
import math.Matrix2d
import math.getEuclideanDistance
import math.los

class World(
        val tiles: Matrix2d<Cell>,
        val entities: MutableList<Entity>,
        var player: Entity
) {

//    val actors = mutableListOf<Entity>()

    fun passTime() {

        // find the entity with an actor of the lowest time priority, do it if it can

        val nextEntity = entities.filter { it.ai != null }
                .minBy { it.ai!!.nextTurn() }

        if (nextEntity != null) {
            val action = nextEntity.ai?.getNextAction(nextEntity, this)
            nextEntity.ai?.setAction(null)
            if (action != null) {
                val result = action.onPerform(this, nextEntity)
                if (result == Succeeded || result == InProgress || !nextEntity.player) {
                    nextEntity.ai!!.delayTurn(100)
                    recalculateLight()
                }

            }

        }

    }

    fun recalculateLight() {
        // set all to false
        // find player, in radius make visible
        tiles.forEach { it -> it.lit = false }
        val players = entities.filter { it.player }
        players.forEach { player ->

            for (xD in (-10..10)) {
                for (yD in (-10..10)) {

                    val x = player.pos.x + xD
                    val y = player.pos.y + yD

                    if (tiles.contains(x, y)
                            && getEuclideanDistance(0.0, 0.0, xD.toDouble(), yD.toDouble()) < 10
                            && (los(player.pos.x, player.pos.y, x, y) { x, y -> !tiles[x, y].tileType.blocks }
                                    || los(x, y, player.pos.x, player.pos.y) { x, y -> !tiles[x, y].tileType.blocks })
                    ) {
                        tiles[x, y].lit = true
                        tiles[x, y].wasLit = true
                    }


                }
            }


            // for all points in square
            // if less than sight radius
            // if bresenham possible
            // mark as seen
        }
    }
}