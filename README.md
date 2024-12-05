Custom logger to print single End-Point Operation in single log.

Sometime its useful to trace all log details in single objects for small projects.

: url 
curl --location 'localhost:8080/v1/test'
: Response

<img width="967" alt="Screenshot 2024-12-05 at 22 44 42" src="https://github.com/user-attachments/assets/c9801f51-f58d-4ae8-b9b5-6851f7c04368">


This simple request shows how we can create two sub-task and attach them to logger.

and printing them at the end of request in case of either success or failure.
<img width="967" alt="Screenshot 2024-12-05 at 22 44 42" src="https://github.com/user-attachments/assets/08f9bcee-66c7-44b5-a75b-f8fb57baaf48">
