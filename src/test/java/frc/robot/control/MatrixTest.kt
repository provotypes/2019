package frc.robot.control

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MatrixTest {

    @Test fun testInit() {
        val A = Matrix(1, 1) { i -> 1.0}

        assertTrue(A[0] == 1.0)
        assertTrue(A[0, 0] == 1.0)
    }

    @Test fun testSet() {
        val a = Matrix(1, 1) {i -> 1.0}

        a[0] = -1.0
        assertTrue(a[0] == -1.0)
    }

    @Test fun testElementwise() {
        val A = Matrix(4, 4) { i -> i.toDouble()}

        val B = A.elementwise { d -> -d }

        for (i in A.indices) {
            assertTrue(A[i] == -B[i])
        }

        A.applyElementwise { d -> -d }

        for (i in A.rows) {
            for (j in A.cols) {
                assertTrue(A[i, j] == B[i, j])
            }
        }

        val C = A.elementwise(B) { a, b -> a + b }

        for (i in C.indices) {
            assertTrue(C[i] == (A[i] + B[i]))
        }

        A.applyElementwise(B) { a, b -> a + b}

        for (i in A.rows) {
            for (j in A.cols) {
                assertTrue(A[i, j] == C[i, j])
            }
        }
    }

    @Test fun testOperators() {
        val A = Matrix(4, 4) { i -> i.toDouble()}
        val B = A.elementwise { d -> -d }

        var C = A + B

        for (i in C.indices) {
            assertTrue(C[i] == 0.0)
        }

        C = A - B

        for (i in C.indices) {
            assertTrue(C[i] == A[i] * 2)
        }

        C = A.copy()

        assertTrue(C == A)
    }

    @Test fun testMultiplication() {
        val A = Matrix(2, 3) { i ->
            when (i) {
                0 -> -3.0
                1 -> 1.0
                2 -> -1.0
                3 -> 4.0
                4 -> 0.0
                5 -> 2.0
                else -> 0.0
            }
        }

        val B = Matrix (3, 4) { i ->
            when (i) {
                0 -> 3.0
                1 -> 5.0
                2 -> 1.0
                3 -> -3.0
                4 -> -2.0
                5 -> -1.0
                6 -> 2.0
                7 -> 4.0
                8 -> 6.0
                9 -> -4.0
                10 -> 7.0
                11 -> 8.0
                else -> 0.0
            }
        }

        val C = A * B

        assertTrue(C.height == 2)
        assertTrue(C.width == 4)

        for (i in C.indices) {
            val expected = when(i) {
                0 -> -17.0
                1 -> -12.0
                2 -> -8.0
                3 -> 5.0
                4 -> 24.0
                5 -> 12.0
                6 -> 18.0
                7 -> 4.0
                else -> 0.0
            }

            assertEquals(expected, C[i])
        }
    }
}