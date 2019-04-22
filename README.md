# MDemo

###How to run
To run application, either load it into Intellij or any other IDE and run "DemoApplication" or package application and then run jar.

After loading go to http://localhost:26000/ to find web application. Please note very little effort was given to the frontend since this is not the focus of the assessment.

Admin user credentials are: admin/admin

User is greeted with a welcome page that will direct the user to a login page if user is not logged in. Or to the main page of the application if user is logged in. Passwords are being encrypted before storing in database.

Login page contains two separate forms. One for login and the other for registration. I have not added any validation on the fields except for them not to be empty and password must match the confirm password field. These validations are added as constraints from the backend only and not from the frontend. In a production scenario these will be added on the frontend as well.

Submitting a registration form successfully will take the user into a page that is not used that will display the JSON out from the api request. Please go back to the previous page and proceed with loging in with the new user to access the application. Or use admin credentials to access with admin rights.

Successfully loging in will take user to main page. This includes a form to create a new card. Like the user registration, i have only included simple validation in this form. Fields must not be empty and the expiry field should be in a pattern of "00/00". Further improvement on the validation might be to really validate the months to not be above 12 for example. Also please note i am storing card numbers as a plain string. In a real world scenario these will be encrypted or storing the first four and last six digits only for extra security to the users. 

After creating new card successfully, please go back to the previous page to continue.

Clicking 'Search Cards' will take user to another page where a search field can be used to search for cards. Admin can view all cards in the system. Newly created users can search for their own cards only. Searching for cards will take user to page only showing the JSON output from the api request just for simplicity of the frontend. 


###Architecture
Application was designed along the Hexagonal Architecture Pattern. Main advantages in using this pattern are separation of concerns since logic is split into different layers of the application. This for me offers a lot more control and easier testing as well.

