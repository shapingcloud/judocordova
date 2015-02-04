//
//  TransactionProcessingApiService.h
//  JudoPay
//
//  Created by Robert Nash on 06/05/2014.
//  Copyright (c) 2014 Alternative Payments. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Transaction;
@class Card;
@class Consumer;
@class Receipt;

@interface TransactionProcessingApiService : NSObject

+(void)payWithTransaction:(Transaction *)transaction
             withDeviceID:(NSString*)deviceID
              withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
              withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;


+(void)preauthWithTransaction:(Transaction *)transaction
                 withDeviceID:(NSString*)deviceID
                  withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)collectionForReceipt:(Receipt *)receipt
                 withAmount:(float)amount
               withDeviceID:(NSString*)deviceID
                withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)registerCardWithConsumer:(Consumer *)consumer
                       withCard:(Card *)card
                   withDeviceID:(NSString*)deviceID
                    withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                    withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

@end
