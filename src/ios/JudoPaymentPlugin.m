//
//  JudoPaymentPlugin.m
//  HelloWorld
//
//  Created by James Isbister on 10/10/2013.
//
//

#import "JudoPaymentPlugin.h"
#import <JudoPay/JudoPay.h>
#import "AppDelegate.h"

@interface JudoPaymentPlugin ()

//@property (nonatomic, strong) NSDictionary *cardInfo;
//@property (nonatomic, strong) NSString *consumerRef;

@end

@implementation JudoPaymentPlugin

#pragma mark - Make Simple Transaction

- (void)makeSimpleTransaction:(CDVInvokedUrlCommand *)command {
    NSLog(@"Amount: %f", [[command.arguments objectAtIndex:0] floatValue]);
    [self paymentAction:nil withCommand:command];
}

-(void)paymentAction:(id)sender withCommand:(CDVInvokedUrlCommand*)command {
    AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
    
    //[JudoAPIManager setToken:@"JiMUukHHkLyX3cax" andSecret:@"65d81925c1ca0a884c6a4e9f2f4186486e7a91c27cf92244223e40cb0170cef1"];
    
    float paymentAmount = [[command.arguments objectAtIndex:0] floatValue];
    NSString* paymentId = [command.arguments objectAtIndex:1];
    NSString* paymentToken = [command.arguments objectAtIndex:2];
    NSString* paymentRef = [command.arguments objectAtIndex:3];
    NSString* consumerRef = [command.arguments objectAtIndex:4];
    
    NSLog(@"Payment amount: [%f]", paymentAmount);
    NSLog(@"Payment ID: [%@]", paymentId);
    NSLog(@"Payment Token: [%@]", paymentToken);
    NSLog(@"Payment Ref: [%@]", paymentRef);
    NSLog(@"Consumer Ref: [%@]", consumerRef);
    
        [JudoAPIManager setoAuthToken:paymentToken];
    
        [JudoAPIManager simpleMakePaymentWithNavBarAmount:paymentAmount
                                       toJudoID:paymentId
                                     withPayRef:paymentRef
                                withConsumerRef:consumerRef
                        andParentViewController:appDelegate.window.rootViewController
                                    withSuccess:^(id JSON) {
                                                  NSLog(@"Success: %@", JSON);
                                                  
                                                  //self.cardInfo = [JSON valueForKey:@"cardDetails"];
                                                  //self.consumerRef = [[JSON valueForKey:@"consumer"] valueForKey:@"yourConsumerReference"];
                                                  NSString* paymentResult = [JSON valueForKey:@"result"];
                                        if((paymentResult != nil)&&([paymentResult isEqualToString:@"Success"])){
                                        
                                        
                                                  CDVPluginResult *pluginResult = [ CDVPluginResult resultWithStatus : CDVCommandStatus_OK messageAsDictionary : JSON ];
                                                  
                                                  UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Payment succesful" message:@"Thank you for your purchase" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles: nil];
                                                  [alertView show];
                                                  
                                                  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                        } else {
                                            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Cancelled"];
                                            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                        }
                                              }
                                    withFailure:^(NSError *error) {
                                                  NSLog(@"Fail: %@", error);
                                                  
                                                  CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"user_cancelled"];
                                                  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                              }
         ];
}

@end
