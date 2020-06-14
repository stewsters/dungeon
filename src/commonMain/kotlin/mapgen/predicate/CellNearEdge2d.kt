package mapgen.predicate

import math.Matrix2d

class CellNearEdge2d<T> : CellPredicate2d<T> {
    override fun belongs(generatedMap2d: Matrix2d<T>, x: Int, y: Int): Boolean {
        return x == 0 || y == 0 || x >= generatedMap2d.xSize - 1 || y >= generatedMap2d.ySize - 1
    }
}