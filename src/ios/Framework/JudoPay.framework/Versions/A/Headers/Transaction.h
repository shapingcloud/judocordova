//
//  Transaction.h
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



#import <Foundation/Foundation.h>
#import "Helper.h"

@class Card;
@class Consumer;
@class Receipt;
@class Address;

/**
 *  This class helps you initialise a new transaction object with data.
 */
@interface Transaction : NSObject

/**
 *  The judo id of the account which processed the transaction.
 */
@property (nullable, nonatomic, strong) NSString *judoID;

/**
 *  The amount to be processed in the transaction.
 */
@property (nonatomic, assign) JPAmount amount;

/**
 *  The receipt that is returned from the transaction.
 */
@property (nullable, nonatomic, strong) Receipt *receipt;

/**
 *  A dictionary of key/values that you can specify to attach to a transaction.
 */
@property (nullable, nonatomic, strong) NSDictionary *yourPaymentMetaData;

/**
 *  Your unique reference for this transaction.
 */
@property (nullable, nonatomic, strong) NSString *yourPaymentReference;

/**
 *  The currency used for this transaction.
 */
@property (nullable, nonatomic, strong) NSString *currency;

/**
 *  The consumer associated with this transaction.
 */
@property (nullable, nonatomic, strong) Consumer *consumer;

/**
 *  The card associated with this transaction.
 */
@property (nullable, nonatomic, strong) Card *card;

/**
 *  The address associated with this transaction.
 */
@property (nullable, nonatomic, strong) Address *address;


/**
 *  create a transaction with the given parameters
 *
 *  @param amount   The transaction amount to be processed.
 *  @param judoID   The judo id of the account which processed the transaction.
 *  @param payRef   Your unique reference for this transaction.
 *  @param metaData A dictionary of key/values that you can specify to attach to a transaction.
 *  @param symbol   The currency used for this transaction (default is GBP).
 *  @param cons     The consumer associated with this transaction.
 *  @param cardRef  The card associated with this transaction.
 *
 *  @return a instance of Transaction
 */
- (nonnull instancetype)initWithAmount:(JPAmount)amount
                             andJudoID:(nonnull NSString *)judoID
               andYourPaymentReference:(nullable NSString *)payRef
                andYourPaymentMetaData:(nullable NSDictionary *)metaData
                           andCurrency:(nullable NSString *)symbol
                           andConsumer:(nonnull Consumer *)cons
                               andCard:(nonnull Card *)cardRef;

@end
