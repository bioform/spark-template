package info.krasnoff.bulletin.dao

import info.krasnoff.bulletin.jpa.Models
import io.requery.cache.EntityCacheBuilder
/*
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
*/
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import org.h2.jdbcx.JdbcDataSource
import javax.sql.CommonDataSource


object Db {
    val JDBC_URL_TEMPLATE = "jdbc:h2:%s/db/bulletin_board;AUTO_SERVER=TRUE";
    //val sessionFactory: SessionFactory
    val dataStore: KotlinEntityDataStore<Any>

    init {
        val models = Models.DEFAULT
        val cache = EntityCacheBuilder(models)
                .useReferenceCache(false)
                .useSerializableCache(false)
                .build()
        val configuration = KotlinConfiguration(
                dataSource = dataSource(),
                model = models,
                statementCacheSize = 0,
                useDefaultLogging = true
                //, cache = cache
        )
        this.dataStore = KotlinEntityDataStore<Any>(configuration)
    }

    fun dataSource(): CommonDataSource {
        val jdbcUrl = String.format(JDBC_URL_TEMPLATE, System.getProperty("user.dir"))
        val ds = JdbcDataSource()
        ds.setURL(jdbcUrl)
        ds.user = "sa"
        ds.password = ""

        return ds
    }

}