# Paralin

A library for Javalin framework to extract HTTP params formatted and validated


## Usage

```kotlin

// Extract email from query or null as String?
val email = Paralin.with(ctx).query("email").asStringOrNull()
// Extract email from query or default as Int
val uid = Paralin.with(ctx).form("uid").match("\\d+").default("0").asInt()
// Extract value transfer it into an enum and throw exc in case something goes wrong
val enum = Paralin.with(ctx).form("day").throwExc(NoEnumExc::class.java).asEnum(WeekEnum::class.java)
// Header e.g. Authorization: Bearer <JWT TOKEN>
val jwt = Paralin.with(ctx).header("Authorization").split(" ", 1).verify { param -> jwtUtil.isValid(param) }.asString()
```

* asStringOrNull(),similar functions, or if a default value set never throw an exception
* The default exception you get is ParamExc with the following messages:
  * missing (no param found)
  * invalid-size (not in range or same as specified length)
  * invalid-value (pattern did not match)
  * empty (param is empty)
* Your custom exception class should accept a String message as a parameter e.g. `class MyExc(msg: String): Exception(msg)`
* Split function doesn't perform on default value if param is empty
* Make sure you're enum class values are full capital
* All params are trimmed automatically
* The best way to handle the common exception is to use 
```kotlin
// Or your custom exception
javalin.exception(ParamExc::class.java) { exc, ctx ->
  when (exc.message) {
    "missing" -> ctx.status(400)
    ...
  }
}
```

## Installation

Use Jitpack to install the latest version
https://jitpack.io/
(You need to copy the GitHub address and get the latest version for Gradle, Maven, ...)

```bash
repositories {
  ...
  maven { url 'https://jitpack.io' }
}
dependencies {
  implementation 'com.github.abstract-arrow:Paralin:<version>'
}
# latest version currently is 0.1.1
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## License
[MIT](https://choosealicense.com/licenses/mit/)

