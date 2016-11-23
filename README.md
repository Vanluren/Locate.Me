# Locate.Me
## Made by Stefan Jensen, Thomas Richardy, Jonathan Berger & Villads Nielsen

This is an Android app project exploring ubiquitous computing. This was the primary exercises for the course Pervasive Computing, thaught at the institute of computer science at Aarhus University.

### UserServer

The user server contains a simple rest-api, built on NodeJS using MongoDB as the database. This was used to model login and signup capabilities, and storing user information.


### Locate.Me 

The actual app contains all files for a fully functional app. The app centers around a Google Maps activity, which shows the location of all the users registered on the server. Beyond this, the Maps activity uses the location taken from the deivce, to update the location of the signed-in user. Further on the app uses bluetooth to search for nearby bluetooth devices, which are broadcasting, and furthermore the ability to make reminders.  
