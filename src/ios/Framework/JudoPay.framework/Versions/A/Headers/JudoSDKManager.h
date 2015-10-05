//
//  JudoAPIManager.h
//  JudoPayAPI
//
//  The MIT License (MIT)
//
//  Copyright (c) 2015 Judo Payments
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.
//



#import <Foundation/Foundation.h>
#import "Helper.h"

@class Card;
@class CreditCardController;

#define USER_AGENT_NSUSERDEFAULTS_USERAGENT @"userAgentValue"


/**
 *  First Point of interaction with the Judo Payments SDK to initialize and make simple transfers
 */
@interface JudoSDKManager : NSObject


/**
 *  the current card if available
 */
@property (nonatomic, strong) Card * __nullable currentCard;


/**
 *  the client details if available
 */
@property (nonatomic, strong) NSDictionary * __nullable clientDetails;


/**
 *  define wether location is enabled
 */
@property (nonatomic, assign, getter=isLocationEnabled) BOOL locationEnabled;


/**
 *  define wether 3DS is enabled
 *  @warning to enable 3DS - please contact judo
 */
@property (nonatomic, assign, getter=isThreeDSEnabled) BOOL threeDSEnabled;


/**
 *  define wether AVS is enabled
 */
@property (nonatomic, assign, getter=isAVSEnabled) BOOL AVSEnabled;


/**
 *  If you need to accept payments from American Express cards you can use this method to configure your SDK for this card type - However, before enabling this please contact us to ensure your account is configured for this card type.
 */
@property (nonatomic, assign, getter=isAMEXAccepted) BOOL AMEXAccepted;


/**
 *  If you need to accept payments from Maestro cards you can use this method to configure your SDK for this card type - However, before enabling this please contact us to ensure your account is configured for this card type.
 */
@property (nonatomic, assign, getter=isMaestroAccepted) BOOL maestroAccepted;


/**
 *  Singleton access to the SDK Manager
 *
 *  @return an instance of JudoSDKManager
 */
+ (nonnull instancetype)sharedSession;


/**
 *  Using this method will set the functionality of your SDK to submit API calls to our sandbox environment - where only test card details can be utilised.
 *  If you’ve already set this method and want to revert back to the live environment, you can follow the steps required on our Go Live tutorial.
 *  https://www.judopay.com/docs/v4_1/going-live/
 */
- (void)setSandboxMode;


/**
 *  This method sets the global key or secret that the SDK will use for authorisation when submitting calls to our API.
 *  You can find your API credentials, including your Token and Secret within your judo dashboard.
 *  https://www.judopay.com/sign-in/
 *
 *  @param key    The token generated for your App.
 *  @param secret The secret key associated with the token.
 */
- (void)setToken:(nonnull NSString *)key andSecret:(nonnull NSString *)secret;


/**
 *  set currency code (unicode format)
 *
 *  @param currency currency code string
 */
- (void)setCurrency:(nonnull NSString *)currency;


/**
 *  set the device identifier
 *
 *  @param deviceIdentifier device id string
 */
- (void)enableFraudSignalsWithDeviceId:(nullable NSString *)deviceIdentifier;


/**
 *  You can use this method to enable judo’s navigation bar within the SDK. 
 *  You can set this within your app to toggle so it only appears when you need it to.
 *
 *  @param navEnabled Set to true/false to enable/disable the navbar.
 */
- (void)enableNavBar:(BOOL)navEnabled;


/**
 *  set the user agent
 */
+ (void)setUserAgent;


/**
 *  check the user agent
 *
 *  @return boolean value
 */
+ (BOOL)shouldCheckUserAgent;


/**
 *  forward application handling state
 *
 *  @param url url
 */
+ (void)handleApplicationOpenURL:(nonnull NSURL *)url;



#pragma mark UI methods without UINavBar


/**
 *  By using this method, you can submit a payment to the judoPay API from your own user interface.
 *  Callback will be to the success or failure blocks depending on the result.
 *
 *  @param amount         The amount to be processed in the transaction.
 *  @param judoID         The judo id of the account that will process the transaction.
 *  @param ref            Your unique reference for this transaction.
 *  @param consumerRef    Your reference for the consumer.
 *  @param metaData       A dictionary of key/values that you can specify to attach to a transaction.
 *  @param viewController The parent view controller.
 *  @param successBlock   the A block object to be executed when the task finishes successfully. This block has no return value and receives one argument: the response object created by the client response serializer.
 *  @param failureBlock   the A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives one argument: the error describing the network or parsing error that occurred.
 */
- (void)judoPaymentWithAmount:(JPAmount)amount
                       judoID:(nonnull NSString *)judoID
                       payRef:(nonnull NSString *)ref
                  consumerRef:(nonnull NSString *)consumerRef
                     metaData:(nullable NSDictionary *)metaData
         parentViewController:(nonnull id)viewController
                      success:(nullable void(^)(id __nullable JSON))successBlock
                      failure:(nullable void(^)(NSError * __nullable error))failureBlock;


/**
 *  make a judo payment with the given parameters
 *
 *  @param amount         The amount to be processed in the transaction.
 *  @param cardDetails    The card details to use whilst processing the transaction.
 *  @param judoID         The judo id of the account that will process the transaction.
 *  @param ref            Your unique reference for this transaction.
 *  @param consumerRef    Your reference for the consumer.
 *  @param metaData       A dictionary of key/values that you can specify to attach to a transaction.
 *  @param viewController The parent view controller.
 *  @param successBlock   the A block object to be executed when the task finishes successfully. This block has no return value and receives one argument: the response object created by the client response serializer.
 *  @param failureBlock   the A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives one argument: the error describing the network or parsing error that occurred.
 */
- (void)judoPaymentWithAmount:(JPAmount)amount
                  cardDetails:(nonnull NSDictionary *)cardDetails
                       judoID:(nonnull NSString *)judoID
                       payRef:(nonnull NSString *)ref
                  consumerRef:(nonnull NSString *)consumerRef
                     metaData:(nullable NSDictionary *)metaData
         parentViewController:(nonnull id)viewController
                      success:(nullable void (^)(id __nullable JSON))successBlock
                      failure:(nullable void (^)(NSError * __nullable error))failureBlock;


/**
 *  make a judo pre-auth with the given parameters
 *
 *  @param amount         The amount to be processed in the transaction.
 *  @param cardDetails    The card details to use whilst processing the transaction.
 *  @param judoID         The judo id of the account that will process the transaction.
 *  @param ref            Your unique reference for this transaction.
 *  @param consumerRef    Your reference for the consumer.
 *  @param metaData       A dictionary of key/values that you can specify to attach to a transaction.
 *  @param viewController The parent view controller.
 *  @param successBlock   the A block object to be executed when the task finishes successfully. This block has no return value and receives one argument: the response object created by the client response serializer.
 *  @param failureBlock   the A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives one argument: the error describing the network or parsing error that occurred.
 */
- (void)judoPreAuthWithAmount:(JPAmount)amount
                       judoID:(nonnull NSString *)judoID
                       payRef:(nonnull NSString *)ref
                  consumerRef:(nonnull NSString *)consumerRef
                     metaData:(nullable NSDictionary *)metaData
         parentViewController:(nonnull id)viewController
                      success:(nullable void (^)(id __nullable JSON))successBlock
                      failure:(nullable void (^)(NSError * __nullable error))failureBlock;


/**
 *  make a judo pre-auth with a token and the given parameters
 *
 *  @param amount         The amount to be processed in the transaction.
 *  @param cardDetails    The card details to use whilst processing the transaction.
 *  @param judoID         The judo id of the account that will process the transaction.
 *  @param ref            Your unique reference for this transaction.
 *  @param consumerRef    Your reference for the consumer.
 *  @param metaData       A dictionary of key/values that you can specify to attach to a transaction.
 *  @param viewController The parent view controller.
 *  @param successBlock   the A block object to be executed when the task finishes successfully. This block has no return value and receives one argument: the response object created by the client response serializer.
 *  @param failureBlock   the A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives one argument: the error describing the network or parsing error that occurred.
 */
- (void)judoTokenPreAuthWithAmount:(JPAmount)amount
                       cardDetails:(nonnull NSDictionary *)cardDetails
                            judoID:(nonnull NSString *)judoID
                            payRef:(nonnull NSString *)ref
                       consumerRef:(nonnull NSString *)consumerRef
                          metaData:(nullable NSDictionary *)metaData
              parentViewController:(nonnull id)viewController
                           success:(nullable void (^)(id __nullable JSON))successBlock
                           failure:(nullable void (^)(NSError * __nullable error))failureBlock;


/**
 *  register a card with the given parameters
 *
 *  @param card                 The card details to use whilst processing the transaction.
 *  @param consumerRef          consumer reference string
 *  @param deviceID             device id string
 *  @param submitButtonTitle    the button title string
 *  @param parentViewController The parent view controller.
 *  @param successBlock         the A block object to be executed when the task finishes successfully. This block has no return value and receives one argument: the response object created by the client response serializer.
 *  @param failureBlock         the A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives one argument: the error describing the network or parsing error that occurred.
 */
- (void)judoRegisterCard:(nonnull Card *)card
             consumerRef:(nonnull NSString *)consumerRef
                deviceID:(nonnull NSString *)deviceID
       submitButtonTitle:(nonnull NSString *)submitButtonTitle
    parentViewController:(nonnull id)parentViewController
                 success:(nullable void(^)(id __nullable JSON))successBlock
                 failure:(nullable void(^)(NSError * __nullable error))failureBlock;


/**
 *  register a card with the given parameters
 *
 *  @param card           The card details to use whilst processing the transaction.
 *  @param consumerRef    consumer reference string
 *  @param deviceID       device id string
 *  @param viewController The parent view controller.
 *  @param successBlock   the A block object to be executed when the task finishes successfully. This block has no return value and receives one argument: the response object created by the client response serializer.
 *  @param failureBlock   the A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives one argument: the error describing the network or parsing error that occurred.
 */
- (void)judoRegisterCard:(nonnull Card *)card
             consumerRef:(nonnull NSString *)consumerRef
                deviceID:(nullable NSString *)deviceID
    parentViewController:(nonnull id)viewController
                 success:(nullable void(^)(id __nullable JSON))successBlock
                 failure:(nullable void(^)(NSError * __nullable error))failureBlock;


#pragma mark - Deprecated


- (void)getPath:(nonnull NSString *)path
     parameters:(nonnull NSDictionary *)parameters
        success:(nullable void (^)(NSInteger status, id __nullable data))success
        failure:(nullable void (^)(NSInteger status, NSError * __nullable error))failure __deprecated_msg("no networking interaction should be made with this SDK.");



- (void)putPath:(nonnull NSString *)path
     parameters:(nonnull NSDictionary *)parameters
        success:(nullable void (^)(NSInteger status, id __nullable data))success
        failure:(nullable void (^)(NSInteger status, NSError * __nullable error))failure __deprecated_msg("no networking interaction should be made with this SDK.");



- (void)postPath:(nonnull NSString *)path
      parameters:(nonnull NSDictionary *)parameters
         success:(nullable void (^)(NSInteger status, id __nullable  data))success
         failure:(nullable void (^)(NSInteger status, NSError * __nullable error))failure __deprecated_msg("no networking interaction should be made with this SDK.");



+ (void)makeAPaymentWithAmountFromCustomUI:(JPAmount)amount
                                  toJudoID:(nonnull NSString *)judoID
                                withPayRef:(nonnull NSString *)ref
                           withConsumerRef:(nonnull NSString *)consumerRef
                              withMetaData:(nullable NSDictionary *)metaData
                          inViewController:(nullable id)dvc
                                  withCard:(nonnull Card *)card
                   andParentViewController:(nullable id)viewController
                               withSuccess:(nullable void(^)(id __nullable JSON))successBlock
                               withFailure:(nullable void(^)(NSError * __nullable error))failureBlock __deprecated_msg("use TransactionProcessingApiService instead.");



+ (void)makeAPaymentWithAmount:(JPAmount)amount
                      toJudoID:(nonnull NSString *)judoID
                    withPayRef:(nonnull NSString *)ref
               withConsumerRef:(nonnull NSString *)consumerRef
                  withMetaData:(nullable NSDictionary *)metaData
       andParentViewController:(nonnull id)viewController
                   withSuccess:(nullable void(^)(id __nullable JSON))successBlock
                   withFailure:(nullable void(^)(NSError * __nullable error))failureBlock __deprecated_msg("use [JudoSDKManager sharedSession] instead.");



+ (void)makeATokenPaymentWithAmount:(JPAmount)amount
                    withCardDetails:(nonnull NSDictionary *)cardDetails
                           toJudoID:(nonnull NSString *)judoID
                         withPayRef:(nonnull NSString *)ref
                    withConsumerRef:(nonnull NSString *)consumerRef
                       withMetaData:(nullable NSDictionary *)metaData
            andParentViewController:(nonnull id)viewController
                        withSuccess:(nullable void (^)(id __nullable JSON))successBlock
                        withFailure:(nullable void (^)(NSError * __nullable error))failureBlock __deprecated_msg("use [JudoSDKManager sharedSession] instead.");



+ (void)makeAPreAuthWithAmount:(JPAmount)amount
               withCardDetails:(nonnull NSDictionary *)cardDetails
                      toJudoID:(nonnull NSString *)judoID
                    withPayRef:(nonnull NSString *)ref
               withConsumerRef:(nonnull NSString *)consumerRef
                  withMetaData:(nullable NSDictionary *)metaData
       andParentViewController:(nonnull id)viewController
                   withSuccess:(nullable void (^)(id __nullable JSON))successBlock
                   withFailure:(nullable void (^)(NSError * __nullable error))failureBlock __deprecated_msg("use [JudoSDKManager sharedSession] instead.");



+ (void)makeATokenPreAuthWithAmount:(JPAmount)amount
                    withCardDetails:(nonnull NSDictionary *)cardDetails
                           toJudoID:(nonnull NSString *)judoID
                         withPayRef:(nonnull NSString *)ref
                    withConsumerRef:(nonnull NSString *)consumerRef
                       withMetaData:(nullable NSDictionary *)metaData
            andParentViewController:(nonnull id)viewController
                        withSuccess:(nullable void (^)(id __nullable JSON))successBlock
                        withFailure:(nullable void (^)(NSError * __nullable error))failureBlock __deprecated_msg("use [JudoSDKManager sharedSession] instead.");



+ (void)registerCard:(nonnull Card *)card
     withConsumerRef:(nonnull NSString *)consumerRef
        withDeviceID:(nullable NSString *)deviceID
withParentViewController:(nonnull id)viewController
         withSuccess:(nullable void(^)(id __nullable JSON))successBlock
         withFailure:(nullable void(^)(NSError * __nullable error))failureBlock __deprecated_msg("use [JudoSDKManager sharedSession] instead.");


+ (nonnull instancetype)sharedManager __deprecated_msg("use sharedSession instead.");

+ (void)setToken:(nonnull NSString *)token andSecret:(nonnull NSString *)secret __deprecated_msg("use [[JudoSDKManager sharedSession] setToken: andSecret:] instead.");

+ (void)setSandboxMode __deprecated_msg("use [JudoSDKManager sharedSession] instead.");

+ (nonnull NSDictionary *)clientDetailsWithDeviceID:(nullable NSString *)deviceID __deprecated_msg("use [TransactionProcessingApiService clientDetailsWithDeviceID:deviceID] instead.");

+ (void)enableNavBar:(BOOL)navEnabled __deprecated_msg("use [JudoSDKManager sharedSession] instead.");
+ (void)setCurrency:(nonnull NSString *)currency __deprecated_msg("use [JudoSDKManager sharedSession] instead.");

+ (void)enableFraudSignalsWithDeviceId:(nullable NSString *)deviceIdentifier __deprecated_msg("use [JudoSDKManager sharedSession] instead.");

+ (void)setLocationEnabled:(BOOL)enabled __deprecated_msg("use [JudoSDKManager sharedSession] instead.");
+ (BOOL)getLocationEnabled __deprecated_msg("use [JudoSDKManager sharedSession] instead.");

+ (void)set3DSecureEnabled:(BOOL)enabled __deprecated_msg("use [JudoSDKManager sharedSession] instead.");
+ (BOOL)get3DSecureEnabled __deprecated_msg("use [JudoSDKManager sharedSession] instead.");

+ (void)setAVSEnabled:(BOOL)enabled __deprecated_msg("use [JudoSDKManager sharedSession] instead.");
+ (BOOL)getAVSEnabled __deprecated_msg("use [JudoSDKManager sharedSession] instead.");

+ (void)setAmExAccepted:(BOOL)accepted __deprecated_msg("use [JudoSDKManager sharedSession] instead.");
+ (BOOL)getAmExAccepted __deprecated_msg("use [JudoSDKManager sharedSession] instead.");

+ (void)setMaestroAccepted:(BOOL)accepted __deprecated_msg("use [JudoSDKManager sharedSession] instead.");
+ (BOOL)getMaestroAccepted __deprecated_msg("use [JudoSDKManager sharedSession] instead.");


@end
