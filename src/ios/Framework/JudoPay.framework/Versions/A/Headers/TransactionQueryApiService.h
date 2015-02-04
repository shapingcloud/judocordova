//
//  TransactionQueryApiService.h
//  JudoPay
//
//  Created by Robert Nash on 06/05/2014.
//  Copyright (c) 2014 Alternative Payments. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum : NSUInteger {
    SORT_ASCENDING = 0,
    SORT_DESCENDING
} SORT_OPTION;

@interface TransactionQueryApiService : NSObject

+(void)getTransactionsWithSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                      withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getTransactionsWithSuccess:(void (^)(NSInteger, id))successBlock
                         withSort:(SORT_OPTION)sortOption
                     withPageSize:(int)pageSize
                       withOffset:(int)offset
                      withFailure:(void (^)(NSInteger, NSError *))failureBlock;

+(void)getTransactionWithID:(NSString *)receiptID
                withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;


+(void)getPaymentsWithSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getPaymentswithSort:(SORT_OPTION)sortOption
              withPageSize:(int)pageSize
                withOffset:(int)offset
               withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
               withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getRefundsWithSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                 withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getRefundswithSort:(SORT_OPTION)sortOption
             withPageSize:(int)pageSize
               withOffset:(int)offset
              withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
              withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getPreAuthsWithSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getPreAuthswithSort:(SORT_OPTION)sortOption
              withPageSize:(int)pageSize
                withOffset:(int)offset
               withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
               withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getCollectionsWithSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                     withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getCollectionswithSort:(SORT_OPTION)sortOption
                 withPageSize:(int)pageSize
                   withOffset:(int)offset
                  withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getTransactionsForConsumer:(NSString *)consumerID
                      withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                      withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getTransactionsforConsumer:(NSString *)consumerID
                         withSort:(SORT_OPTION)sortOption
                     withPageSize:(int)pageSize
                       withOffset:(int)offset
                      withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                      withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getPaymentsForConsumer:(NSString *)consumerID
                  withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getPaymentsForConsumer:(NSString *)consumerID
                     withSort:(SORT_OPTION)sortOption
                 withPageSize:(int)pageSize
                   withOffset:(int)offset
                  withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getRefundsForConsumer:(NSString *)consumerID
                 withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                 withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getRefundsForConsumer:(NSString *)consumerID
                    withSort:(SORT_OPTION)sortOption
                withPageSize:(int)pageSize
                  withOffset:(int)offset
                 withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                 withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getPreAuthsForConsumer:(NSString *)consumerID
                  withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getPreAuthsForConsumer:(NSString *)consumerID
                     withSort:(SORT_OPTION)sortOption
                 withPageSize:(int)pageSize
                   withOffset:(int)offset
                  withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                  withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getCollectionsForConsumer:(NSString *)consumerID
                     withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                     withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

+(void)getCollectionsForConsumer:(NSString *)consumerID
                        withSort:(SORT_OPTION)sortOption
                    withPageSize:(int)pageSize
                      withOffset:(int)offset
                     withSuccess:(void(^)(NSInteger statusCode, id JSON))successBlock
                     withFailure:(void(^)(NSInteger statusCode, NSError *error))failureBlock;

@end
















