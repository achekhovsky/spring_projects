Make sure you have already installed both Docker Engine and Docker Compose. To deploy a project, you need to go to its root directory and run the docker-compose up command. For more information see the documentation on the official Docker site. After successful deployment, go to localhost: 8080/JavaWEB. 
This project consists of several simple web applications, each of which uses its technology stack. At the moment, only one of them has been implemented - orders management. 
The application for managing orders is implemented based on Spring MVC and consists of several pages: 
• Home page
The page provides a brief description of the project. The home page does not require the user to have certain rights. To go to the rest pages, you need to log in as a “user” or “admin”. Depending on the user who was logged in, an automatic transition to the desired page is carried out. All of this is implemented using Spring Security. 
• Orders page
On the page, you can add, change, delete orders. All orders displayed on the page are stored in the PostgreSQL database, in two tables linked by an identifier with a one-to-one relationship. Each order contains the following data: 
- id (numeric)
- create date (date/time type)
- name (string)
- description (string)
- status (boolean)
- image (byte)
Interaction with the database is implemented using Spring JPA and Hibernate technologies. All changes made to the database are immediately displayed on the page. Enabled Spring's transaction management capability. After successfully creating an application on the page, you can change its status. If the "hide completed" option is activated, the orders will no longer be displayed on the page. Real-time page changes are implemented in JavaScript. 
• Admin page 
The demo page does not have any functionality, is activated only if the login is done under the admin. The application also supports internationalization (English, Russian) implemented based on Spring MVC i18n technologies and i18next. Switching between locales occurs both in automatic mode, based on information from cookies, and in manual mode, by adding the "lang" variable to the address line indicating the desired locale. To support your locales or change existing ones, you need to edit the files in the src/main/resources/i18n directory. 
Part of the project is covered by Junit tests. These are mainly tests for interaction with the database and controller tests.
