package info.krasnoff.bulletin.dao

import info.krasnoff.bulletin.domains.User
import info.krasnoff.bulletin.domains.UserEntity
import io.requery.kotlin.eq

//import org.hibernate.testing.transaction.TransactionUtil.doInHibernate

object UserDao : AbstractDao() {

    fun findOne(id: Long): User? {
        return db.select(User::class).get().firstOrNull()
    }

    fun create(user: UserEntity):User {
        return db.insert(user)
    }

    fun findOrCreateByEmail(email: String): User {
        return db {
            var user = select(User::class).where(User::email eq email).get().firstOrNull()
            user ?: create(UserEntity().apply { setEmail(email) })
        }
    }

}