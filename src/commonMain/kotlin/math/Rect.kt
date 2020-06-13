package math

class Rect(var x1: Int, var y1: Int, var x2: Int, var y2: Int) {
    fun center(): Vec2 {
        val center_x = (x1 + x2) / 2
        val center_y = (y1 + y2) / 2
        return Vec2(center_x, center_y)
    }

    fun intersect(other: Rect): Boolean {
        return x1 <= other.x2 && x2 >= other.x1 && y1 <= other.y2 && y2 >= other.y1
    }

    override fun toString(): String {
        return "$x1 $y1 $x2 $y2"
    }

    fun contains(x: Int, y: Int): Boolean {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2
    }

    fun onEdge(x: Int, y: Int): Boolean {
        return x == x1 || x == x2 || y == y1 || y == y2
    }
}