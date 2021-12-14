import collection.ItemsCollection
import collection.UserCollection
import api.itemRouting
import auth.authRouting
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.tomcat.*
import model.AuthData

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(Authentication) {
        jwt("validate") {
            verifier(JwtConfig.verifier)
            validate { jwtCredential ->
                val login = jwtCredential.payload.getClaim("login").asString()
                val pwdHash = jwtCredential.payload.getClaim("pwdHash").asString()
                AuthData(login, pwdHash)
            }
        }
    }
    routing {
        itemRouting(ItemsCollection())
        authRouting(UserCollection())
    }
}



