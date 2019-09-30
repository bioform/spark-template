package info.krasnoff.bulletin.board.controllers.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import spark.Request
import spark.Response
import java.io.FileReader
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.gson.Gson
import info.krasnoff.bulletin.dao.UserDao
import info.krasnoff.bulletin.domains.User
import info.krasnoff.bulletin.domains.UserEntity


object GoogleAuthController {
    val REDIRECT_URI = ""
    // Set path to the Web application client_secret_*.json file you downloaded from the
    // Google API Console: https://console.developers.google.com/apis/credentials
    // You can also find your Web application client ID and client secret from the
    // console and specify them directly when you create the GoogleAuthorizationCodeTokenRequest
    // object.
    val CLIENT_SECRET_FILE = "./config/client_secret.json"

    fun login(request: Request, response: Response): String {
        // (Receive authCode via HTTPS POST)


        if (request.headers("X-Requested-With") == null) {
            // Without the `X-Requested-With` header, this request could be forged. Aborts.
            return ""
        }

        val idTokenString = Gson().fromJson(request.body(), GoogleTokenRequest::class.java).idToken

        // Exchange auth code for access token
        val clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), FileReader(CLIENT_SECRET_FILE))

        val transport = NetHttpTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()

        val verifier = GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(listOf(clientSecrets.getDetails().getClientId()))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build()

        val idToken = verifier.verify(idTokenString)

        val payload = idToken.getPayload()
        val userId = payload.getSubject()  // Use this value as a key to identify a user.
        val email = payload.getEmail()
        val emailVerified = java.lang.Boolean.valueOf(payload.getEmailVerified())
        val name = payload.get("name") as String
        val pictureUrl = payload.get("picture") as String
        val locale = payload.get("locale") as String
        val familyName = payload.get("family_name") as String
        val givenName = payload.get("given_name") as String

        return updateUser(email).email
    }

    // TODO use auto sign up service instead
    private fun updateUser(email: String):User {
        return UserDao.findOrCreateByEmail(email)
    }
}
