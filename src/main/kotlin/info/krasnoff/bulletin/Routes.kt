package info.krasnoff.bulletin

import info.krasnoff.bulletin.board.controllers.HomeController
import info.krasnoff.bulletin.config.auth.AuthConfigFactory
import org.slf4j.LoggerFactory
import spark.Spark.staticFiles
import spark.Spark.get
import spark.Spark.post
import org.pac4j.sparkjava.CallbackRoute
import spark.Route
import org.pac4j.sparkjava.LogoutRoute




object Routes {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    init {
        staticFiles.location("/public")

        get("/api/hello", HomeController::index)
    }

    fun init(){
        log.debug("Routes were loaded")
    }
}