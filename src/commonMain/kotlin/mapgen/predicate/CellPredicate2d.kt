package mapgen.predicate

import math.Matrix2d


interface CellPredicate2d<T> {
    fun belongs(generatedMap2d: Matrix2d<T>, x: Int, y: Int): Boolean
}