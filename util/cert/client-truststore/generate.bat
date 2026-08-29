@echo off

IF "%1"=="" (
	SET myworkdir=%CD%
) ELSE (
	SET myworkdir=%~dpf1
)

echo working dir: %myworkdir%

set JAVA_HOME=c:\portable\jdk-21.0.6

set CERTPATH=%JAVA_HOME%\lib\security\cacerts
set STORENAME=%myworkdir%\client-truststore.jks
set STOREPASS=changeit
set CERTFILE_LOCALHOST=%myworkdir%\..\server_keystore.jks

IF EXIST %STORENAME% DEL /F /Q %STORENAME% 

rem ======= Global CA certificates
echo *** Importing cacerts.jks
"%JAVA_HOME%\bin\keytool.exe" -importkeystore -srckeystore "%CERTPATH%" -destkeystore %STORENAME% -storepass %STOREPASS% -srcstorepass %STOREPASS%

rem ======= Export self signed certificate =====================
	
"%JAVA_HOME%\bin\keytool.exe" -exportcert -keystore "%CERTFILE_LOCALHOST%" -alias localhost -file "%myworkdir%\localhost-cert.cer" -storepass %STOREPASS%
	
rem ======= Import self signed certificate =====================
echo *** Importing self-signed certificate for localhost
"%JAVA_HOME%\bin\keytool.exe" -importcert -keystore %STORENAME% -alias localhost -file "%myworkdir%\localhost-cert.cer" -storepass %STOREPASS% -noprompt
