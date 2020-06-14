package mapgen

import com.soywiz.kds.Deque
import mapgen.brush.Brush2d
import mapgen.predicate.CellPredicate2d
import math.Matrix2d
import math.Vec2

object MapGen2d {
    /**
     * Fills an area with a border
     *
     * @param map  The map we are working on
     * @param fill The CellType we are filling the center with
     * @param wall The CellType we are filling the edge with
     */
    fun <T> fillWithBorder(map: Matrix2d<T>, fill: T, wall: T) {
        map.forEach { x, y ->
            if (x == 0 || y == 0 || x >= map.xSize - 1 || y >= map.ySize - 1) {
                map.set(x, y, wall)
            } else {
                map.set(x, y, fill)
            }
        }
    }

    fun <T> fill(map: Matrix2d<T>, predicate: CellPredicate2d<T>, brush2d: Brush2d<T>) {
        map.forEach { x, y ->
            if (predicate.belongs(map, x, y)) {
                brush2d.draw(map, x, y)
            }
        }
    }

    fun <T> fill(map: Matrix2d<T>, predicate: CellPredicate2d<T>, brush2d: (x: Int, y: Int) -> Unit) {
        map.forEach { x, y ->
            if (predicate.belongs(map, x, y)) {
                brush2d(x, y)
            }
        }
    }

    /**
     * Flood fills on things that fit the predicate
     *
     * @param map       The map we are working on
     * @param start     The beginning of the flood fille
     * @param predicate The predicate to check
     * @param brush2d   The brush to fill
     */
    fun <T> floodFill(map: Matrix2d<T>, start: Vec2, predicate: CellPredicate2d<T>, brush2d: Brush2d<T>) {
        val todo: Deque<Vec2> = Deque<Vec2>()
        val match: ArrayList<Vec2> = ArrayList()
        val done: HashSet<Vec2> = HashSet<Vec2>()
        todo.add(start)
        var p: Vec2
        while (todo.size > 0) {
            p = todo.removeFirst()
            if (!done.contains(p) && predicate.belongs(map, p.x, p.y)) {
                match.add(p)

                if (p.x > 0) todo.add(Vec2(p.x - 1, p.y))
                if (p.x < map.xSize - 1) todo.add(Vec2(p.x + 1, p.y))
                if (p.y > 0) todo.add(Vec2(p.x, p.y - 1))
                if (p.y < map.ySize - 1) todo.add(Vec2(p.x, p.y + 1))
            }
            done.add(p)
        }

        // Goes over the whole map replacing a cell satisfying the predicate with the brush contents.
        for (point2i in match) {
            brush2d.draw(map, point2i.x, point2i.y)
        }
    }
}