package info.krasnoff.bulletin.dao

import info.krasnoff.bulletin.jpa.User
import info.krasnoff.bulletin.jpa.UserEntity

//import org.hibernate.testing.transaction.TransactionUtil.doInHibernate

object UserDao : AbstractDao() {

    fun findOne(id: Long): User? {
        return db.select(User::class).get().firstOrNull()
    }

    fun create(user: UserEntity) {
        db.insert(user)
    }

}