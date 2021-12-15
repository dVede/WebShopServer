import api.itemRouting
import auth.authRouting
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import collection.CollectionFactory
import collection.ItemsCollection
import collection.UserCollection
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.tomcat.*
import model.AuthData
import model.Item
import model.User
import org.slf4j.LoggerFactory
import java.util.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    (LoggerFactory.getILoggerFactory() as LoggerContext).getLogger("org.mongodb.driver").level =
        Level.OFF
    install(ContentNegotiation) {
        json()
    }
    install(Authentication) {
        jwt("validate") {
            verifier(JwtConfig.verifier)
            validate { jwtCredential ->
                val login = jwtCredential.payload.getClaim("login").asString()
                val pwdHash = jwtCredential.payload.getClaim("pwdHash").asString()
                val time = jwtCredential.payload.expiresAt
                if (login != null && pwdHash != null && time.time > System.currentTimeMillis()) {
                    AuthData(login, pwdHash)
                } else {
                    null
                }
            }
        }
    }
    routing {
        itemRouting(ItemsCollection())
        authRouting(UserCollection())
    }
}



