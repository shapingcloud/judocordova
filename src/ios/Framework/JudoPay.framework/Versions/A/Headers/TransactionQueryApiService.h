//
//  TransactionQueryApiService.h
//  JudoPay
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

/**
 *  This class is used to invoke the judo Transaction Query API service to retrieve various details for a transaction for display on a custom page of your choice.
 */
@interface TransactionQueryApiService : NSObject


/**
 *  get all Transactions with descending order from the beginning with standard page size (a list of the latest ten transactions processed).
 *
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getTransactionsWithSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                           failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  This method retrieves a sorted page of successful transactions, retrieved according to the sorting option you specify.
 *
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getTransactionsWithOrder:(NSComparisonResult)order
                        pageSize:(NSUInteger)pageSize
                          offset:(NSUInteger)offset
                         success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                         failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;

/**
 *  This method retrieves the transaction details for the receipt number you specify in the request (a list of the latest ten).
 *
 *  @param receiptID    The receipt identifier for the given transaction.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getTransactionWithID:(nonnull NSString *)receiptID
                     success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                     failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get all Payments with descending order from the beginning with standard page size (a list of the latest ten transactions processed).
 *
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPaymentsWithSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  This method retrieves a sorted page of successful transactions, retrieved according to the sorting option you specify.
 *
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPaymentsWithOrder:(NSComparisonResult)order
                    pageSize:(NSUInteger)pageSize
                      offset:(NSUInteger)offset
                     success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                     failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get all refunds with descending order from the beginning with standard page size (a list of the latest ten refunds).
 *
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getRefundsWithSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                      failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get all refunds with the given paramteres
 *
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getRefundsWithOrder:(NSComparisonResult)order
                   pageSize:(NSUInteger)pageSize
                     offset:(NSUInteger)offset
                    success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                    failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get all pre auths with descending order from the beginning with standard page size (a list of the latest ten pre auths).
 *
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPreAuthsWithSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get pre auths from an order with the given parameters
 *
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPreAuthsWithOrder:(NSComparisonResult)order
                    pageSize:(NSUInteger)pageSize
                      offset:(NSUInteger)offset
                     success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                     failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get all collections with descending order from the beginning with standard page size (a list of the latest ten collections).
 *
 *  @param successBlock a block that is executed when the API Call was successful
 *  @param failureBlock a block that is executed when the API Call was unsuccessful
 */
+ (void)getCollectionsWithSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                          failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get all collections from an order with the given parameters
 *
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getCollectionsWithOrder:(NSComparisonResult)order
                       pageSize:(NSUInteger)pageSize
                         offset:(NSUInteger)offset
                        success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                        failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get transaction for a consumer with descending order from the beginning with standard page size (a list of the latest ten transactions processed).
 *
 *  @param consumerID   the consumer identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getTransactionsForConsumer:(nonnull NSString *)consumerID
                           success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                           failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get transaction for a consumer with the given parameters
 *
 *  @param consumerID   the consumer identifier.
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getTransactionsforConsumer:(nonnull NSString *)consumerID
                             order:(NSComparisonResult)order
                          pageSize:(NSUInteger)pageSize
                            offset:(NSUInteger)offset
                           success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                           failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get payments for a consumer with descending order from the beginning with standard page size (a list of the latest ten payments processed).
 *
 *  @param consumerID   the consumer identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPaymentsForConsumer:(nonnull NSString *)consumerID
                       success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get payments for a consumer with the given parameters
 *
 *  @param consumerID   the consumer identifier.
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPaymentsForConsumer:(nonnull NSString *)consumerID
                         order:(NSComparisonResult)order
                      pageSize:(NSUInteger)pageSize
                        offset:(NSUInteger)offset
                       success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get refunds for a consumer with descending order from the beginning with standard page size (a list of the latest ten refunds).
 *
 *  @param consumerID   the consumer identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getRefundsForConsumer:(nonnull NSString *)consumerID
                      success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                      failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get refunds for a consumer with the given parameters
 *
 *  @param consumerID   the consumer identifier.
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getRefundsForConsumer:(nonnull NSString *)consumerID
                        order:(NSComparisonResult)order
                     pageSize:(NSUInteger)pageSize
                       offset:(NSUInteger)offset
                      success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                      failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get pre auths for a consumer with descending order from the beginning with standard page size (a list of the latest ten pre auths).
 *
 *  @param consumerID   the consumer identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPreAuthsForConsumer:(nonnull NSString *)consumerID
                       success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get pre auths for a consumer with the given parameters
 *
 *  @param consumerID   the consumer identifier.
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getPreAuthsForConsumer:(nonnull NSString *)consumerID
                         order:(NSComparisonResult)order
                      pageSize:(NSUInteger)pageSize
                        offset:(NSUInteger)offset
                       success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get collections for a consumer with descending order from the beginning with standard page size (a list of the latest ten collections).
 *
 *  @param consumerID   the consumer identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getCollectionsForConsumer:(nonnull NSString *)consumerID
                          success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                          failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  get collections for a consumer with the given parameters
 *
 *  @param consumerID   the consumer identifier.
 *  @param order        the order of the transactions returned
 *  @param pageSize     The page size to retrieve.
 *  @param offset       The offset at which the page begins.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code, and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)getCollectionsForConsumer:(nonnull NSString *)consumerID
                            order:(NSComparisonResult)order
                         pageSize:(NSUInteger)pageSize
                           offset:(NSUInteger)offset
                          success:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                          failure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


#pragma mark - Deprecated


typedef enum : NSUInteger {
    SORT_ASCENDING = 0,
    SORT_DESCENDING
} SORT_OPTION __deprecated_msg("use NSComparisonResult instead");



+ (void)getTransactionsWithSuccess:(nullable void (^)(NSInteger, id __nonnull))successBlock
                          withSort:(SORT_OPTION)sortOption
                      withPageSize:(NSInteger)pageSize
                        withOffset:(NSInteger)offset
                       withFailure:(nullable void (^)(NSInteger, NSError * __nonnull))failureBlock __attribute__((deprecated));



+ (void)getPaymentswithSort:(SORT_OPTION)sortOption
               withPageSize:(NSInteger)pageSize
                 withOffset:(NSInteger)offset
                withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getPreAuthswithSort:(SORT_OPTION)order
               withPageSize:(NSInteger)pageSize
                 withOffset:(NSInteger)offset
                withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getRefundswithSort:(SORT_OPTION)sortOption
              withPageSize:(NSInteger)pageSize
                withOffset:(NSInteger)offset
               withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
               withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getCollectionswithSort:(SORT_OPTION)sortOption
                  withPageSize:(NSInteger)pageSize
                    withOffset:(NSInteger)offset
                   withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                   withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getTransactionsforConsumer:(nonnull NSString *)consumerID
                          withSort:(SORT_OPTION)sortOption
                      withPageSize:(NSInteger)pageSize
                        withOffset:(NSInteger)offset
                       withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getPaymentsForConsumer:(nonnull NSString *)consumerID
                      withSort:(SORT_OPTION)sortOption
                  withPageSize:(NSInteger)pageSize
                    withOffset:(NSInteger)offset
                   withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                   withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getRefundsForConsumer:(nonnull NSString *)consumerID
                     withSort:(SORT_OPTION)sortOption
                 withPageSize:(NSInteger)pageSize
                   withOffset:(NSInteger)offset
                  withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                  withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getPreAuthsForConsumer:(nonnull NSString *)consumerID
                      withSort:(SORT_OPTION)sortOption
                  withPageSize:(NSInteger)pageSize
                    withOffset:(NSInteger)offset
                   withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                   withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getCollectionsForConsumer:(nonnull NSString *)consumerID
                         withSort:(SORT_OPTION)sortOption
                     withPageSize:(NSInteger)pageSize
                       withOffset:(NSInteger)offset
                      withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                      withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getTransactionsWithSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));



+ (void)getTransactionWithID:(nonnull NSString *)receiptID
                 withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                 withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock __attribute__((deprecated));


@end
