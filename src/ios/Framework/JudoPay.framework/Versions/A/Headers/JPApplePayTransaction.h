//
//  JPApplePayTransaction.h
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
#import <PassKit/PassKit.h>

@class Receipt;
@class Consumer;
@class Card;
@class PKPayment;

/**
 *  Apple Pay Transaction Class
 */
@interface JPApplePayTransaction : NSObject

@property (nullable, nonatomic, strong) NSString *judoID; // do we need this? Probably has to be associated with a merchant ID somewhere so could get it from that server-side.
@property (nullable, nonatomic, strong) NSDecimalNumber *amount; // should be in PKPayment data.
@property (nullable, nonatomic, strong) Receipt *receipt;
@property (nullable, nonatomic, strong) NSDictionary *yourPaymentMetaData;
@property (nullable, nonatomic, strong) NSString *yourPaymentReference;
@property (nullable, nonatomic, strong) NSString *currency; // should be in PKPayment data.
@property (nullable, nonatomic, strong) Consumer *consumer;
@property (nullable, nonatomic) ABRecordRef address; // should be in PKPayment data.
@property (nullable, nonatomic, strong) PKPayment *pkPayment;


/**
 *
 *  create a transaction with the given parameters
 *
 *  @param payment  a PKPayment object
 *  @param amount   the amount of the transaction
 *  @param judoID   the judoID String
 *  @param payRef   the pay reference string
 *  @param metaData the meta data dictionary
 *  @param symbol   the currency Code unicode string (default is GBP)
 *  @param cons     the consumer
 *
 *  @return a instance of JPApplePayTransaction
 */
- (nonnull instancetype)initWithPayment:(nonnull PKPayment *)payment
                              andAmount:(nonnull NSDecimalNumber *)amount
                              andJudoID:(nonnull NSString *)judoID
                andYourPaymentReference:(nullable NSString *)payRef
                 andYourPaymentMetaData:(nullable NSDictionary *)metaData
                            andCurrency:(nullable NSString *)symbol
                            andConsumer:(nullable Consumer *)cons;

@end
