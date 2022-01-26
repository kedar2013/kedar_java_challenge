# kedar_java_challenge
Once the project build is ready and running, to test the API's we can run them through Postman request

endpoint: http://localhost:18080/v1/accounts/moneyTransfer

Payload can be : 
{
  "accountFromId":"Id-1",
  "accountToId":"Id-2",
  "amount": "1.55"
}

Future improvements in order to make production ready application:

* Security will be most important aspect that I would apply over this application by below steps
    a. I will introduce API Gateway(Spring cloud Gateway), it will handle authorisation & Authentication, entitlements
       to the user and depending on those entitlements APIs would be authorised
    b. All the calls from user would be routed through this gateway.
    c. I would write a common utility to filter out requests and apply spring security to validate requests against all security
       vulnerabilities. This common utility I would inject in all of my micro services.
* For scalability, load balancing will be another important thing I would take into account to make this applicable readily available
* The repository implementation would be modified with actual database storage.
