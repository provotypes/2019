package frc.robot.control

class StateSpaceGains(val A: Matrix,
                      val B: Matrix,
                      val C: Matrix,
                      val D: Matrix,
                      val K: Matrix,
                      val Kff: Matrix,
                      val UMax: Matrix,
                      val UMin: Matrix,
                      val Kalman: Matrix)