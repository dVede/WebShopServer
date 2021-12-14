package collection

import com.mongodb.MongoWriteException
import org.bson.conversions.Bson
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineCollection

abstract class CollectionFactory<T : Any> {
    abstract val collection: CoroutineCollection<T>

    suspend fun add(member: T): Boolean =
        try {
            collection.insertOne(member)
            true
        } catch (ex: MongoWriteException) {
            false
        }

    suspend fun getAll(): List<T> =
        collection.find().toList()

    suspend fun getOne(vararg filters: Bson): T? =
        collection.findOne(and(*filters))

    suspend fun delete(vararg filters: Bson): Boolean =
        !collection.deleteOne(and(*filters)).wasAcknowledged()
}