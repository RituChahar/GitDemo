@tag
Feature: Purchase the order from E-commerce Website
  I want to use this template for my feature file
  
  Background: 
  Given I landed on Ecommerce Page


  @Regression
  Scenario Outline: Positive Test of Submitting the order
  
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

    Examples: 
      | name 									 | password 		| productName  |
      | testid1demo@gmail.com  |     Test@123 | ZARA COAT 3  |
      
