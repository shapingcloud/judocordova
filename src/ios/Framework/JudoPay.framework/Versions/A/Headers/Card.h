//
//  Card.h
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

@class Address;

/**
 * Card Class
 */
@interface Card : NSObject

//typedef enum { Visa=0, MasterCard, AMEX, Discover, DinersClub, Maestro, InvalidCard } CREDIT_CARD_TYPE __deprecated_msg("use JPCardType instead");

@property (nullable, nonatomic, strong) NSString *cardNumber, *expiryDate, *cv2, *cardToken, *lastFour, *startDate, *issueNumber, *postCode, *countryCode;
@property (nonatomic, assign) JPCardType cardType;
@property (nullable, nonatomic, strong) Address *address;


/**
 *  creates and returns an instance of Card
 *
 *  @param cardNumber a card number string
 *  @param expiryDate expiry date string
 *  @param cv2        cv2 descirption string
 *
 *  @return a newly created instance of Card
 */
- (nonnull instancetype)initWithCardNumber:(nullable NSString *)cardNumber andExpiry:(nullable NSString *)expiryDate andCV2:(nullable NSString *)cv2;


/**
 *  creates and returns an instance of Card
 *
 *  @param cardNumber   a card number string
 *  @param expiryDate   expiry date string
 *  @param cv2          cv2 descirption string
 *  @param startDate    start date string
 *  @param issueNumber  issue number string
 *
 *  @return a newly created instance of Card
 */
- (nonnull instancetype)initWithCardNumber:(nullable NSString *)cardNumber andExpiry:(nullable NSString *)expiryDate andCV2:(nullable NSString *)cv2 andStartDate:(nullable NSString *)startDate andIssueNumber:(nullable NSString *)issueNumber;


/**
 *  set new details with a dictionary
 *
 *  @param details the new details dictionary
 */
- (void)setDetailsWithDictionary:(nonnull NSDictionary *)details;


/**
 *  a valid test visa credit card for testing purposes
 *
 *  @return an instance of Card
 */
- (nonnull instancetype)initWithTestValidVisaCredit;


/**
 *  a valid visa debit card for testing purposes
 *
 *  @return an instance of Card
 */
- (nonnull instancetype)initWithTestValidVisaDebit;


/**
 *  a valid mastercard credit card for testing purposes
 *
 *  @return an instance of Card
 */
- (nonnull instancetype)initWithTestValidMastercardCredit;


/**
 *  a valid maestro card for testing purposes
 *
 *  @return an instance of Card
 */
- (nonnull instancetype)initWithTestValidMaestro;


/**
 *  an invalid visa credit card for testing purposes
 *
 *  @return an instance of Card
 */
- (nonnull instancetype)initWithTestInvalidVisaCredit;


/**
 *  an invalid visa debit card for testing purposes
 *
 *  @return for testing purposes
 */
- (nonnull instancetype)initWithTestInvalidVisaDebit;


/**
 *  an invalid mastercard credit for testing purposes
 *
 *  @return for testing purposes
 */
- (nonnull instancetype)initWithTestInvalidMastercardCredit;


@end