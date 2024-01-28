Password: hi!@nW8B*N$9e6S

Microsoft Windows [Versión 10.0.19044.3803]
(c) Microsoft Corporation. Todos los derechos reservados.

C:\WINDOWS\system32>choco install okta
Chocolatey v1.1.0
Installing the following packages:
okta
By installing, you accept licenses for the packages.

okta v0.10.0 [Approved]
okta package files install completed. Performing other installation steps.
The package okta wants to run 'chocolateyinstall.ps1'.
Note: If you don't run this script, the installation will fail.
Note: To confirm automatically next time, use '-y' or consider:
choco feature enable -n allowGlobalConfirmation
Do you want to run the script?([Y]es/[A]ll - yes to all/[N]o/[P]rint): Y

Downloading okta 64 bit
  from 'https://github.com/okta/okta-cli/releases/download/okta-cli-tools-0.10.0/okta-cli-windows-0.10.0-x86_64.exe.zip'
Progress: 100% - Completed download of C:\Users\mmacho\AppData\Local\Temp\chocolatey\okta\0.10.0\okta-cli-windows-0.10.0-x86_64.exe.zip (13.51 MB).
Download of okta-cli-windows-0.10.0-x86_64.exe.zip (13.51 MB) completed.
Hashes match.
Extracting C:\Users\mmacho\AppData\Local\Temp\chocolatey\okta\0.10.0\okta-cli-windows-0.10.0-x86_64.exe.zip to C:\ProgramData\chocolatey\lib\okta\tools...
C:\ProgramData\chocolatey\lib\okta\tools
 ShimGen has successfully created a shim for okta.exe
 The install of okta was successful.
  Software installed to 'C:\ProgramData\chocolatey\lib\okta\tools'

Chocolatey installed 1/1 packages.
 See the log for details (C:\ProgramData\chocolatey\logs\chocolatey.log).

C:\WINDOWS\system32>okta register
First name: Manuel
Last name: Macho
Email address: manuelmachodelrio@gmail.com
Country: Spain
Creating new Okta Organization, this may take a minute:
An account activation email has been sent to you.

Check your email
New Okta Account created!
Your Okta Domain: https://dev-72720413.okta.com

C:\WINDOWS\system32>cd C:\Users\mmacho\eclipse-workspace-new\poc\okta-spring-cloud-sleuth-example-main

C:\Users\mmacho\eclipse-workspace-new\poc\okta-spring-cloud-sleuth-example-main>okta apps create
Application name [okta-spring-cloud-sleuth-example-main]:
Type of Application
(The Okta CLI only supports a subset of application types and properties):
> 1: Web
> 2: Single Page App
> 3: Native App (mobile)
> 4: Service (Machine-to-Machine)
Enter your choice [Web]:
Framework of Application
> 1: Okta Spring Boot Starter
> 2: Spring Boot
> 3: JHipster
> 4: Quarkus
> 5: Other
Enter your choice [Other]: 1
Redirect URI
Common defaults:
 Spring Security - http://localhost:8080/login/oauth2/code/okta
 Quarkus OIDC - http://localhost:8080/callback
 JHipster - http://localhost:8080/login/oauth2/code/oidc
Enter your Redirect URI(s) [http://localhost:8080/login/oauth2/code/okta]:
Enter your Post Logout Redirect URI(s) [http://localhost:8080/]:
Configuring a new OIDC Application, almost done:
Created OIDC application, client-id: 0oae8cp8uq7RcG5AL5d7

Okta application configuration has been written to: C:\Users\mmacho\eclipse-workspace-new\poc\okta-spring-cloud-sleuth-example-main\src\main\resources\application.properties

C:\Users\mmacho\eclipse-workspace-new\poc\okta-spring-cloud-sleuth-example-main>