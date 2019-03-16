package info.krasnoff.bulletin

import info.krasnoff.bulletin.board.controllers.HomeController
import org.slf4j.LoggerFactory
import spark.Spark.staticFiles
import spark.Spark.get

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