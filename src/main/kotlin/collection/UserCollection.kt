package collection

import DatabaseSingleton.Companion.getDb
import model.User
import org.litote.kmongo.coroutine.CoroutineCollection

class UserCollection: CollectionFactory<User>() {
    override val collection: CoroutineCollection<User>
        get() = getDb().getCollection()
}