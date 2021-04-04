# Paralin

A library for Javalin framework to extract HTTP params formatted and validated


## Usage

```kotlin

// Extract email from query or null as String?
val email = Paralin.with(ctx).query("email").asStringOrNull()
// Extract email from query or default as Int
val uid = Paralin.with(ctx).form("uid").match("\\d+").default("0").asInt()
// Extract value transfer it into an enum and throw exc in case something goes wrong
val enum = Paralin.with(ctx).form("day").throwExc(CustomExc::class.java).default("SUNDAY").asEnum(WeekEnum::class.java)
// Header e.g. Authorization: Bearer <JWT TOKEN>
val jwt = Paralin.with(ctx).header("Authorization").split(" ", 1).verify { param -> jwtUtil.isValid(param) }.asString()
```

* asStringOrNull(),similar functions, or if a default value set never throw an exception
* The default exception you get is ParamExc with the following messages:
  * missing
  * invalid-size
  * invalid-value
  * empty
* Your custom exception class should accept a String message as a parameter e.g. `class MyExc(msg: String): Exception(msg)`
* Split function deosn't perform on default value if param is empty
* Make sure you're enum class values are full capital
* Best way to handle the common exception is to use 
```kotlin
// Or your custom exception
javalin.exception(ParamExc::class.java) { exc, ctx ->
  when (exc.message) {
    "missing" -> ctx.status(400)
    ...
  }
}
```


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## License
[MIT](https://choosealicense.com/licenses/mit/)

