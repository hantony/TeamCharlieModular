To reset the database:

1) Edit the com.umgc.application  application.properties file
	
	- Copy contents of application.properties.reset and
		place into application.properties and save
		
2) Edit the StartApplication.java file

	-	Uncomment the @Bean annotation, to allow users and tables to be re-seeded
	
		//@Bean
		public CommandLineRunner initializeUsers(UserRepository userRepository) {
	
3) Run the StartApplication.java to re-seed the database

4) Stop the StartApplication server

5) Restore the old application.properties file using application.properties.orig

6) Comment out the @Bean annotation in StarApplication.java

6) Start the StartApplication server 