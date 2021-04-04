package dev.devura.paralin

import io.javalin.http.Context
import io.javalin.plugin.json.JavalinJson
import java.security.InvalidParameterException

open class Paralin private constructor(private val ctx: Context) : ParalinHttpBuilder, ParalinOptionsBuilder {
    private lateinit var http: ParalinHttp
    private lateinit var key: String
    private var matchPattern: String? = null
    private var verifyBlock: ((String) -> Boolean)? = null
    private var default: String? = null
    private var splitPattern: String? = null
    private var splitIndex: Int? = null
    private var length: Int? = null
    private var exc: Class<*>? = null
    private var range: Pair<Int, Int>? = null
    private var containsPattern: String? = null

    companion object {
        /**
         * Entry point for extracting http parameter
         *
         * @param [ctx] is a Javalin [Context] object
         * @return [ParalinHttpBuilder]
         */
        @JvmStatic
        fun with(ctx: Context): ParalinHttpBuilder = Paralin(ctx)
    }



    override fun header(key: String): ParalinOptionsBuilder = apply {
        this.http = ParalinHttp.HEADER
        this.key = key
    }

    override fun query(key: String): ParalinOptionsBuilder = apply {
        this.http = ParalinHttp.QUERY
        this.key = key
    }

    override fun form(key: String): ParalinOptionsBuilder = apply {
        this.http = ParalinHttp.FORM
        this.key = key
    }

    override fun match(regex: String): ParalinOptionsBuilder = apply{ this.matchPattern = regex }

    override fun verify(block: (String) -> Boolean): ParalinOptionsBuilder = apply{ this.verifyBlock = block  }

    override fun default(value: String): ParalinOptionsBuilder = apply{ this.default = value }

    override fun split(pattern: String, index: Int): ParalinOptionsBuilder = apply {
        this.splitPattern = pattern
        this.splitIndex = index
    }

    override fun length(n: Int): ParalinOptionsBuilder = apply { this.length = n }

    override fun range(from: Int, to: Int): ParalinOptionsBuilder = apply { this.range = Pair(from, to) }

    override fun contains(value: String): ParalinOptionsBuilder = apply{ this.containsPattern = value }

    override fun asString(): String {
        var param = (http(key) ?: default ?: throw genExc(ParalinExcMsg.MISSING, exc)).trim()
        if (param == "") throw genExc(ParalinExcMsg.INVALID_EMPTY, exc)

        if (splitPattern != null && splitIndex != null && param != default) {
            param = param.split(splitPattern!!)[splitIndex!!]
        }

        if (length != null) {
            val isLength = param.length == length
            if (!isLength) throw genExc(ParalinExcMsg.INVALID_SIZE, exc)
        }

        if (range != null) {
            val inRange = param.length >= range!!.first && param.length < range!!.second
            if (!inRange) throw genExc(ParalinExcMsg.INVALID_SIZE, exc)
        }

        if (containsPattern != null) {
            val contains = param.contains(containsPattern!!)
            if (!contains) throw genExc(ParalinExcMsg.INVALID_VALUE, exc)
        }

        if (verifyBlock != null) {
            if (!verifyBlock!!.invoke(param)) throw genExc(ParalinExcMsg.INVALID_VALUE, exc)
        }

        if (matchPattern != null) {
            val matches = Regex(matchPattern!!).matches(param)
            if (!matches) throw genExc(ParalinExcMsg.INVALID_VALUE, exc)
        }

        return param
    }

    override fun<T : Exception> throwExc(exc: Class<T>): ParalinOptionsBuilder = apply { this.exc = exc }

    override fun asInt(): Int = asString().toInt()

    override fun asStringOrNull(): String? {
        return try { asString() } catch(exc: Exception) { null }
    }

    override fun asIntOrNull(): Int? {
        return try { asInt() } catch (exc: Exception) { null }
    }

    override fun asDouble(): Double = asString().toDouble()

    override fun asDoubleOrNull(): Double? {
        return try {
            asString().toDoubleOrNull()
        } catch (exc: java.lang.Exception) {
            null
        }
    }

    override fun<T : Enum<T>> asEnum(enumClass: Class<T>): T {
        return java.lang.Enum.valueOf(enumClass, asString().toUpperCase())
    }

    override fun <T : Enum<T>> asEnumOrNull(enumClass: Class<T>): T? {
        return try {
            asEnum(enumClass)
        } catch (exc: Exception) { null }
    }

    override fun <T> asJsonObj(objClass: Class<T>): T {
        return JavalinJson.fromJson(asString(), objClass)
    }

    override fun <T> asJsonObjOrNull(objClass: Class<T>): T? {
        val param = asStringOrNull() ?: return null
        return try {
            JavalinJson.fromJson(param, objClass)
        } catch (exc: Exception) { null }
    }

    private fun http(key: String): String? = when (http) {
        ParalinHttp.HEADER -> ctx.header(key)
        ParalinHttp.QUERY -> ctx.queryParam(key)
        ParalinHttp.FORM -> ctx.formParam(key)
    }

    private fun genExc(msg: String, excClass: Class<*>? = null): Exception {
        val exc = excClass ?: ParalinExc::class.java
        return try {
            exc.getConstructor(String::class.java).newInstance(msg) as Exception
        } catch (exc: Exception) { throw InvalidParameterException() }
    }

}