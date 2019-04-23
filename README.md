# MDemo

## How to compile
Assuming you have maven and java 8 installed, just run mvn clean install to compile the application.

## How to run
To run application, either load it into Intellij or any other IDE and run "DemoApplication" or package application and then run jar.

## How to use
After running go to http://localhost:26000/ to find web application. Please note very little effort was given to the frontend since this is not the focus of the assessment.

#### Login
Admin user credentials are: admin/admin

User is greeted with a welcome page that will direct the user to a login page if user is not logged in. Or to the main page of the application if user is logged in. Passwords are being encrypted before storing in database.

Login page contains two separate forms. One for login and the other for registration. I have not added any validation on the fields except for them not to be empty and password must match the confirm password field. These validations are added as constraints from the backend only and not from the frontend. In a production scenario these should be added on the frontend as well.

#### Logout
In the main screen a logout button will appear.

#### Register
Submitting a registration form successfully will take the user into a page that is not used that will display the JSON output from the api request. Please go back to the previous page and proceed with loging in with the new user to access the application. Or use admin credentials to access with admin rights.

#### Create Card
Successfully loging in will take user to main page. This includes a form to create a new card. Like the user registration, i have only included simple validation in this form. Fields must not be empty and the expiry field should be in a pattern of "00/00". Further improvement on the validation might be to really validate the months to not be above 12 for example. Validation checks that can be added easily as well are name length and card number length but were not added since these will mainly depend on requirements that where not given. Also please note i am storing card numbers as a plain string. In a real world scenario these will be encrypted or storing the first four and last six digits only for extra security to the users. 

After creating new card successfully, please go back to the previous page to continue.

#### Update Card
Creating a new card with the same exact number as a card that is already in the system will update the expiry to the one entered last.

Please note: At the last moment I remembered a check that can be included for a user to not be able to update a card that is not his. This can easily be done since we have the old card that contains the owner id and the update request has the principal which we can get the id of the person doing the call. If these are different we then check if the user is an admin and if yes still update the card. If not an admin, the call will be rejected.

#### Search Card
Clicking 'Search Cards' will take user to another page where a search field can be used to search for cards. Admin can view all cards in the system. Newly created users can search for their own cards only. Searching for cards will take user to page only showing the JSON output from the api request just for simplicity of the frontend. Search will return a list of cards that contain the string the user is filtering by.  


## Architecture

Application was designed along the Hexagonal Architecture Pattern. Hexagonal Architecture implies a separation between the domain and the adapters which are in turn split into primary adapter and secondary adapters. A primary adapter makes calls to the domain such as a unit test or a rest controller while the secondary adapter is called from the domain such as a database or caching server. In this exercise the adapters and separated from the domain by packages. This for me offers a lot more control and easier testing as well.

