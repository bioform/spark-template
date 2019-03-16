package info.krasnoff.bulletin.dao

import io.requery.sql.KotlinEntityDataStore

abstract class AbstractDao {
    protected val db: KotlinEntityDataStore<Any> = Db.dataStore
}