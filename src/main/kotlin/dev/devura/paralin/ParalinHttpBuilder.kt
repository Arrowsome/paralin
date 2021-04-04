package dev.devura.paralin

interface ParalinHttpBuilder {
    /**
     * Http POST body param in x-www-form-urlencoded form
     * @param [key] the name of form body to be extracted
     * @return [ParalinOptionsBuilder]
     */
    fun form(key: String): ParalinOptionsBuilder
    /**
     * Http GET param
     * @param [key] the name of query to be extracted
     * @return [ParalinOptionsBuilder]
     */
    fun query(key: String): ParalinOptionsBuilder
    /**
     * Http header param
     * @param [key] the name of header to be extracted
     * @return [ParalinOptionsBuilder]
     */
    fun header(key: String): ParalinOptionsBuilder
}