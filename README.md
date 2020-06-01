# AT-SMS-POC
![](https://res.cloudinary.com/dbcrzw17s/image/upload/v1549388402/yoda%20course%20artwork/ussd-overview.png)

## Your Web Application:
* This application should be able to handle the AT **POST** request and send response as plain text.
* `Service code` and `Callback URL` should be registered in AT.
* **API Params**:
    - `sessionId`	- a unique value generated when session starts and user responds to the given menu
    - `phoneNumber`	- mobile number of subscriber interacting with USSD app
    - `networkCode`	- telco of phoneNumber (A unique identifier for the telco that handled the message)
    - `serviceCode`	- USSD code assigned to our api
    - `text`		- This shows the user input. It is an empty string in the first notification of a session. After that, it concatenates all the user input within the session with a * until the session ends.

* **Note**: All parameters should be the part of query string.

## AT API Cloud:
- Africa's Talking API cloud will be bridge between your application and the user.
- You need to register your application's endpoint as a callback URL for USSD service.
- You need to create a channel to identify your callback URL with a service code.
- You can see all sessions/channels/service codes in your AT dashboard.

## USSD Request Flow:
1. When user dials the `SERVICE CODE` in their phone, AT will handle the request and forwards it to your application.
2. For first request, the `text` parameter will be empty. This is the identification of initial request.
3. If the `text` is empty, display the main menu, to start communicating with user.
4. When user responds with any option, AT will append user option to `text` parameter and forwards to your application.
5. Every user input will separated by `*`.
6. After processing user input, you need to send the response back in plain text format (`text/plain`).
    * `END` will be the end of the session.
    * `CON` means it will continue the communication between your app and user.
    * To continue the session, CON should be starting text in the response. Else, END should be the starting text.
    * Session will be terminated if your API is not working or the response malformed (does not begin with CON or END)
7. As USSD is session driven, authenticate using **sessionId** from post request.

[PHP code sample for reference](https://build.at-labs.io/docs/ussd%2Foverview)

-------------------------------------------------------------------------------------

