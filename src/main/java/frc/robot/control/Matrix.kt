package frc.robot.control

/**
 * A class to represent arbitrary matrices of doubles.  Primarily used to
 * facilitate state space control.
 *
 * @author Brennon Brimhall <brennonbrimhall@provotypes.org
 * @property height the number of rows
 * @property width the number of cols
 * @param initFunction the function used to initialize the matrix
 * @property indices the valid one-dimensional indices of this matrix
 * @property rows the valid rows of this matrix
 * @property cols the valid cols of this matix
 */
class Matrix(val height: Int, val width: Int, initFunction: (Int) -> Double = { i -> 0.0 }) {

    /**
     * The array used to store the data.
     */
    private var data: DoubleArray = DoubleArray(height * width, initFunction)

    val indices: IntRange
        get() = data.indices

    val rows: IntRange
        get() = IntRange(0, height - 1)

    val cols: IntRange
        get() = IntRange(0, width - 1)

    /**
     * Retrieve the element at the given two-dimensional location in the matrix.
     */
    operator fun get(row: Int, col: Int): Double {
        if (row >= height || col >= width) {
            throw IndexOutOfBoundsException()
        }

        return data[col + (row * width)]
    }

    /**
     * Retrieve the element at the given one-dimensional location in the matrix.
     */
    operator fun get(i: Int): Double {
        return data[i]
    }

    /**
     * Set the element at the given two-dimensional location in the matrix to
     * the specified value.
     */
    operator fun set(row: Int, col: Int, d: Double) {
        if (row >= height || col >= width) {
            throw IndexOutOfBoundsException()
        }

        data[col + (row * width)] = d
    }

    /**
     * Set the element at the given one-dimensional location in the matrix to
     * the specified value.
     */
    operator fun set(i: Int, d: Double) {
        data[i] = d
    }

    /**
     * Perform matrix addition.  Throws an error if the provided matrix is not
     * the same size as this matrix.
     */
    operator fun plus(m: Matrix): Matrix {
        if (!sameSize(m)) {
            throw IndexOutOfBoundsException()
        }

        return elementwise(m) { a, b -> a + b }
    }

    /**
     * Perform matrix subtraction.  Throws an error if the provided matrix is
     * not the same size as this matrix.
     */
    operator fun minus(m: Matrix): Matrix {
        if (!sameSize(m)) {
            throw IndexOutOfBoundsException()
        }

        return elementwise(m) { a, b -> a - b }
    }

    /**
     * Perform matrix multiplication.  Throws an error if the provided matrix's
     * height does not equal this matrix's width.
     */
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

    /**
     * Compare this matrix to another object and determine if they are equal.
     */
    override operator fun equals(other: Any?): Boolean {
        if (other !is Matrix) {
            return false
        }

        if (!sameSize(other)) {
            return false
        }

        return data.contentEquals(other.data)
    }

    /**
     * Compare the dimensions of another matrix and determine if they are
     * equal to our dimensions.
     */
    private fun sameSize(m: Matrix): Boolean {
        return m.width == width && m.height == height
    }

    /**
     * Provide a hash code for the JVM.
     */
    override fun hashCode(): Int {
        return data.hashCode()
    }

    /**
     * Compute a new matrix by using the provided function to calculate each
     * element.
     */
    fun elementwise(f: (Double) -> Double): Matrix {
        return Matrix(height, width) { i -> f(this[i]) }
    }

    /**
     * Apply the given function in place to each element of this matrix.
     */
    fun applyElementwise(f: (Double) -> Double) {
        for (i in data.indices) {
            this[i] = f(this[i])
        }
    }

    /**
     * Compute a new matrix by using the provided secondary matrix and function
     * to calculate each element.
     */
    fun elementwise(m: Matrix, f: (Double, Double) -> Double): Matrix {
        return Matrix(height, width) { i -> f(this[i], m[i]) }
    }

    /**
     * Apply the given function in place to each element of this matrix, using
     * the secondary matrix.
     */
    fun applyElementwise(m: Matrix, f: (Double, Double) -> Double) {
        if (!sameSize(m)) {
            throw IndexOutOfBoundsException()
        }

        for (i in indices) {
            this[i] = f(this[i], m[i])
        }
    }

    /**
     * Copy this matrix's data into a new matrix.
     */
    fun copy(): Matrix {
        return elementwise { d -> d }
    }

    /**
     * Clip the elements of this matrix so that the ith element of this matrix
     * is between the ith element of min and the ith element of max, inclusive.
     */
    fun clip(lower: Matrix, upper: Matrix) {
        if (!sameSize(lower)) {
            throw IndexOutOfBoundsException()
        }

        if (!sameSize(upper)) {
            throw IndexOutOfBoundsException()
        }

        for (i in indices) {
            if (this[i] > upper[i]) {
                this[i] = upper[i]
            } else if (this[i] < lower[i]) {
                this[i] = lower[i]
            }
        }
    }
}