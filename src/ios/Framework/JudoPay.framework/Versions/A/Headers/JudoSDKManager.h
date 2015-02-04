//
//  JudoAPIManager.h
//  JudoPayAPI
//
//  Created by Kieran Gutteridge on 26/06/2013.
//  Copyright (c) 2013 Alternative Payments.. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Card.h"
#import "CreditCardController.h"
#import <CoreLocation/CoreLocation.h>

#define USER_AGENT_NSUSERDEFAULTS_USERAGENT @"userAgentValue"

@interface JudoSDKManager : NSObject <CreditCardControllerDelegate>
@property (nonatomic, strong) Card *currentCard;
@property (nonatomic, strong) CLLocation *currentLocation;
@property (nonatomic, strong) NSDictionary *clientDetails;

+ (JudoSDKManager *)sharedManager;

+(void)setSandboxMode;
+(void)setProductionMode;

+(void)setToken:(NSString *)key
      andSecret:(NSString *)secret;
+(void)setoAuthToken:(NSString *)oAuthtoken;


+(void)enableNavBar:(BOOL)navEnabled;
+(void)setCurrency:(NSString *)currency;
+(void)enableFraudSignalsWithDeviceId:(NSString *)deviceIdentifier;


+(void)setLocationEnabled:(BOOL)enabled;
+(BOOL)getLocationEnabled;

+(void)set3DSecureEnabled:(BOOL)enabled;
+(BOOL)get3DSecureEnabled;

+(void)setAVSEnabled:(BOOL)enabled;
+(BOOL)getAVSEnabled;

+(void)setAmExAccepted:(BOOL)accepted;
+(BOOL)getAmExAccepted;

+(void)setMaestroAccepted:(BOOL)accepted;
+(BOOL)getMaestroAccepted;

+(NSDictionary*)clientDetailsWithDeviceID:(NSString*)deviceID;

+(void)setUserAgent;
+(BOOL)shouldCheckUserAgent;

+(void)handleApplicationOpenURL:(NSURL *)url;


+(CreditCardController *)getCreditCardController;

#pragma mark custom method
+(void)makeAPaymentWithAmountFromCustomUI:(float)amount
                                 toJudoID:(NSString *)judoID
                               withPayRef:(NSString *)ref
                          withConsumerRef:(NSString *)consumerRef
                             withMetaData:(NSDictionary *)metaData
                         inViewController:(UIViewController *)dvc
                                 withCard:(Card *)card
                  andParentViewController:(id)viewController
                              withSuccess:(void(^)(id JSON))successBlock
                              withFailure:(void(^)(NSError *error))failureBlock;

#pragma mark UI methods without UINavBar
+(void)makeAPaymentWithAmount:(float)amount
                      toJudoID:(NSString *)judoID
                    withPayRef:(NSString *)ref
               withConsumerRef:(NSString *)consumerRef
                  withMetaData:(NSDictionary *)metaData
       andParentViewController:(id)viewController
                   withSuccess:(void(^)(id JSON))successBlock
                   withFailure:(void(^)(NSError *error))failureBlock;

+(void)makeATokenPaymentWithAmount:(float)amount
                    withCardDetails:(NSDictionary *)cardDetails
                           toJudoID:(NSString *)judoID
                         withPayRef:(NSString *)ref
                    withConsumerRef:(NSString *)consumerRef
                       withMetaData:(NSDictionary *)metaData
            andParentViewController:(id)viewController
                        withSuccess:(void (^)(id))successBlock
                        withFailure:(void (^)(NSError *))failureBlock;

+(void)makeAPreAuthWithAmount:(float)amount
                   withCardDetails:(NSDictionary *)cardDetails
                          toJudoID:(NSString *)judoID
                        withPayRef:(NSString *)ref
                   withConsumerRef:(NSString *)consumerRef
                      withMetaData:(NSDictionary *)metaData
           andParentViewController:(id)viewController
                       withSuccess:(void (^)(id))successBlock
                       withFailure:(void (^)(NSError *))failureBlock;

+(void)makeATokenPreAuthWithAmount:(float)amount
                   withCardDetails:(NSDictionary *)cardDetails
                          toJudoID:(NSString *)judoID
                        withPayRef:(NSString *)ref
                   withConsumerRef:(NSString *)consumerRef
                      withMetaData:(NSDictionary *)metaData
           andParentViewController:(id)viewController
                       withSuccess:(void (^)(id))successBlock
                       withFailure:(void (^)(NSError *))failureBlock;

+(void)registerCard:(Card *)card
             withConsumerRef:(NSString *)consumerRef
                withDeviceID:(NSString *)deviceID
    withParentViewController:(id)viewController
                 withSuccess:(void(^)(id JSON))successBlock
                 withFailure:(void(^)(NSError *error))failureBlock;

#pragma mark judo get and post helpers

- (void)getPath:(NSString *)path
     parameters:(NSDictionary *)parameters
        success:(void (^)(NSInteger statusCode, id JSON))success
        failure:(void (^)(NSInteger statusCode, NSError *error))failure;

- (void)postPath:(NSString *)path
      parameters:(NSDictionary *)parameters
         success:(void (^)(NSInteger statusCode, id JSON))success
         failure:(void (^)(NSInteger statusCode, NSError *error))failure;

- (void)putPath:(NSString *)path
     parameters:(NSDictionary *)parameters
        success:(void (^)(NSInteger statusCode, id))success
        failure:(void (^)(NSInteger statusCode, NSError *))failure;

@end
