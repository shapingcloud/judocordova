//
//  Receipt.h
//  JudoClient
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



#import <UIKit/UIKit.h>
#import "Helper.h"

@class Card;

/**
 *  This class helps describe and construct the transaction receipt objects for the consumer when you’re using a custom UI.
 */
@interface Receipt : NSObject

/**
 *  The unique id of the receipt.
 */
@property (nonatomic, assign) NSInteger receiptId;

/**
 *  If this is a receipt for a refunded transaction that the receipt is for, this will be either "Payment" or "Refund".
 */
@property (nonatomic, assign) NSInteger originalReceiptId;

/**
 *  The type of this Transaction
 */
@property (nullable, nonatomic, strong) NSString *transactionType;

/**
 *  The result of this transaction, this will be either be "Success" or "Declined".
 */
@property (nullable, nonatomic, strong) NSString *result;

/**
 *  A message detailing the result of the transaction.
 */
@property (nullable, nonatomic, strong) NSString *message;

/**
 *  The currency symbol used.
 */
@property (nullable, nonatomic, strong) NSString *currency;

/**
 *  The judo id of the account which processed the transaction.
 */
@property (nullable, nonatomic, strong) NSString *judoID;

/**
 *  The name of the merchant that processed the transaction.
 */
@property (nullable, nonatomic, strong) NSString *merchantName;

/**
 *  Describes how the transaction will appear on the customer credit card or bank statement.
 */
@property (nullable, nonatomic, strong) NSString *appearsOnStatementsAs;

/**
 *  Your reference for the consumer. This could be a unique customer id or device id.
 */
@property (nullable, nonatomic, strong) NSString *consumerRef;

/**
 *  Your unique reference for this transaction.
 */
@property (nullable, nonatomic, strong) NSString *paymentRef;

/**
 *  The date the transaction was posted.
 */
@property (nullable, nonatomic, strong) NSDate *createdAt;

/**
 *  A dictionary of key/values for any meta data that had been captured and submitted as part of a transaction.
 */
@property (nullable, nonatomic, strong) NSMutableDictionary *metaData;


/**
 *  Information about the card used in this transaction.
 */
@property (nullable, nonatomic, strong) Card *card;


/**
 *  If this is a receipt for a refunded transaction then this will indicate the amount of the original payment.
 */
@property (nonatomic, assign) JPAmount originalAmount;

/**
 *  This will show the total value of refunds made against the original payment.
 */
@property (nonatomic, assign) JPAmount refunds;

/**
 *  If this is a refund transaction, this will show the remaining balance of the original payment. You cannot refund more than the original payment.
 */
@property (nonatomic, assign) JPAmount netAmount;

/**
 *  The transaction value. This is always a positive value, even for refunds.
 */
@property (nonatomic, assign) JPAmount amount;

/**
 *  The partner service fee.
 */
@property (nonatomic, assign) JPAmount partnerServiceFee;


/**
 *  creates and returns an instance of Receipt
 *
 *  @param dict dictionary of key/values to initialize the receipt object with.
 *
 *  @return an instance of the Card class
 */
- (nonnull instancetype)initWithDictionary:(nonnull NSDictionary *)dict;


/**
 *  Returns a string with a formatted date.
 *
 *  @return formatted date string
 */
- (nullable NSString *)getFormattedDate;


/**
 *  Returns an image that shows whether the transaction was accepted or declined.
 *
 *  @return a UIImage object
 */
- (nullable UIImage *)receiptStatusImage;


@end
