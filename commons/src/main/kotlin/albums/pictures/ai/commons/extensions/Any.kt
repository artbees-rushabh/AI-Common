package albums.pictures.ai.commons.extensions

// extensions used mostly at importing app settings for now
fun Any.toBoolean() = toString() == "true"

fun Any.toInt() = Integer.parseInt(toString())

fun Any.toStringSet() = toString().split(",".toRegex()).toSet()
