//
//  JudoPaymentPlugin.h
//  HelloWorld
//
//  Created by James Isbister on 10/10/2013.
//
//

#import <Cordova/CDV.h>

@interface JudoPaymentPlugin : CDVPlugin

- (void)makeSimpleTransaction:(CDVInvokedUrlCommand*)command;

@end
