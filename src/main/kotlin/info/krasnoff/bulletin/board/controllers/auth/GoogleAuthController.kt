package info.krasnoff.bulletin.board.controllers.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import spark.Request
import spark.Response
import java.io.FileReader
import com.google.gson.Gson
import java.util.Collections
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken






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
        }

        val idTokenString = Gson().fromJson(request.body(), TokenRequest::class.java).token

        // Exchange auth code for access token
        val clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), FileReader(CLIENT_SECRET_FILE))
        val transport = NetHttpTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        // JsonParser jsonParser = jsonFactory.createJsonParser(nwkJsonString);
        //        Network netwrkOut = jsonParser.parse(Network.class);

        val verifier = GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(listOf(clientSecrets.getDetails().getClientId()))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build()

        val idToken = verifier.verify(idTokenString)

        /*
        val tokenResponse = GoogleAuthorizationCodeTokenRequest(
                NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                "https://oauth2.googleapis.com/token",
                clientSecrets.getDetails().getClientId(),
                clientSecrets.getDetails().getClientSecret(),
                authCode,
                REDIRECT_URI)  // Specify the same redirect URI that you use with your web
                               // app. If you don't have a web version of your app, you can
                               // specify an empty string.
                .execute()

        val accessToken = tokenResponse.getAccessToken()

        // Use access token to call API
        val credential = GoogleCredential().setAccessToken(accessToken)

        // Get profile info from ID token
        val idToken = tokenResponse.parseIdToken()
        */
        val payload = idToken.getPayload()
        val userId = payload.getSubject()  // Use this value as a key to identify a user.
        val email = payload.getEmail()
        val emailVerified = java.lang.Boolean.valueOf(payload.getEmailVerified())
        val name = payload.get("name") as String
        val pictureUrl = payload.get("picture") as String
        val locale = payload.get("locale") as String
        val familyName = payload.get("family_name") as String
        val givenName = payload.get("given_name") as String

        return email
    }
}
