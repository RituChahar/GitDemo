@tag
Feature: error Validation
  I want to use this template for my feature file

  @ErrorValidation
  Scenario Outline: login with invalid credentials
  
    Given I landed on Ecommerce Page
    When Logged in with username <name> and password <password>
    Then "<errormessage>" message is displayed 
    
    #Then "Incorrect email or password." message is displayed


    Examples: 
      | name 									 | password 		| errormessage                  |    
      | testid1demo@gmail.com  | Test@1234    | Incorrect email or password.  |
      
      