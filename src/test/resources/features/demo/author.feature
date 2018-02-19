Feature: Author steps
As an author
I should be able to see access AEM
So that I can create page,add component and publish page

@author
Scenario Outline: Verify if the template is present
Given I login to aem as "<username>" and "<password>"
When  I navigate to the "<folder>"
Then  I should be able to see that template "<template>" is present

Examples: Demo template
|username|password|template     |folder                                              |
|admin   |admin   |Product Pages|sites.html/content/we-retail/us/en/men|


Scenario Outline: Verify if page can be cretaed using template
Given I login to aem as "<username>" and "<password>"
When  I navigate to the "<folder>"
Then  I should be able to createpage with title "<pageTitle>" using "<template>"

Examples: Demo page create
|username|password|template    |folder                               |pageTitle|
|admin   |admin   |Hero Page |sites.html/content/we-retail/us/en/men/|testpages|


Scenario Outline: Verify if page component can be added and page can be published
Given I login to aem as "<username>" and "<password>"
When  I navigate to the "<folder>"
And   I add commponenet "<component>"
And   I edit the component "<value>"
And   I publish the page
Then  I should be able to see that the page is published

Examples: Demo page publish
|username|password|template    |folder                     										  |value                    |component|                         
|admin   |admin   |Hero Page|editor.html/content/we-retail/us/en/men/testpage.html|testvalue is entered...  | Text    |