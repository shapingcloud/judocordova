//
//  TransactionProcessingApiService.h
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

@class Transaction;
@class JPApplePayTransaction;
@class Card;
@class Consumer;
@class Receipt;


/**
 *  Using the given methods - Address, Consumer, CreditCard, Receipt, Transaction - you can use the static methods of this class to process various transactions via your custom user interface and the judoPayAPI.
 */
@interface TransactionProcessingApiService : NSObject


/**
 *  with the parameters provided you can use this method to make a payment for a given transaction.
 *
 *  @param transaction  The transaction details.
 *  @param deviceID     The device identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)payWithTransaction:(nonnull Transaction *)transaction
              withDeviceID:(nullable NSString *)deviceID
               withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
               withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  pay an apple pay authorized transaction
 *
 *  @param transaction  a JPApplePayTransaction object
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)payWithApplePayTransaction:(nonnull JPApplePayTransaction *)transaction
                       withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                       withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  If you’ve initialised a Transaction object with the parameters provided in the Transaction class, you can use this method to make a pre-authorisation of funds for that transaction.
 *
 *  @param transaction  The transaction details.
 *  @param deviceID     The device identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)preauthWithTransaction:(nonnull Transaction *)transaction
                  withDeviceID:(nullable NSString *)deviceID
                   withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                   withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  pre auth an Apple Pay Transaction
 *
 *  @param transaction  a JPApplePayTransaction object
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)preauthWithApplePayTransaction:(nonnull JPApplePayTransaction *)transaction
                           withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                           withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  refund a transaction with a given receiptID
 *
 *  @param receiptID    the receipt ID
 *  @param amount       the amount
 *  @param paymentRef   the payment reference
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)refundWithReceiptID:(nonnull NSString *)receiptID
                     amount:(nonnull NSString *)amount
           paymentReference:(nonnull NSString *)paymentRef
                withSuccess:(nullable void(^)(NSInteger statusCode, id __nonnull JSON))successBlock
                withFailure:(nullable void(^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  void a previously authenticated transaction
 *
 *  @param receiptID    the receipt id
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)voidPreauthWithReceiptID:(nonnull NSString *)receiptID
                     withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                     withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  If you’ve used the Receipt class to construct a receipt object, you can use that receipt to process a Collection of funds for the specified amount.
 *
 *  @param receipt      The receipt details.
 *  @param amount       the amount to collect.
 *  @param deviceID     the device identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)collectionForReceipt:(nonnull Receipt *)receipt
                  withAmount:(JPAmount)amount
                withDeviceID:(nullable NSString *)deviceID
                 withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                 withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  If you’ve initialised a Consumer object with the parameters provided in the Consumer class, you can use this method to register that consumer’s card with a preauth of £1.01
 *
 *  @param consumer     the consumer details.
 *  @param card         the card details to register.
 *  @param deviceID     the device identifier.
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)registerCardWithConsumer:(nonnull Consumer *)consumer
                        withCard:(nonnull Card *)card
                    withDeviceID:(nullable NSString*)deviceID
                     withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                     withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  An extended version of registerCardWithConsumer, allowing more control over card registration for advanced use cases
 *
 *  @param consumer     the consumer details.
 *  @param card         the card details to register.
 *  @param deviceID     the device identifier.
 *  @param judoId       the judo id
 *  @param preAuth      a bollean that indicates wether to persist the card details
 *  @param successBlock A block object to be executed when the task finishes successfully. This block has no return value and receives two arguments: the status code and the response object created by the client response serializer.
 *  @param failureBlock A block object to be executed when the task finishes unsuccessfully, or that finishes successfully, but encountered an error while parsing the response data. This block has no return value and receives a two arguments: the status code and the error describing the network or parsing error that occurred.
 */
+ (void)registerCardWithConsumer:(nonnull Consumer *)consumer
                        withCard:(nonnull Card *)card
                    withDeviceID:(nullable NSString*)deviceID
                      withJudoId:(nullable NSString*)judoId
                     withPreAuth:(BOOL)preAuth
                     withSuccess:(nullable void (^)(NSInteger statusCode, id __nonnull JSON))successBlock
                     withFailure:(nullable void (^)(NSInteger statusCode, NSError * __nonnull error))failureBlock;


/**
 *  helper method to provide client information
 *
 *  @param deviceID The device identifier
 *
 *  @return a dictionary containing all available details about the client device
 */
+ (nonnull NSDictionary *)clientDetailsWithDeviceID:(nullable NSString*)deviceID;

@end
