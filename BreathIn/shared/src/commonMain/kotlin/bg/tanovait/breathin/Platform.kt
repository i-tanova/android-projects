package bg.tanovait.breathin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform