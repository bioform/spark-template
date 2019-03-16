package info.krasnoff.bulletin.board.controllers

import info.krasnoff.bulletin.dao.UserDao
import info.krasnoff.bulletin.jpa.User
import info.krasnoff.bulletin.jpa.UserEntity
import spark.Request
import spark.Response

object HomeController {
    fun index(req: Request, res: Response): String {
        val user = UserEntity()
        user.setName("New Name")
        UserDao.create(user)
        val someUser = UserDao.findOne(1)
        return someUser?.name ?: "Unknown"
    }
}