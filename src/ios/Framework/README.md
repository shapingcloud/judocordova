JudoPay SDK for iOS
===================

[judopay.com](https://www.judopay.com/) provides fast, easy credit card handling in mobile apps.

Instructions
------------

The judopayments iOS SDK includes header files and a single static library.

### Sign up for judopayments

*   To use the judopay SDK, you'll need to [sign up](https://www.judopay.com/signup) and get your app token 

### Requirements

*   Supports target deployment of iOS version 6.0+.
*   Supports armv7 and armv7s devices, but not armv6.

### Instructions

1. Add these frameworks to your project.
	* CoreGraphics
	* Foundation
	* MobileCoreServices
    * QuartzCore
    * Security
	* SystemConfiguration
    * UIKit

2. Drag the JudoPay.framework and JudoPay.bundle to your Xcode project and add the -ObjC and -all_load to your projects linker flags

3. Add [judopayments open source license acknowledgments](acknowledgments.md) to
[your app's acknowledgments](http://stackoverflow.com/questions/3966116/where-to-put-open-source-credit-information-for-an-iphone-app).

4. add '#import <JudoPay/JudoPay.h>' to the top of the file where you want to use the SDK.

5. To instruct the SDK to communicate with the sandbox, include the following line 
 [JudoAPIManager setSandboxMode];
 When you are ready to go live you can remove this line.
 We would recommend to put this in the method didFinishLaunchingWithOptions in AppDelegate.m

6. You can also set your key and secret here if you do not wish to include it in all subsequent calls
 [JudoAPIManager setKey:@"yourKey" andSecret:@"yourSecret"];

7. A single line integration can be achieved with the following static method on JudoAPIManager: +(void)simpleMakePaymentAmount:(float)amount toJudoID:(NSString *)judoID withKey:(NSString *)key andSecret:(NSString *)secret withPayRef:(NSString *)ref andParentViewController:(id)viewController withSuccess:(void(^)(id responseObject))successBlock withFailure:(void(^)(NSError *error))failureBlock;
Parse and inspect the returned JSON data object for details of the transaction.

Example response:
{
    amount = 4;
    appearsOnStatementAs = "JudoPay/judoPayD";
    cardDetails =     {
        cardLastfour = 3436;
        cardToken = 9CE128956DB34ED0902AC3E155EBC299;
        cardType = 1;
        endDate = 1215;
    };
    consumer =     {
        yourConsumerReference = yourconsumerref;
    };
    createdAt = "2013-07-18T11:39:03.6000+01:00";
    judoId = 100016;
    merchantName = "judoPay Dev";
    message = "AuthCode: 492115";
    netAmount = 4;
    originalAmount = 4;
    receiptId = 33712;
    result = Success;
    type = Payment;
}

6. To save a card for later use, retrieve the cardDetails dictionary and yourConsumerReference from the above response and store them securely. Use the following static method on JudoAPIManager to make another payment using the card token: +(void)simpleMakeTokenPaymentAmount:(float)amount withCardDetails:(NSDictionary *)cardDetails toJudoID:(NSString *)judoID withKey:(NSString *)key andSecret:(NSString *)secret withPayRef:(NSString *)ref withConsumerRef:(NSString *)consumerRef andParentViewController:(id)viewController withSuccess:(void(^)(id responseObject))successBlock withFailure:(void(^)(NSError *error))failureBlock;

7.  Refer to the header files for more usage options and information.

### Sample code
```obj-c
// SomeViewController.h

#import <JudoPay/JudoPay.h>

[JudoAPIManager simpleMakePaymentAmount:4.0
                               toJudoID:@"100016"
                                withKey:@"1234"
                              andSecret:@"secret"
                             withPayRef:@"1234"
                        withConsumerRef:@"yourConsumerRef"
                andParentViewController:self
                            withSuccess:^(id responseObject) {
                              id object = [NSJSONSerialization JSONObjectWithData:responseObject options:0 error:nil];
                                        NSLog(@"parsed response: %@", object);
                                        
					// store these for later use
                                        NSDictionary *cardDetails = [object valueForKey:@"cardDetails"];
                                        NSString *consumerRef = [[object valueForKey:@"consumer"] valueForKey:@"yourConsumerReference"];
                          } withFailure:^(NSError *error) {
                                        NSLog(@"Failure: %@", error);
                          }];

```

### Hints & Tips

Most likely you will want to include a proper payment reference and consumer reference, helper classes are provided in the JudoPay SDK to facilitate this and make use of simple objects such as NSDictionary and NSString


