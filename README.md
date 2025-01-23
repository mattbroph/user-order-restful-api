# User Display Exercise

This exercise will provide you with practice with IntelliJ, Tomee+, MySqL and Maven, while providing a refresher
on many topics from Advanced Java including servlets, jsps, properties, jdbc, expression language (el) and beans. 

## Getting Started

To begin using this project:

1. Accept the assignment using the provided link. This will create a repository containing this exercise.
2. Copy the clone url from your repository in GitHub.
3. From the IntelliJ Welcome Screen, check out the project from Git/GitHub using the clone url you copied above.
4. Edit the run configuration.
5. Build and deploy.
6. The application should deploy, but it will not function correctly since there is no database connected at this point. Click the "Go to the User Search" link and observe that no results are returned. Take a look at the console and look for an error message that indicates the problem. 
7. Explore the code to understand what's been given to you.

## Next Steps

1. Create the database. You can use the sql below, or, you may write your own.

        mysql> CREATE DATABASE sample;
       
1. Select the database you just created. 

        mysql> use sample
    
1. Create the user table.
    
        mysql> create table user
               (
                 id int auto_increment
                   primary key,
                 first_name varchar(25) null,
                 last_name varchar(30) null,
                 user_name varchar(15) null,
                 password varchar(30) null,
                 date_of_birth date null,
                 constraint users_user_name_uindex
                 unique (user_name)
               );

1. Populate the user table. 

        insert into user values (1, 'Joe', 'Coyne', 'jcoyne', 'supersecret1', '1964/04/01');
        insert into user values (2, 'Fred', 'Hensen', 'fhensen', 'supersecret2', '1988/05/08');
        insert into user values (3, 'Barney', 'Curry', 'bcurry', 'supersecret3',  '1947/11/11');
        insert into user values (4, 'Karen', 'Mack', 'kmack', 'supersecret4', '1986/07/08');
        insert into user values (5, 'Dianne', 'Klein', 'dklein', 'supersecret5', '1991/01/22');
        insert into user values (6, 'Dawn', 'Tillman', 'dtillman', 'supersecret6', '1979/08/30');
        
1. Find and complete the TODO in "database.properties" to configure the program to use the database you created above. Question: did you know you can easily find all TODOs in intelliJ with a single click?  Commit your change.

1. Once the TODO is completed, build and deploy. The application should open in the browser, and clicking the "Go to the User Search" link should show you a list of users, but this probably isn't working quite yet...

1. What if any errors do you see in the console? Do you see this "SearchUser.getAllUsers()...SQL Exception: java.sql.SQLException: The server time zone value 'CDT' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specific time zone value if you want to utilize time zone support."? 
One fix is to run this command in mysql: SET GLOBAL time_zone = '-6:00';  If this error had happened and the fix wasn't included here, what would you have searched to find the correct answer?  Try your search idea to see if you find the same solution.

1. After fixing that error, it's likely you'll encounter one more bug to fix in the code. Use the console and the debugger to figure out what very small change you need to make. Commit the fix.

1. Remember to build and deploy after any code change. If you make the correct code fix, you should see a list of users. It's not pretty, but you'll fix that in a little bit. 

## Next Next Steps

1. Add to the project such that the completed application provides the ability to search for and display users from the database. The user display/search results
    should include userid, first name, last name, and age, and be displayed in a table. At minimum, provide a last name search. Some TODOs have been added to help you get started.
1. Make sure your code follows industry best practices, including making atomic commits and clearly commenting that commit.

## Extra Challenges

2. Pretty up your results table.
3. What happens if there's an error in your application? Often when something goes wrong, we end up at a standard error page with an ugly stacktrace on it. Did you know we can create custom error pages in our java web applications? Spend a few minutes searching how to do this and give it a try.
4. Add functionality to add, update, and/or delete users.

## Expectations

1. All steps above and on the course website for the exercise are complete and correct.
2. Execise Submission instructions completed correctly.
3. Follow best practices. For example:
   1. Expression Language (EL) used
   2. No scriptlets used (check your code for <%...%>)
   3. Use the javabean properties in the jsp; do not use explicit method calls such as getFavoriteColor()
   4. Elimate redundant code
   5. Eliminate hard-coded values. Use PropertiesLoader.
   6. Age is not stored as an instance variable, but instead calculated as needed.
   7. Methods are properly javadoc'd.
   8. Commits are atomic and clearly explained in the corresponding commit message. Do not commit all of your work in a single commit!  

    
## Exercise Submission

To receive feedback on this exercise:

1. Take screenshots of your search screen and search results screen.  Please be sure that Barney Curry is included in the output and that his age is correct. 
3. Add the screenshots from step 1 to your screenshots directory.
2. Push your completed code to GitHub. 
3. Verify your latest code and screenshots are visible on GitHub.
5. Create an issue in GitHub to notify your instructor that your work is complete.
      Go to your repository in GitHub.com, click on ```issues```, and then ```New issue```.
   - For issue title, use something like: "Week 2 ready for review".
   - In the issue detail, at minimum add "@pawaitemadisoncollege week 2 is complete".  The ```@pawaitemadisoncollege``` portion is very important as that will send me a notification.
   - Within the issue reflect on your learning experience and work for the week by answering, at minimum, the following:
   
      1. What were your key learning points/takeaways?
      1. What challenged you?
      1. What problems did you solve and what resources did you use to solve them?

   - If you have any questions for me or want to communicate anything else related to your week 2 work, feel free to add that into the issue detail as well.
   - I will add any feedback into the issue.
