$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("02_BookingMagnet.feature");
formatter.feature({
  "line": 2,
  "name": "Booking Magnet Component",
  "description": "As a guest user \r\nwhen i open the home page of aquasana\r\nbooking magnet , which is used as search bar is present already on page",
  "id": "booking-magnet-component",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@aquasana"
    },
    {
      "line": 1,
      "name": "@complete"
    },
    {
      "line": 1,
      "name": "@now"
    }
  ]
});
formatter.before({
  "duration": 332133869,
  "status": "passed"
});
formatter.before({
  "duration": 5898852948,
  "status": "passed"
});
formatter.scenario({
  "line": 8,
  "name": "Verify booking Magnet on homepage and perform a spa search",
  "description": "",
  "id": "booking-magnet-component;verify-booking-magnet-on-homepage-and-perform-a-spa-search",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 9,
  "name": "I am on the home page \"https://www.aquasana.co.uk\"",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "I see the Booking Magnet",
  "keyword": "When "
});
formatter.step({
  "line": 11,
  "name": "I select \"Whinfell Forest\" from Spa locations dropdown in booking magnet",
  "keyword": "And "
});
formatter.step({
  "line": 12,
  "name": "I select \"30\" date \"September\" month \"2017\" year from date dropdown in booking magnet",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "I select \"+/- 1 days\" as date variance and search in booking magnet",
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "I should see the Search Results Page for Spa days",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "https://www.aquasana.co.uk",
      "offset": 23
    }
  ],
  "location": "BookingMagnet.i_am_on_the_home_page_http_Homepage_html(String)"
});
formatter.result({
  "duration": 6648497214,
  "status": "passed"
});
formatter.match({
  "location": "BookingMagnet.i_should_be_able_to_see_sticky_Booking_Magnet()"
});
formatter.result({
  "duration": 94188792,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Whinfell Forest",
      "offset": 10
    }
  ],
  "location": "BookingMagnet.select_from_Spa_locations_dropdown(String)"
});
formatter.result({
  "duration": 9864267328,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "30",
      "offset": 10
    },
    {
      "val": "September",
      "offset": 20
    },
    {
      "val": "2017",
      "offset": 38
    }
  ],
  "location": "BookingMagnet.select_from_date_dropdown(String,String,String)"
});
formatter.result({
  "duration": 142127098173,
  "error_message": "org.openqa.selenium.ElementNotVisibleException: element not visible\n  (Session info: chrome\u003d60.0.3112.113)\n  (Driver info: chromedriver\u003d2.29.461591 (62ebf098771772160f391d75e589dc567915b233),platform\u003dWindows NT 6.1.7601 SP1 x86_64) (WARNING: The server did not provide any stacktrace information)\nCommand duration or timeout: 33 milliseconds\nBuild info: version: \u00272.53.0\u0027, revision: \u002735ae25b1534ae328c771e0856c93e187490ca824\u0027, time: \u00272016-03-15 10:43:46\u0027\nSystem info: host: \u0027SHKULKAR-W7-4\u0027, ip: \u002710.193.122.84\u0027, os.name: \u0027Windows 7\u0027, os.arch: \u0027amd64\u0027, os.version: \u00276.1\u0027, java.version: \u00271.8.0_121\u0027\nDriver info: browsers.ChromeBrowser\nCapabilities [{applicationCacheEnabled\u003dfalse, rotatable\u003dfalse, mobileEmulationEnabled\u003dfalse, networkConnectionEnabled\u003dfalse, chrome\u003d{chromedriverVersion\u003d2.29.461591 (62ebf098771772160f391d75e589dc567915b233), userDataDir\u003dC:\\Users\\shkulkar\\AppData\\Local\\Temp\\scoped_dir8124_14810}, takesHeapSnapshot\u003dtrue, pageLoadStrategy\u003dnormal, databaseEnabled\u003dfalse, handlesAlerts\u003dtrue, hasTouchScreen\u003dfalse, version\u003d60.0.3112.113, platform\u003dXP, browserConnectionEnabled\u003dfalse, nativeEvents\u003dtrue, acceptSslCerts\u003dtrue, locationContextEnabled\u003dtrue, webStorageEnabled\u003dtrue, browserName\u003dchrome, takesScreenshot\u003dtrue, javascriptEnabled\u003dtrue, cssSelectorsEnabled\u003dtrue, unexpectedAlertBehaviour\u003d}]\nSession ID: e4d476cd9a2a8d998e6822a4b2efd08a\r\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)\r\n\tat sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)\r\n\tat java.lang.reflect.Constructor.newInstance(Constructor.java:423)\r\n\tat org.openqa.selenium.remote.ErrorHandler.createThrowable(ErrorHandler.java:206)\r\n\tat org.openqa.selenium.remote.ErrorHandler.throwIfResponseFailed(ErrorHandler.java:158)\r\n\tat org.openqa.selenium.remote.RemoteWebDriver.execute(RemoteWebDriver.java:678)\r\n\tat org.openqa.selenium.remote.RemoteWebElement.execute(RemoteWebElement.java:327)\r\n\tat org.openqa.selenium.remote.RemoteWebElement.click(RemoteWebElement.java:85)\r\n\tat application.teststeps.BookingMagnet.select_from_date_dropdown(BookingMagnet.java:64)\r\n\tat ✽.And I select \"30\" date \"September\" month \"2017\" year from date dropdown in booking magnet(02_BookingMagnet.feature:12)\r\n",
  "status": "failed"
});
formatter.match({
  "arguments": [
    {
      "val": "+/- 1 days",
      "offset": 10
    }
  ],
  "location": "BookingMagnet.select_as_date_variance_and_click_on_search_now(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "BookingMagnet.i_should_see_the_Search_Results_Page_for_Spa_days()"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "duration": 255224751,
  "status": "passed"
});
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "duration": 1691018182,
  "status": "passed"
});
formatter.uri("04_visual.feature");
formatter.feature({
  "line": 3,
  "name": "A",
  "description": "",
  "id": "a",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@now"
    }
  ]
});
formatter.before({
  "duration": 99144022,
  "status": "passed"
});
formatter.before({
  "duration": 687097105,
  "status": "passed"
});
formatter.scenario({
  "line": 6,
  "name": "1",
  "description": "",
  "id": "a;1",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 5,
      "name": "@applitools"
    }
  ]
});
formatter.step({
  "line": 7,
  "name": "a",
  "keyword": "Given "
});
formatter.match({
  "location": "Test.a()"
});
formatter.result({
  "duration": 21198664920,
  "error_message": "com.applitools.eyes.TestFailedException: \u0027My first Selenium Java test!\u0027 of \u0027Hello World!\u0027. See details at https://eyes.applitools.com/app/batches/00000251896317615627/00000251896317615377?accountId\u003d-psUyHdup0q7tbMynmKexQ~~\r\n\tat com.applitools.eyes.EyesBase.close(EyesBase.java:601)\r\n\tat com.applitools.eyes.Eyes.close(Eyes.java:17)\r\n\tat com.applitools.eyes.EyesBase.close(EyesBase.java:541)\r\n\tat com.applitools.eyes.Eyes.close(Eyes.java:17)\r\n\tat application.teststeps.Test.a(Test.java:69)\r\n\tat ✽.Given a(04_visual.feature:7)\r\n",
  "status": "failed"
});
formatter.after({
  "duration": 51373055,
  "status": "passed"
});
formatter.embedding("image/png", "embedded1.png");
formatter.after({
  "duration": 527755039,
  "status": "passed"
});
formatter.uri("demo/author.feature");
formatter.feature({
  "line": 2,
  "name": "Author steps",
  "description": "As an author\r\nI should be able to see access AEM\r\nSo that I can create page,add component and publish page",
  "id": "author-steps",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@author"
    },
    {
      "line": 1,
      "name": "@now"
    }
  ]
});
formatter.scenarioOutline({
  "line": 7,
  "name": "Verify if the template is present",
  "description": "",
  "id": "author-steps;verify-if-the-template-is-present",
  "type": "scenario_outline",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 8,
  "name": "I login to aem as \"\u003cusername\u003e\" and \"\u003cpassword\u003e\"",
  "keyword": "Given "
});
formatter.step({
  "line": 9,
  "name": "I navigate to the \"\u003cfolder\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "I should be able to see that template \"\u003ctemplate\u003e\" is present",
  "keyword": "Then "
});
formatter.examples({
  "line": 12,
  "name": "Demo template",
  "description": "",
  "id": "author-steps;verify-if-the-template-is-present;demo-template",
  "rows": [
    {
      "cells": [
        "username",
        "password",
        "template",
        "folder"
      ],
      "line": 13,
      "id": "author-steps;verify-if-the-template-is-present;demo-template;1"
    },
    {
      "cells": [
        "admin",
        "admin",
        "Product Pages",
        "sites.html/content/geometrixx-outdoors/en/men/coats/"
      ],
      "line": 14,
      "id": "author-steps;verify-if-the-template-is-present;demo-template;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 46397708,
  "status": "passed"
});
formatter.before({
  "duration": 17267767,
  "status": "passed"
});
formatter.scenario({
  "line": 14,
  "name": "Verify if the template is present",
  "description": "",
  "id": "author-steps;verify-if-the-template-is-present;demo-template;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 1,
      "name": "@author"
    },
    {
      "line": 1,
      "name": "@now"
    }
  ]
});
formatter.step({
  "line": 8,
  "name": "I login to aem as \"admin\" and \"admin\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 9,
  "name": "I navigate to the \"sites.html/content/geometrixx-outdoors/en/men/coats/\"",
  "matchedColumns": [
    3
  ],
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "I should be able to see that template \"Product Pages\" is present",
  "matchedColumns": [
    2
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "admin",
      "offset": 19
    },
    {
      "val": "admin",
      "offset": 31
    }
  ],
  "location": "AuthorSteps.i_login_to_aem_as_and(String,String)"
});
formatter.result({
  "duration": 4726770150,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "sites.html/content/geometrixx-outdoors/en/men/coats/",
      "offset": 19
    }
  ],
  "location": "AuthorSteps.i_navigate_to_the_folder(String)"
});
formatter.result({
  "duration": 2217317442,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Product Pages",
      "offset": 39
    }
  ],
  "location": "AuthorSteps.i_should_be_able_to_see_that_template_is_present(String)"
});
formatter.result({
  "duration": 3237904434,
  "error_message": "java.lang.AssertionError: Template not present..\r\n\tat org.junit.Assert.fail(Assert.java:88)\r\n\tat org.junit.Assert.assertTrue(Assert.java:41)\r\n\tat application.teststeps.AuthorSteps.i_should_be_able_to_see_that_template_is_present(AuthorSteps.java:46)\r\n\tat ✽.Then I should be able to see that template \"Product Pages\" is present(demo/author.feature:10)\r\n",
  "status": "failed"
});
formatter.after({
  "duration": 77163243,
  "status": "passed"
});
formatter.embedding("image/png", "embedded2.png");
formatter.after({
  "duration": 533226319,
  "status": "passed"
});
formatter.scenarioOutline({
  "line": 17,
  "name": "Verify if page can be cretaed using template",
  "description": "",
  "id": "author-steps;verify-if-page-can-be-cretaed-using-template",
  "type": "scenario_outline",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 18,
  "name": "I login to aem as \"\u003cusername\u003e\" and \"\u003cpassword\u003e\"",
  "keyword": "Given "
});
formatter.step({
  "line": 19,
  "name": "I navigate to the \"\u003cfolder\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 20,
  "name": "I should be able to createpage with title \"\u003cpageTitle\u003e\" using \"\u003ctemplate\u003e\"",
  "keyword": "Then "
});
formatter.examples({
  "line": 22,
  "name": "Demo page create",
  "description": "",
  "id": "author-steps;verify-if-page-can-be-cretaed-using-template;demo-page-create",
  "rows": [
    {
      "cells": [
        "username",
        "password",
        "template",
        "folder",
        "pageTitle"
      ],
      "line": 23,
      "id": "author-steps;verify-if-page-can-be-cretaed-using-template;demo-page-create;1"
    },
    {
      "cells": [
        "admin",
        "admin",
        "Product Page",
        "sites.html/content/geometrixx-outdoors/en/men/coats/",
        "testpages"
      ],
      "line": 24,
      "id": "author-steps;verify-if-page-can-be-cretaed-using-template;demo-page-create;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 15348898,
  "status": "passed"
});
formatter.before({
  "duration": 213811012,
  "status": "passed"
});
formatter.scenario({
  "line": 24,
  "name": "Verify if page can be cretaed using template",
  "description": "",
  "id": "author-steps;verify-if-page-can-be-cretaed-using-template;demo-page-create;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 1,
      "name": "@author"
    },
    {
      "line": 1,
      "name": "@now"
    }
  ]
});
formatter.step({
  "line": 18,
  "name": "I login to aem as \"admin\" and \"admin\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 19,
  "name": "I navigate to the \"sites.html/content/geometrixx-outdoors/en/men/coats/\"",
  "matchedColumns": [
    3
  ],
  "keyword": "When "
});
formatter.step({
  "line": 20,
  "name": "I should be able to createpage with title \"testpages\" using \"Product Page\"",
  "matchedColumns": [
    2,
    4
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "admin",
      "offset": 19
    },
    {
      "val": "admin",
      "offset": 31
    }
  ],
  "location": "AuthorSteps.i_login_to_aem_as_and(String,String)"
});
formatter.result({
  "duration": 2905100151,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "sites.html/content/geometrixx-outdoors/en/men/coats/",
      "offset": 19
    }
  ],
  "location": "AuthorSteps.i_navigate_to_the_folder(String)"
});
formatter.result({
  "duration": 1657415809,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "testpages",
      "offset": 43
    },
    {
      "val": "Product Page",
      "offset": 61
    }
  ],
  "location": "AuthorSteps.i_should_be_able_to_createpage_with_title_using(String,String)"
});
formatter.result({
  "duration": 512721982831,
  "error_message": "java.lang.AssertionError: Page not created..\r\n\tat org.junit.Assert.fail(Assert.java:88)\r\n\tat org.junit.Assert.assertTrue(Assert.java:41)\r\n\tat application.teststeps.AuthorSteps.i_should_be_able_to_createpage_with_title_using(AuthorSteps.java:54)\r\n\tat ✽.Then I should be able to createpage with title \"testpages\" using \"Product Page\"(demo/author.feature:20)\r\n",
  "status": "failed"
});
formatter.after({
  "duration": 690069833,
  "status": "passed"
});
formatter.embedding("image/png", "embedded3.png");
formatter.after({
  "duration": 936452350,
  "status": "passed"
});
formatter.scenarioOutline({
  "line": 27,
  "name": "Verify if page component can be added and page can be published",
  "description": "",
  "id": "author-steps;verify-if-page-component-can-be-added-and-page-can-be-published",
  "type": "scenario_outline",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 28,
  "name": "I login to aem as \"\u003cusername\u003e\" and \"\u003cpassword\u003e\"",
  "keyword": "Given "
});
formatter.step({
  "line": 29,
  "name": "I navigate to the \"\u003cfolder\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 30,
  "name": "I add commponenet \"\u003ccomponent\u003e\"",
  "keyword": "And "
});
formatter.step({
  "line": 31,
  "name": "I edit the component \"\u003cvalue\u003e\"",
  "keyword": "And "
});
formatter.step({
  "line": 32,
  "name": "I publish the page",
  "keyword": "And "
});
formatter.step({
  "line": 33,
  "name": "I should be able to see that the page is published",
  "keyword": "Then "
});
formatter.examples({
  "line": 35,
  "name": "Demo page publish",
  "description": "",
  "id": "author-steps;verify-if-page-component-can-be-added-and-page-can-be-published;demo-page-publish",
  "rows": [
    {
      "cells": [
        "username",
        "password",
        "template",
        "folder",
        "value",
        "component"
      ],
      "line": 36,
      "id": "author-steps;verify-if-page-component-can-be-added-and-page-can-be-published;demo-page-publish;1"
    },
    {
      "cells": [
        "admin",
        "admin",
        "Product Page",
        "editor.html/content/geometrixx-outdoors/en/men/coats/testpage.html",
        "testvalue is entered...",
        "Text"
      ],
      "line": 37,
      "id": "author-steps;verify-if-page-component-can-be-added-and-page-can-be-published;demo-page-publish;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 18043689,
  "status": "passed"
});
formatter.before({
  "duration": 158113317,
  "status": "passed"
});
formatter.scenario({
  "line": 37,
  "name": "Verify if page component can be added and page can be published",
  "description": "",
  "id": "author-steps;verify-if-page-component-can-be-added-and-page-can-be-published;demo-page-publish;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 1,
      "name": "@author"
    },
    {
      "line": 1,
      "name": "@now"
    }
  ]
});
formatter.step({
  "line": 28,
  "name": "I login to aem as \"admin\" and \"admin\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 29,
  "name": "I navigate to the \"editor.html/content/geometrixx-outdoors/en/men/coats/testpage.html\"",
  "matchedColumns": [
    3
  ],
  "keyword": "When "
});
formatter.step({
  "line": 30,
  "name": "I add commponenet \"Text\"",
  "matchedColumns": [
    5
  ],
  "keyword": "And "
});
formatter.step({
  "line": 31,
  "name": "I edit the component \"testvalue is entered...\"",
  "matchedColumns": [
    4
  ],
  "keyword": "And "
});
formatter.step({
  "line": 32,
  "name": "I publish the page",
  "keyword": "And "
});
formatter.step({
  "line": 33,
  "name": "I should be able to see that the page is published",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "admin",
      "offset": 19
    },
    {
      "val": "admin",
      "offset": 31
    }
  ],
  "location": "AuthorSteps.i_login_to_aem_as_and(String,String)"
});
formatter.result({
  "duration": 3570406970,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "editor.html/content/geometrixx-outdoors/en/men/coats/testpage.html",
      "offset": 19
    }
  ],
  "location": "AuthorSteps.i_navigate_to_the_folder(String)"
});
formatter.result({
  "duration": 130407955,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Text",
      "offset": 19
    }
  ],
  "location": "AuthorSteps.i_add_commponenet(String)"
});
formatter.result({
  "duration": 640192175908,
  "error_message": "java.lang.AssertionError: Unable to add the component...\r\n\tat org.junit.Assert.fail(Assert.java:88)\r\n\tat org.junit.Assert.assertTrue(Assert.java:41)\r\n\tat application.teststeps.AuthorSteps.i_add_commponenet(AuthorSteps.java:60)\r\n\tat ✽.And I add commponenet \"Text\"(demo/author.feature:30)\r\n",
  "status": "failed"
});
formatter.match({
  "arguments": [
    {
      "val": "testvalue is entered...",
      "offset": 22
    }
  ],
  "location": "AuthorSteps.i_edit_the_component(String)"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "AuthorSteps.i_publish_the_page()"
});
formatter.result({
  "status": "skipped"
});
formatter.match({
  "location": "AuthorSteps.i_should_be_able_to_see_that_the_page_is_published()"
});
formatter.result({
  "status": "skipped"
});
formatter.after({
  "duration": 476886949,
  "status": "passed"
});
formatter.embedding("image/png", "embedded4.png");
formatter.after({
  "duration": 859417606,
  "status": "passed"
});
});