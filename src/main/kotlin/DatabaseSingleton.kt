import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class DatabaseSingleton {
    companion object {
        private var database: CoroutineDatabase? = null

        fun getDb(): CoroutineDatabase {
            return database ?: KMongo.createClient().coroutine.getDatabase("shop")
        }
    }
}
