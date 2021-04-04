package dev.devura.paralin

/**
 * Options for extracting param
 */
interface ParalinOptionsBuilder {
    /**
     * Exactly matches the (trimmed) param to the specified regex
     * @param [regex] string pattern to be used as regex e.g. ^[a-z]{3}$ (three letters only a-z)
     * @return [ParalinOptionsBuilder]
     */
    fun match(regex: String): ParalinOptionsBuilder

    /**
     * Accepts a block and gives it the (trimmed) param to go through verification
     * @param [block] A function that accepts String as parameter and returns boolean
     * @return [ParalinOptionsBuilder]
     */
    fun verify(block: (String) -> Boolean): ParalinOptionsBuilder

    /**
     * Sets a default value to be used when param is empty
     * @param [value] the default value
     * @return [ParalinOptionsBuilder]
     */
    fun default(value: String): ParalinOptionsBuilder

    /**
     * Splits the param into multiple segments and sets extraction from specified index
     * If default value is set and used it won't go through the operation
     *
     * @param [pattern] the pattern to use when splitting
     * @param [index] the index to use when choosing splinted segments
     * @return [ParalinOptionsBuilder]
     */
    fun split(pattern: String, index: Int): ParalinOptionsBuilder

    /**
     * Specifies the length of param should be exactly as [n]
     * @param [n] size of param
     * @return [ParalinOptionsBuilder]
     */
    fun length(n: Int): ParalinOptionsBuilder

    /**
     * Specifies the length of param should be in range [from] (inclusive) to [to] (exclusive)
     * @param [n] size of param
     * @return [ParalinOptionsBuilder]
     */
    fun range(from: Int, to: Int): ParalinOptionsBuilder

    /**
     * Specify the exception to be thrown when something goes wrong
     *
     * An exception is not thrown if you request a nullable param
     *
     * @return [ParalinOptionsBuilder]
     */
    fun<T : Exception> throwExc(exc: Class<T>): ParalinOptionsBuilder

    /**
     * Specifies a [value] that should be part of or matches the param
     *
     * @param [value] the value to be checked if exists inside the param
     * @return [ParalinOptionsBuilder]
     */
    fun contains(value: String): ParalinOptionsBuilder

    /**
     * Return a [String] representation of param or throws an exception with following messages:
     * - "missing"
     * - "invalid-size"
     * - "invalid pattern"
     * - "empty"
     *
     * @throws [ParalinExc] or the one have been set via [throwExc]
     */
    fun asString(): String

    /**
     * Return a [String] representation of param or null
     * No exception is thrown
     */
    fun asStringOrNull(): String?

    /**
     * Return a [Int] representation of param or throws an exception with following messages:
     * - "missing"
     * - "invalid-size"
     * - "invalid pattern"
     * - "empty"
     *
     * @throws [ParalinExc] or the one have been set via [throwExc]
     */
    fun asInt(): Int

    /**
     * Return a [Int] representation of param or null
     * No exception is thrown
     *
     * @return [Int]
     */
    fun asIntOrNull(): Int?

    /**
     * Return a [Double] representation of param or throws an exception with following messages:
     * - "missing"
     * - "invalid-size"
     * - "invalid pattern"
     * - "empty"
     *
     * @throws [ParalinExc] or the one have been set via [throwExc]
     */
    fun asDouble(): Double

    /**
     * Return a [Double] representation of param or null
     * No exception is thrown
     *
     * @return [Double]
     */
    fun asDoubleOrNull(): Double?

    /**
     * Returns param as specified [Enum]
     * Automatically converts param to uppercase to match constant name
     *
     * @param [enumClass] Enum class to find value from
     */
    fun<T : Enum<T>> asEnum(enumClass: Class<T>): T

    /**
     * Returns param as specified [Enum] or null
     * Automatically converts param to uppercase to match constant name
     *
     * @param [enumClass] Enum class to find value from
     */
    fun<T : Enum<T>> asEnumOrNull(enumClass: Class<T>): T?

    fun<T> asJsonObj(objClass: Class<T>): T

    fun<T> asJsonObjOrNull(objClass: Class<T>): T?

}