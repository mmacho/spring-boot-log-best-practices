# Spring Cloud Sleuth Example
 
This example app shows you how to use Spring Cloud Sleuth in a Spring Boot application secured with Okta and Okta's Spring Boot Starter.

Please read [Easy Distributed Tracing with Spring Cloud Sleuth][blog] to see how this app was created.

**Prerequisites:** 

- **Java 11**: This project uses Java 11. OpenJDK 11 will work just as well. Instructions are found on the [OpenJDK website](https://openjdk.java.net/install/). OpenJDK can also be installed using [Homebrew](https://brew.sh/). Alternatively, [SDKMAN](https://sdkman.io/) is another great option for installing and managing Java versions.
- **Okta CLI**: You’ll be using Okta as an OAuth/OIDC provider to add JWT authentication and authorization to the application. You can go to [our developer site](https://developer.okta.com) to learn more about Okta. You need a free developer account for this tutorial. The Okta CLI is an easy way to register for a free Okta developer account, or log in to an existing one, and configure a Spring Boot app to use Okta as an auth provider. The [project GitHub page](https://github.com/okta/okta-cli) has installation instructions.
- **HTTPie**: This is a powerful command-line HTTP request utility that you'll use to test the WebFlux server. Install it according to [the docs on their site](https://httpie.org/doc#installation).

> [Okta](https://developer.okta.com/) has Authentication and User Management APIs that reduce development time with instant-on, scalable user infrastructure. Okta's intuitive API and expert support make it easy for developers to authenticate, manage, and secure users and roles in any application.

* [Getting Started](#getting-started)
* [Create an Okta OIDC Application](#create-an-okta-oidc-application)
* [Start the Apps](#start-the-apps)
* [Links](#links)
* [Help](#help)
* [License](#license)

## Getting Started

To install this example application, run the following commands:

```bash
git clone https://github.com/oktadev/okta-spring-cloud-sleuth-example.git spring-cloud-sleuth
cd spring-cloud-sleuth
```

This will get a copy of the project installed locally. Before the projects apps will run, however, you need to create an OIDC application in Okta and configure the application to use it.

## Create an Okta OIDC Application

Install the [Okta CLI](https://cli.okta.com) and run `okta register` to sign up for a new account. If you already have an account, run `okta login`. 

In the directory you cloned, run `okta apps create`. Select the default app name, or change it as you see fit. Choose **Web** and press **Enter**. Select **Okta Spring Boot Starter** to continue.

This places the necessary values in the `src/main/resources/application.properties` file. 

```properties
okta.oauth2.issuer=https://{yourOktaDomain}/oauth2/default
okta.oauth2.client-secret={yourClientSecret}
okta.oauth2.client-id={yourClientId}
```

## Start the Apps

You will need to generate a JWT to test the application. To do this, you can use the [OIDC Debugger](https://oidcdebugger.com/). For full instructions, see the blog post associated with this project.

Once you have a token, store it in a shell variable and run a request using HTTPie.

```bash
TOKEN={your token value}
```

Start two instances of the application.

Service A:
```bash
APP_NAME="Service A" APP_PORT=8081 ./mvnw spring-boot:run
```

Service B:
```bash
APP_NAME="Service B" APP_PORT=8082 ./mvnw spring-boot:run
```

## Testing the Application

Make a request to service A endpoint `/a` using the JWT you just created.

```bash
http :8081/a "Authorization: Bearer $TOKEN"
```

If all went well, you'll see this.

```bash
HTTP/1.1 200 
...

Hello from /a - Service A, Hello from /b - Service B

```

## Links

This example uses the following open source libraries:

* [Okta Spring Boot Starter](https://github.com/okta/okta-spring-boot)
* [Spring Cloud Sleuth](https://spring.io/projects/spring-cloud-sleuth)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Security](https://spring.io/projects/spring-security)

## Help

Please post any questions as comments on the [blog post][blog], or visit our [Okta Developer Forums](https://devforum.okta.com/).

## License

Apache 2.0, see [LICENSE](LICENSE).

[blog]: https://developer.okta.com/blog/2021/07/26/spring-cloud-sleuth
