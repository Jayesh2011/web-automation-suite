set projectLocation=E:\GIT\automation-common
cd %projectLocation%
java -cp "%projectLocation%\resources\Libraries\selenium-server-standalone\selenium-server-standalone-2.53.1.jar;%projectLocation%\resources\Libraries\testng\testng-6.10.jar;%projectLocation%\resources\Libraries\jcommander\1.60\jcommander-1.60.jar;%projectLocation%\target\classes" -Dtestng.test.classpath="%projectLocation%\target" org.testng.TestNG ./src/java/com/zeuslearning/automation/unittests/testParallelExecution.xml
pause
