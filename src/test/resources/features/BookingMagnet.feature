@first
Feature: Booking Magnet Component
As a guest user 
when i open the home page of aquasana
booking magnet , which is used as search bar is present already on page


Scenario: Verify booking Magnet on homepage and perform a spa search
Given I am on the home page "https://uat.aquasana.co.uk"
When  I see the Booking Magnet
And   I select "Whinfell Forest" from Spa locations dropdown in booking magnet 
And   I select "26" date "May" month "2017" year from date dropdown in booking magnet
And   I select "+/- 1 days" as date variance and search in booking magnet
Then  I should see the Search Results Page for Spa days