package game.world

import game.action.InProgress
import game.action.Succeeded
import math.Matrix2d

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
                }

            }

        }

    }
}