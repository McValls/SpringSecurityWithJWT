# SpringSecurityWithJWT
Example of how to add JWT Filter to a Spring Security configuration

The WebAppInitializer class contains main configuration for deployment.

The SpringSecurityConfigurer class declares main Beans for the security to work and configure the request security.

The JwtAuthenticationFilter returns an Spring Authentication with the UserDetails obtained from the Token if authentication is successful.

The JwtAuthenticationProvider calls the JwtTokenUtils which retrieves the user from the Token.

************************
You can build the .war extension file of this example and deploy it in any servlet container.

There is a test class (JwtTokenTest) which generates a valid token to test via REST Client that you can successfully hit the
/helloWorld/sayHello endpoint but you can't hit /helloWorld/sayHelloAsAdmin.

You can play with this code and configure as you wish.

*************************
In Eclipse, import as Gradle STS Project.
