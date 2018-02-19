# BDD_AEM_REPO
This repository is for automation of AEM Sites/Assets using Cucumber BDD

Pre-Requisites :
----------------
-JAVA 8 : Download link http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

-Set JAVA_HOME path

-Add JAVA_HOME to path variable

-Maven : Download maven https://maven.apache.org/download.cgi

-Set M2_HOME path

-Add M2_HOME to path variable


Folder Structure:
-----------------

Below section explains the folder structure and its usage:

	Project
  			|
  			|
  			src
     			 |
   	 			 |
   	  			 test
   	    			|
   	    			| 
   	    			java
     	  				|
     	   				|
     	   				application
           						  |
           						  |
           						  RunCukesTest.java (used for sequential execution of tests. This file would be used by cuke profile in pom.xml)
           						  |
           						  |
           						  teststeps (This folder would have all the java step definitions matching the behavior statements specified in feature files)
        			|
        			|
        			lib (This folder has sikuli jar used for automation)
       				|
        			|
        			resources
       	   					|
       	   					|
       	   					config (has .properties file for different environments. This would be picked up during run time as pecified by the execution command)
       	  				 	|
           					|
       	   					features (this folder has all the .feature files which explain the behavior of the application under test)
       	   					|
       	   					|
           					testdata (this folder has all the testdata required for the test. Here .txt,json,xml,.xls,etc files which would be used as testdata would be stored)
      			| 
      			|
      			main
        		 	 |
        		  	 |
        		  	 java
           			 	|
           			 	|
           			 	automation
              				 	 |
                              	 |
              				     ConfigurationRead
                     						     |
                    						     |
                    						     ConfigurationReader.java (Reads the .properties file from config folder)
           				|
           				|
           			    browsers(this folder has .java files which configures each browser)
              				    |
              				    |
              			    	BrowserCinfigurator.java (Configures the specified driver as per the browser specifed)
           			    |
           			    |
           			    com
              				    |
              				    |
              			    	aem
                 			       |
                 			       |
                 			       application
                 			                 |
                 			                 |
                                             constants
                                             |
                                             |
                                             genericutilities (This folder has java files related to commonfunction,commonexception,and loging)                       
           			   |
           			   |
           			   pages (This folder would have all the Pages of application under test. Page Object Model approach is followed)
            		       |
             			   |
             			   BasePage.java(this class is the base class which would have all the necessary methods and configiration which would be inherited by other page classes)  
        	         |
                 	 |
        	        resources 
           			        |
           		      	    |
           			        com
             		           |
             		           |
             		           aem
              			         |
              			         |
               			         objectRepository
                     					        |
                     					        |
                     					        ObjectRepository.properties(If Page object is not followed then this file would have all the locators used in test)          
  			|
    		|
            browserdrivers (this folder would have all the required browser drivers for automation)
            |
            |
            cucumber-html-reports (this folder would have OOTB generate cucumber html report)
            |
            |
            Latest Result (This folder would have the extent report of the current test execution)
            |
            |
            logs (this folder would have the logs of the current test execution)
            |
            |
            supporting_jar (this has some of the jars which are required for test execution)
            |
            |
            target (this folder would have .class files)
            |
            |
            pom.xml (This is where the profiles,dependencies etc are defined)
            |
            |
            Run.bat (this would be used by jenkins,users for test execution)

Environment Specification:
----------------------------
All environment related configuration are to be stored under \BDD_AEM_REPO\src\test\resources\config\ in respective properties files.

Druning runtime based on the environment specified the appropriate properties file is picked by ConfigurationReader.java and used in execution.


Execution:
-----------

There are 2 profiles in POM.XML which controls the execution:

1)Cuke: This profile is used to execute test in sequential manner

In the below example :
"-Pcuke" is the profile used which is configured such that it executes the test in verify phase of maven build cycle
"-Denv" is used to specify the targeted execution environment
"-Dcucumber.opts="--tags @wip"" is used to specify the tags which are used for filtering (optional : Tags to be executed would be picked from pom.xml)

Example:
Command: mvn clean verify -Pcuke -Denv=stage

Report:
Once the execution is completed reports are dumped at : \BDD_AEM_REPO\ cucumber-html-reports and \BDD_AEM_REPO\LatestResult

2)Parallel: This profile is used to execute test in parallel manner

Note : No of threads to run in parallel is configured as per <acceptance.test.parallel.count>2</acceptance.test.parallel.count>

In the below example :
"-Pparallel-cuke" is the profile used which is configured such that it executes the test in verify phase of maven build cycle. This is used to test the integration tests only.
"-Denv" is used to specify the targeted execution environment
"-Dcucumber.opts="--tags @wip"" is used to specify the tags which are used for filtering  (optional : Tags to be executed would be picked from pom.xml)

Example:
Command: mvn clean verify -Pparallel-cuke -Denv=stage

Report:
Once the execution is completed individual reports of the Threads are dumped at : BDD_AEM_REPO\target\cucumber-parallel\

Logs:
----
Log4j is used in the framework and it is used to log the execution messages. The log messages can be found under /logs folder

GrowlNotification.java is used to add the execution messages(INFO,WARNING,SUCCESS,ERROR) on screen during runtime. This can be used for debugging.

Selenium Version:
-----------------
To support the framework with different version of selenium webdriver a switch is in place.
global.properties has selenium_version. This key would have the version of the selenium(need to be used only we are using selenium 3 or above).If this is the case then geckodriver would be used
If the selenium version is below 3 the webdriver is initialized in reqular way. 

Video Recording of Selenium Scripts Execution:
-----------------

The Monte media library is a Java library for processing media data. Supported media formats include still images, video, audio and meta-data. We have used this external service to record test execution screen video.
Video will be recorded only for the failed test cases and every time when user runs file the previously saved test videos will be removed and destination folder will get auto updated with latest test result video-recordings.

Video’s naming format is given below
Scenario outline_Date of test execution_Time of test run.avi



