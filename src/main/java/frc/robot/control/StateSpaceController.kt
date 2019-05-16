package frc.robot.control

class StateSpaceController(val numInputs: Int,
                           val numOuputs: Int,
                           val numStates: Int,
                           val gains: StateSpaceGains,
                           private var X: Matrix,
                           private var XHat: Matrix,
                           private var U: Matrix,
                           private var Y: Matrix,
                           private var R: Matrix) {

    init {
        assert(gains.A.height == numStates)
        assert(gains.A.width == numStates)

        assert(gains.B.height == numStates)
        assert(gains.B.width == numInputs)

        assert(gains.C.height == numOuputs)
        assert(gains.C.width == numStates)

        assert(gains.D.height == numOuputs)
        assert(gains.D.width == numInputs)

        assert(gains.UMax.height == numOuputs)
        assert(gains.UMax.width == 1)

        assert(gains.UMin.height == numOuputs)
        assert(gains.UMin.width == 1)

        assert(X.height == numStates)
        assert(X.width == 1)

        assert(XHat.height == numStates)
        assert(XHat.width == 1)

        assert(U.height == numInputs)
        assert(U.width == 1)

        assert(Y.height == numOuputs)
        assert(Y.width == 1)
    }

    val state: Matrix
        get() = X.copy()

    val predictedState: Matrix
        get() = XHat.copy()

    val output: Matrix
        get() = Y.copy()

    val input: Matrix
        get() = U.copy()

    var reference: Matrix
        get() = R
        set(r) {
            assert(r.height == R.height)
            assert(r.width == R.width)

            R = r
        }

    private fun update() {
        updatePlant()
        correctObserver()
        updateController()
        predictObserver()
    }

    private fun updatePlant() {
        X = gains.A * X + gains.B * U
        Y = gains.C * X + gains.D * U
    }

    private fun correctObserver() {
        XHat += gains.Kalman * (Y - gains.C * XHat - gains.D * U)
    }

    private fun updateController() {
        assert(R.height == numStates)
        assert(R.width == 1)

        U = gains.K * (R - X) + gains.Kff * (R - gains.A * R)
        U.clip(gains.UMin, gains.UMax)
    }

    private fun predictObserver() {
        XHat = gains.A * XHat + gains.B * U
    }
}