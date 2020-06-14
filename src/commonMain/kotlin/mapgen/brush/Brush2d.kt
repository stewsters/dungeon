package mapgen.brush


import math.Matrix2d


interface Brush2d<T> {
    fun draw(generatedMap2d: Matrix2d<T>, x: Int, y: Int)
}