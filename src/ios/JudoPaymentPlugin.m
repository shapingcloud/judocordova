//
//  JudoPaymentPlugin.m
//
//  Created by Shaping Cloud on 15/11/2014.
//  originally forked from https://github.com/edzordzinam/judocordova
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

- (void)paymentAction:(id)sender withCommand:(CDVInvokedUrlCommand*)command {
    AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];

    float paymentAmount = [[command.arguments objectAtIndex:0] floatValue];
    NSString* currency = [command.arguments objectAtIndex:1];
    NSString* paymentRef = [command.arguments objectAtIndex:2];
    NSString* consumerRef = [command.arguments objectAtIndex:3];


    NSDictionary *infoDict = [[NSBundle mainBundle] infoDictionary];

    NSString *env = [infoDict objectForKey:@"JudoENV"];
    NSString *paymentId = [infoDict objectForKey:@"JudoID"];
    NSString *token = [infoDict objectForKey:@"JudoAPIToken"];
    NSString *secret = [infoDict objectForKey:@"JudoAPISecret"];
   
    BOOL isLive = [env isEqualToString:@"live"];
   
    if(isLive){
        [JudoSDKManager setProductionMode];
    }
    
    NSDictionary *cardInfo;
    NSDictionary *jsonStr = [command.arguments objectAtIndex:4];
    if([jsonStr isKindOfClass:[NSDictionary class]]){
        
        cardInfo = jsonStr;
        cardInfo = [cardInfo objectForKey:@"cardDetails"];
    }
    
    [JudoSDKManager setToken:token andSecret:secret];
    
    NSLog(@"Token: [%@]", token);
    NSLog(@"Secret: [%@]", secret);
    
    NSLog(@"Payment amount: [%f]", paymentAmount);
    NSLog(@"Payment ID: [%@]", paymentId);
    NSLog(@"Payment Ref: [%@]", paymentRef);
    NSLog(@"Consumer Ref: [%@]", consumerRef);

    [JudoSDKManager setCurrency:currency];
    if(![cardInfo objectForKey:@"cardToken"]){ 
    
    [JudoSDKManager
     makeAPaymentWithAmount:paymentAmount
     toJudoID:paymentId
     withPayRef:paymentRef
     withConsumerRef:consumerRef
     withMetaData: @{@"TestDescription" : @"Test"}
     andParentViewController:appDelegate.window.rootViewController
     withSuccess:^(id receipt) {
         NSLog(@"Success: %@", receipt);
         
         NSString* paymentResult = [receipt valueForKey:@"result"];
         if((paymentResult != nil)&&([paymentResult isEqualToString:@"Success"])){
             
             CDVPluginResult *pluginResult = [ CDVPluginResult resultWithStatus : CDVCommandStatus_OK messageAsDictionary : receipt ];
             [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
         } else {
             CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Cancelled"];
             [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
         }
         
     } withFailure:^(NSError *error) {
         NSLog(@"Fail: %@", error);
         
         CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"user_cancelled"];
         [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
     }];
   } else {
    [JudoSDKManager
         makeATokenPaymentWithAmount:paymentAmount
         withCardDetails:cardInfo
         toJudoID:paymentId
         withPayRef:paymentRef
         withConsumerRef:consumerRef
         withMetaData: @{@"TestDescription" : @"Test"}
         andParentViewController:appDelegate.window.rootViewController
         withSuccess:^(id receipt) {
             NSLog(@"Success: %@", receipt);
             
             NSString* paymentResult = [receipt valueForKey:@"result"];
             if((paymentResult != nil)&&([paymentResult isEqualToString:@"Success"])){
                 
                 CDVPluginResult *pluginResult = [ CDVPluginResult resultWithStatus : CDVCommandStatus_OK messageAsDictionary : receipt ];
                 [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
             } else {
                 CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Cancelled"];
                 [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
             }
             
         } withFailure:^(NSError *error) {
             NSLog(@"Fail: %@", error);
             
             CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"user_cancelled"];
             [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
         }];
   }
    
    
    
}

@end
