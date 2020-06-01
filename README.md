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

# Authentication:
Requests made to AT APIs must be authenticated, there are two ways to do this:
1. Using username and API Key
    - API Key can be generated in AT dashboard.
    - Need to add API Key as a header param `apiKey`.
    - Need to add `username` field depending on the request. (either in query params or request body)
2. Using Auth token
    - Can be generated using username and API key
    - To keep it secure, need to generate it using POST request. ```https://api.africastalking.com/auth-token/generate```

-------------------------------------------------------------------------------------
>Authentication ends here
-------------------------------------------------------------------------------------

# **Send SMS Flow:**

### **Description:**
- Can send SMSs from our application by making a HTTP POST request to the SMS API.
- For each request sent from our application, service will respond with a `notification` back indicating whether the sms transaction was `successful` or `failed`.

### **Send Bulk SMS:** 
[Reference for code/request](https://build.at-labs.io/docs/sms%2Fsending%2Fbulk)

**Endpoints for Testing:**
- [Live](https://api.africastalking.com/version1/messaging)
- [Sandbox](https://api.sandbox.africastalking.com/version1/messaging)

- **Request Params:**
    - `username`: AT app username
    - `to`: Recipients' phone numbers
    - `message`: message text
    - `from` *(optional)*: default=AFRICASTKNG
    - `bulkSMSCode`: value must be set to 1 for bulk messages (account will be charged based on this value)
    - `keyword`: used for premium service
    - `linkId`: used for premium services to send OnDemand messages. SMS Service will forward the linkId to our application when the user sends a message to our service
    - `etc` - includes few other optional inputs

- **Response:**
````
{
    "SMSMessageData": {
        "Message": "Sent to 1/1 Total Cost: KES 0.8000",
        "Recipients": [{
            "statusCode": 101,
            "number": "+254711XXXYYY",
            "status": "Success",
            "cost": "KES 0.8000",
            "messageId": "ATPid_SampleTxnId123"
        }]
    }
}
````

# **SMS Notifications**
- The SMS API sends a notification when a specific event happens like status of message (success or fail).
- To receive these notifications, we need to setup a callback URL depending on the type of notification you would like to receive.

**Notification Types**

|Category|Description|
|--------|-----------|
|Delivery reports|Sent whenever the mobile service provider confirms or rejects delivery of a message.|
|Incoming messages|	Sent whenever a message is sent to any of your registered shortcodes.
|Bulk SMS Opt Out| Sent whenever a user opts out of receiving messages from your alphanumeric sender ID.|
|Subscription Notifications|	Sent whenever someone subscribes or unsubscribes from any of your premium SMS products.|
> Need to setup call back URLs for each type of notifications.

**Incoming message notification contents:**

|Parameter  |	Description|
|---------  |------------|
|date       |	The date and time when the message was received.|
|from       |	The number that sent the message.|
|id         |	The internal ID that we use to store this message.|
|linkId Optional|	Parameter required when responding to an on-demand user request with a premium message.|
|text       |	The message content.|
|to         |	The number to which the message was sent.|
|networkCode|	A unique identifier for the telco that handled the message. Possible values are: 62120: Airtel Nigeria; 62130: MTN Nigeria|
>All fields are strings.