package info.krasnoff.bulletin.config.auth

import org.pac4j.core.authorization.authorizer.Authorizer
import org.pac4j.core.config.Config
import org.pac4j.core.config.ConfigFactory
import org.pac4j.oauth.client.Google2Client
import org.pac4j.oauth.config.OAuth20Configuration
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer
import org.pac4j.core.client.direct.AnonymousClient
import org.pac4j.core.client.Clients
import org.pac4j.core.matching.PathMatcher
import org.pac4j.core.profile.CommonProfile


class AuthConfigFactory(val salt: String) : ConfigFactory {
    override fun build(vararg parameters: Any?): Config {
        val google2Client = Google2Client()
        google2Client.setKey("527133725719-1n5sv5ko24hdr80ciju8au0gcj41quhi.apps.googleusercontent.com");
        google2Client.setSecret("3cmewFkmNruD1EdxTgjaDryx");
        google2Client.setCallbackUrl("http://localhost:4567/api/signin/callback");
        google2Client.setScope(Google2Client.Google2Scope.EMAIL_AND_PROFILE);
        google2Client.setAuthorizationGenerator({ ctx, profile ->
            profile.addRole("ROLE_ADMIN")
            profile
        })


        val clients = Clients("http://localhost:8080/callback", google2Client, AnonymousClient())

        val config = Config(clients)
        config.addAuthorizer("admin", RequireAnyRoleAuthorizer<CommonProfile>("ROLE_ADMIN"))
        //config.addMatcher("excludedPath", PathMatcher().excludeRegex("^/facebook/notprotected$"))
        return config
    }
}