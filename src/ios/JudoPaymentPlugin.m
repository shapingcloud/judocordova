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

-(void)paymentAction:(id)sender withCommand:(CDVInvokedUrlCommand*)command {
    AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
    float paymentAmount = [[command.arguments objectAtIndex:0] floatValue];
    NSString* currency = [command.arguments objectAtIndex:1];
    NSString* paymentRef = [command.arguments objectAtIndex:2];
    NSString* consumerRef = [command.arguments objectAtIndex:3];
    NSString *env = [command.arguments objectAtIndex:4];
    NSString* paymentId = @"100610-575";
    NSString *token = @"ujubiPf44kmutM5W";
    NSString *secret = @"1fc7c19857263dce56f022ac0d3da96d90c823a6f9c11b28de26e92698529f38";
   
    
    if(env=='Staging'){
    [JudoSDKManager setSandboxMode];
    }
    
    [JudoSDKManager setToken:token andSecret:secret];
    
    
    
    NSLog(@"Payment amount: [%f]", paymentAmount);
    NSLog(@"Payment ID: [%@]", paymentId);
    NSLog(@"Payment Ref: [%@]", paymentRef);
    NSLog(@"Consumer Ref: [%@]", consumerRef);

    [JudoSDKManager setCurrency:currency];
    
    
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
   
    
    
    
}

@end
