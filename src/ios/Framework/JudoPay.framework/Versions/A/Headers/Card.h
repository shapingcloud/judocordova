//
//  Card.h
//  JudoClient
//
//  Created by Kieran Gutteridge on 04/02/2013.
//  Copyright (c) 2013 Alternative Payments. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Address.h"

@interface Card : NSObject

typedef enum { Visa=0, MasterCard, AMEX, Discover, DinersClub, Maestro, InvalidCard } CREDIT_CARD_TYPE;

@property (nonatomic, strong) NSString *cardNumber, *expiryDate, *cv2, *cardToken, *lastFour, *startDate, *issueNumber, *postCode, *countryCode;
@property (nonatomic) CREDIT_CARD_TYPE cardType;
@property (nonatomic, strong) Address *address;

-(id)initWithCardNumber:(NSString *)cardNumber andExpiry:(NSString *)expiryDate andCV2:(NSString *)cv2;
-(id)initWithCardNumber:(NSString *)cardNumber andExpiry:(NSString *)expiryDate andCV2:(NSString *)cv2 andStartDate:(NSString *)startDate andIssueNumber:(NSString *)issueNumber;
-(void)setDetailsWithDictionary:(NSDictionary *)details;

-(Card *)initWithTestValidVisaCredit;
-(Card *)initWithTestValidVisaDebit;
-(Card *)initWithTestValidMastercardCredit;
-(Card *)initWithTestValidMaestro;
-(Card *)initWithTestInvalidVisaCredit;
-(Card *)initWithTestInvalidVisaDebit;
-(Card *)initWithTestInvalidMastercardCredit;

@end