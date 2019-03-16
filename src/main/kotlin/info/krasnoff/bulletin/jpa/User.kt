package info.krasnoff.bulletin.jpa

import io.requery.*

@Entity
@Table(name = "users")
interface User : Persistable {
        @get:Key
        @get:Generated
        val id: Int
        @get:Column(nullable = false)
        val name: String
        @get:Column(nullable = true)
        val email: String
}