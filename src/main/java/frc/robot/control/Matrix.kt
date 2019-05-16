package frc.robot.control

class Matrix(val height: Int, val width: Int, initFunction: (Int) -> Double = { i -> 0.0 }) {
    private var data: DoubleArray = DoubleArray(height * width, initFunction)

    val indices: IntRange
        get() = data.indices

    val rows: IntRange
        get() = IntRange(0, height - 1)

    val cols: IntRange
        get() = IntRange(0, width - 1)

    operator fun get(row: Int, col: Int): Double {
        if (row >= height || col >= width) {
            throw IndexOutOfBoundsException()
        }

        return data[col + (row * width)]
    }

    operator fun get(i: Int): Double {
        return data[i]
    }

    operator fun set(row: Int, col: Int, d: Double) {
        if (row >= height || col >= width) {
            throw IndexOutOfBoundsException()
        }

        data[col + (row * width)] = d
    }

    operator fun set(i: Int, d: Double) {
        data[i] = d
    }

    private fun sameSize(m: Matrix): Boolean {
        return m.width == width && m.height == height
    }

    fun elementwise(f: (Double) -> Double): Matrix {
        return Matrix(height, width) { i -> f(this[i]) }
    }

    fun applyElementwise(f: (Double) -> Double) {
        for (i in data.indices) {
            this[i] = f(this[i])
        }
    }

    fun elementwise(m: Matrix, f: (Double, Double) -> Double): Matrix {
        return Matrix(height, width) { i -> f(this[i], m[i]) }
    }


    fun applyElementwise(m: Matrix, f: (Double, Double) -> Double) {
        if (!sameSize(m)) {
            throw IndexOutOfBoundsException()
        }

        for (i in indices) {
            this[i] = f(this[i], m[i])
        }
    }

    fun copy(): Matrix {
        return elementwise { d -> d }
    }

    operator fun plus(m: Matrix): Matrix {
        if (!sameSize(m)) {
            throw IndexOutOfBoundsException()
        }

        return elementwise(m) { a, b -> a + b }
    }

    operator fun minus(m: Matrix): Matrix {
        if (!sameSize(m)) {
            throw IndexOutOfBoundsException()
        }

        return elementwise(m) { a, b -> a - b }
    }

    operator fun times(m: Matrix): Matrix {
        if (this.width != m.height) {
            throw IndexOutOfBoundsException()
        }

        val result = Matrix(this.height, m.width)

        for (i in 0 until result.width) {
            for (j in 0 until result.height) {
                var tmp = 0.0

                for (p in 0 until this.width) {
                    tmp += m[p, i] * this[j, p]
                }

                result[j, i] = tmp
            }
        }

        return result
    }

    fun clip(lower: Matrix, upper: Matrix) {
        for (i in indices) {
            if (this[i] > upper[i]) {
                this[i] = upper[i]
            } else if (this[i] < lower[i]) {
                this[i] = lower[i]
            }
        }
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    override operator fun equals(other: Any?): Boolean {
        if (other !is Matrix) {
            return false
        }

        if (!sameSize(other)) {
            return false
        }

        return data.contentEquals(other.data)
    }
}