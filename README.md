Custom logger to print single End-Point Operation in single log.

Sometime its useful to trace all log details in single objects for small projects.

: url 
curl --location 'localhost:8080/v1/test'
: Response
<img width="967" alt="Screenshot 2024-12-05 at 21 05 55" src="https://github.com/user-attachments/assets/71a74aff-69da-47ae-8cb1-39adde48791e">

This simple request shows how we can create two sub-task and attach them to logger.

and printing them at the end of request in case of either success or failure.
