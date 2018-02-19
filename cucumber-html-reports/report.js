$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("prhLogin.feature");
formatter.feature({
  "line": 1,
  "name": "Author verification Steps",
  "description": "As author I need to open browser\nand able to access the AEM URL.",
  "id": "author-verification-steps",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 6,
  "name": "Verify if am able to access AEM URL",
  "description": "",
  "id": "author-verification-steps;verify-if-am-able-to-access-aem-url",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 5,
      "name": "@now"
    }
  ]
});
formatter.step({
  "line": 7,
  "name": "I login to aem \"\u003cusername\u003e\" and \"\u003cpassword\u003e\"",
  "keyword": "Given "
});
formatter.step({
  "line": 8,
  "name": "I navigate to \"\u003cfolder\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 9,
  "name": "create a page with title \"\u003cpageTitle\u003e\" using \"\u003ctemplate\u003e\"",
  "keyword": "And "
});
formatter.examples({
  "line": 12,
  "name": "Demo template",
  "description": "",
  "id": "author-verification-steps;verify-if-am-able-to-access-aem-url;demo-template",
  "rows": [
    {
      "cells": [
        "username",
        "password",
        "folder",
        "pageTitle",
        "template"
      ],
      "line": 13,
      "id": "author-verification-steps;verify-if-am-able-to-access-aem-url;demo-template;1"
    },
    {
      "cells": [
        "admin",
        "admin",
        "sites.html/content/we-retail/us/en/",
        "First Automated Page",
        "Product Page"
      ],
      "line": 14,
      "id": "author-verification-steps;verify-if-am-able-to-access-aem-url;demo-template;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 3794577539,
  "status": "passed"
});
formatter.scenario({
  "line": 14,
  "name": "Verify if am able to access AEM URL",
  "description": "",
  "id": "author-verification-steps;verify-if-am-able-to-access-aem-url;demo-template;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 5,
      "name": "@now"
    }
  ]
});
formatter.step({
  "line": 7,
  "name": "I login to aem \"admin\" and \"admin\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 8,
  "name": "I navigate to \"sites.html/content/we-retail/us/en/\"",
  "matchedColumns": [
    2
  ],
  "keyword": "When "
});
formatter.step({
  "line": 9,
  "name": "create a page with title \"First Automated Page\" using \"Product Page\"",
  "matchedColumns": [
    3,
    4
  ],
  "keyword": "And "
});
formatter.match({
  "arguments": [
    {
      "val": "admin",
      "offset": 16
    },
    {
      "val": "admin",
      "offset": 28
    }
  ],
  "location": "AuthorAccess.accessAEM(String,String)"
});
formatter.result({
  "duration": 3778606461,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "sites.html/content/we-retail/us/en/",
      "offset": 15
    }
  ],
  "location": "AuthorAccess.i_navigate_to_folder(String)"
});
formatter.result({
  "duration": 1564292888,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "First Automated Page",
      "offset": 26
    },
    {
      "val": "Product Page",
      "offset": 55
    }
  ],
  "location": "AuthorAccess.create_a_page_with_title_using(String,String)"
});
formatter.result({
  "duration": 757757270634,
  "error_message": "java.lang.AssertionError: Page not created..\r\n\tat org.junit.Assert.fail(Assert.java:88)\r\n\tat org.junit.Assert.assertTrue(Assert.java:41)\r\n\tat application.teststeps.AuthorAccess.create_a_page_with_title_using(AuthorAccess.java:47)\r\n\tat ✽.And create a page with title \"First Automated Page\" using \"Product Page\"(prhLogin.feature:9)\r\n",
  "status": "failed"
});
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "duration": 848239226,
  "status": "passed"
});
});