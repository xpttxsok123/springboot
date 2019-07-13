# oauth2

## Desc

```
The authorization code grant is used when an application exchanges an authorization code for an access token. After the user returns to the application via the redirect URL, the application will get the authorization code from the URL and use it to request an access token. This request will be made to the token endpoint.
```



## Roles

```

  
     OAuth defines four roles:
  
     resource owner
        An entity capable of granting access to a protected resource.
        When the resource owner is a person, it is referred to as an
        end-user.
  
     resource server
        The server hosting the protected resources, capable of accepting
        and responding to protected resource requests using access tokens.
  
     client
        An application making protected resource requests on behalf of the
        resource owner and with its authorization.  The term "client" does
        not imply any particular implementation characteristics (e.g.,
        whether the application executes on a server, a desktop, or other
        devices).
  
     authorization server
        The server issuing access tokens to the client after successfully
        authenticating the resource owner and obtaining authorization.
```



## authorization code

```



     +----------+
     | Resource |
     |   Owner  |
     |          |
     +----------+
          ^
          |
         (B)
     +----|-----+          Client Identifier      +---------------+
     |         -+----(A)-- & Redirection URI ---->|               |
     |  User-   |                                 | Authorization |
     |  Agent  -+----(B)-- User authenticates --->|     Server    |
     |          |                                 |               |
     |         -+----(C)-- Authorization Code ---<|               |
     +-|----|---+                                 +---------------+
       |    |                                         ^      v
      (A)  (C)                                        |      |
       |    |                                         |      |
       ^    v                                         |      |
     +---------+                                      |      |
     |         |>---(D)-- Authorization Code ---------'      |
     |  Client |          & Redirection URI                  |
     |         |                                             |
     |         |<---(E)----- Access Token -------------------'
     +---------+       (w/ Optional Refresh Token)
```



## Doc

  https://tools.ietf.org/html/rfc6749#section-1.2

  https://developer.okta.com/blog/2018/04/10/oauth-authorization-code-grant-type

  https://www.oauth.com/oauth2-servers/access-tokens/authorization-code-request/



