import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {
    private const val secret = "SecretSecret"
    private val algorithm = Algorithm.HMAC512(secret)
    private const val validityInMs = 3600000

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .build()

    fun createToken(login: String, password: String): String = JWT.create()
        .withClaim("login", login)
        .withClaim("pwdHash", password)
        .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
        .sign(Algorithm.HMAC256(secret))
}