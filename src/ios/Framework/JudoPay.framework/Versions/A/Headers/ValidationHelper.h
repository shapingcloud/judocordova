//
//  CustomUIValidationHelper.h
//  JudoPay
//
//  Created by swamosox on 29/05/2014.
//  Copyright (c) 2014 Alternative Payments. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Card.h"

@class ValidationHelper;

@protocol CustomUIValidationHelperDelegate <NSObject>

@optional

-(void)creditCardSaved:(Card *)card;


@end

@interface ValidationHelper : NSObject

@property (nonatomic) int CreditCardControllerType;
@property (nonatomic) Card *judoCard;

@property (nonatomic, copy) void(^completionBlock)(BOOL success, Card *card);

@property (nonatomic, weak) id <CustomUIValidationHelperDelegate> delegate;

-(Card *)saveCardwithCardNumber:(NSString *)number andExpiry:(NSString*)expiry andCV2:(NSString *)cV2;

+ (CREDIT_CARD_TYPE)ccType:(NSString *)proposedNumber;
+ (BOOL)isValidNumber:(NSString *)proposedNumber;
+ (BOOL)isLuhnValid:(NSString *)proposedNumber;
+ (BOOL)isStartDateValid:(NSString *)proposedDate;
+ (BOOL)isExpiryDateValid:(NSString *)proposedDate;
+ (BOOL)isCardTypeValid:(int)cardType;
+ (BOOL)canProcess:(NSString *)cardNumber;
+ (BOOL)isValidCard:(Card *)card;

+ (NSString *)formatForViewing:(NSString *)enteredNumber;
+ (NSString *)promptStringForType:(CREDIT_CARD_TYPE)type justNumber:(BOOL)justNumber;

+ (NSUInteger)lengthOfStringForType:(CREDIT_CARD_TYPE)type;
+ (NSUInteger)lengthOfFormattedStringForType:(CREDIT_CARD_TYPE)type;
+ (NSUInteger)lengthOfFormattedStringTilLastGroupForType:(CREDIT_CARD_TYPE)type;
+ (NSString *)ccvFormat:(CREDIT_CARD_TYPE)type;

+ (UIImage *)creditCardImage:(CREDIT_CARD_TYPE)type;
+ (UIImage *)creditCardBackImage:(CREDIT_CARD_TYPE)type;

@end
