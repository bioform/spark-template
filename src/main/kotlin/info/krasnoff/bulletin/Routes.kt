package info.krasnoff.bulletin

import info.krasnoff.bulletin.board.controllers.auth.GoogleAuthController
import info.krasnoff.bulletin.board.controllers.HomeController
import org.slf4j.LoggerFactory
import spark.Spark.staticFiles
import spark.Spark.get
import spark.Spark.post

object Routes {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    init {
        staticFiles.location("/public")

        get("/api/hello", HomeController::index)
        post("/api/auth/google/login", GoogleAuthController::login)
    }

    fun init(){
        log.debug("Routes were loaded")
    }
}