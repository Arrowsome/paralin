package dev.devura.paralin

class ParalinExc(msg: String) : Exception(msg)

object ParalinExcMsg {
    const val MISSING = "missing"
    const val INVALID_EMPTY = "empty"
    const val INVALID_SIZE = "invalid-size"
    const val INVALID_VALUE = "invalid-value"
}