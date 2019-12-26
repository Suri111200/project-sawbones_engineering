# Walk In Clinic

SEG 2105 A -- Intro to Software Engineering Fall 2019
School of Engineering University of Ottawa
**Course Coordinator**:  Professor Forward

Group Member's:
Soorya Saravanapavan, 300065226
Hrithik Shah, 300069290
Dylan Boyling, 7889895
Sultan Oloyede, 300076997


**Submission Date**: December 4, 2019

## Introduction

For our culminating project, we were given the task of creating an android application that allows users to interact with several walk-in clinics for the purpose of book appointments.

This required the creation of different user types with varying administrative capabilities and features that would ultimately make it easier for users and clinics to navigate the app. Our app (Clinic On Call) uses external service such as Firebase, CircleCi, and the Google API. Firebase is used to handle user authentication. CircleCi was used to manage different builds of our application. The Google API was used to implement map features into the application.

In the process of constructing our application many lessons were learned in regard to app development. One major take away was the importance of communication. Sharing ideas and having an understand of each individual members progression was crucial to maintaining group chemistry. Another major lesson learned was running tests to find errors. In the process of developing our application, code for different parts of the app were merged together. In doing so many statements clashed with each and as a result time had to be dedicated to making pieces of code work together.

## Class Diagram

![class diagram](media/image1.png)

---

## Contributions

| Contributors / Contributions --> | Dev 1                                                                                                                                     | Dev 2                                                                                   | Dev 3                                                                                                          | Dev 4                                                                   |
|----------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------|
| Hrithik Shah                     | Created sign in/register ability,  implemented Patient, Employee and Admin Class. Created welcome screen. Used SHA-256 to store passwords | Can add services. Delete and edit services. All done by Admin. Validation of all fields | Created Service Provider class. Implemented all functionalities related to SP, Add services, remove services,  | Wait Time, Book appointment, rate service (all Patient functionalities) |
| Soorya Sarvanapavan              | Initialized Firebase                                                                                                                      | UML, Testcases                                                                          | UML, TestCases                                                                                                 | UML, final merges and testing                                           |
| Sultan Oloyede                   | UML                                                                                                                                       | Splash page, Map UI                                                                     | Attempted new UI features.                                                                                     | Report, Profile UI, Loading screen, home screen, dialog UI              |
| Dylan Boyling                    | Not on our team yet                                                                                                                    | Circle CI, Testcases                                                                    | Add availabilites, remove,and edit them                                                                        | Testcases, Search                                                       |

---



## Screenshots

### Loading Screen and Landing Page
![loading screen](media/loadingscreen.jpg)
---
![landing screen](media/landingpage.jpg)

## Sign/Register
![signin screen](media/signin.jpg)
---
![register screen](media/register.jpg)

### Profiles
![patient profile](media/patient_profile.jpg)
---
![service provider profile](media/serviceprovider_profile.jpg)
---
![admin profile](media/admin_profile.jpg)

### Patient Functionalities
![patient welcome](media/patient_welcome.jpg)
---
#### Search
![patient search](media/sp_search.jpg)
---
![patient search](media/sp_search2.jpg)
---
![sp info](media/sp_info.jpg)

#### Book Appointment
![patient book](media/book_appointment1.jpg)
---
![patient book2](media/book_appointment2.jpg)
---
![patient book confirm](media/appointment_booked.jpg)

#### Check In and Review

![checkin](media/check_in.jpg)
---
![patient review](media/add_review.jpg)
---
![patient review](media/reviews.jpg)


### Service Provider Functionalities
![sp welcome](media/sp_welcome.jpg)

#### Pick Services
![Add Services](media/sp_pick_services.jpg)

#### Choose Availabilities
![availabilites](media/availabilities.jpg)
---
![add availabilites](media/add_availabilities.jpg)
---
![update availabilites](media/update_availability.jpg)

### Admin Functionalities
![admin welcome](media/admin_welcome.jpg)

#### Delete Users
![admin users](media/admin_users.jpg)

#### Add/Update/Delete Services
![all services](media/all_services.jpg)
---
![add service](media/add_services.jpg)
---
![update/delete service](media/update_services.jpg)
