//
//  CustomUIValidationHelper.h
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



#import <UIKit/UIKit.h>
#import "Card.h"

@class ValidationHelper;

/**
 *  Delegate protocol definition for the Validation helper
 */
@protocol CustomUIValidationHelperDelegate <NSObject>

@optional


/**
 *  delegate method that is called when the credit card was saved
 *
 *  @param card the card that was saved
 */
- (void)creditCardSaved:(nullable Card *)card;


@end


/**
 *  This class provides various validation helper methods to validate card data entry for use with a custom UI.
 */
@interface ValidationHelper : NSObject

/**
 *  The credit card controller type.
 */
@property (nonatomic) NSInteger CreditCardControllerType;

/**
 *  The debit or credit card details.
 */
@property (nullable, nonatomic, strong) Card *judoCard;

/**
 *  Anonymous completion block to execute.
 */
@property (nullable, nonatomic, copy) void(^completionBlock)(BOOL success, Card * __nullable card);

/**
 *  A delegate for the Custom UI Validation Helper.
 */
@property (nullable, nonatomic, weak) id <CustomUIValidationHelperDelegate> delegate;


/**
 *  Constructs and initializes a Card with the specified parameters.
 *
 *  @param number The debit/credit card number. Sometime this is also known as the PAN number.
 *  @param expiry The expiry date of the card.
 *  @param cV2    The CV2/ CVV number for the card.
 *
 *  @return an instance of the Card class
 */
- (nonnull Card *)saveCardwithCardNumber:(nonnull NSString *)number andExpiry:(nonnull NSString*)expiry andCV2:(nonnull NSString *)cV2;


+ (JPCardType)ccType:(nonnull NSString *)proposedNumber;
+ (BOOL)isValidNumber:(nonnull NSString *)proposedNumber;
+ (BOOL)isLuhnValid:(nonnull NSString *)proposedNumber;
+ (BOOL)isStartDateValid:(nonnull NSString *)proposedDate;
+ (BOOL)isExpiryDateValid:(nonnull NSString *)proposedDate;
+ (BOOL)isCardTypeValid:(NSInteger)cardType;
+ (BOOL)canProcess:(nonnull NSString *)cardNumber;
+ (BOOL)isValidCard:(nonnull Card *)card;

+ (nonnull NSString *)formatForViewing:(nonnull NSString *)enteredNumber;
+ (nonnull NSString *)promptStringForType:(JPCardType)type justNumber:(BOOL)justNumber;

+ (NSUInteger)lengthOfStringForType:(JPCardType)type;
+ (NSUInteger)lengthOfFormattedStringForType:(JPCardType)type;
+ (NSUInteger)lengthOfFormattedStringTilLastGroupForType:(JPCardType)type;
+ (nonnull NSString *)ccvFormat:(JPCardType)type;

/**
 *  Returns an image (front) to use for the UI depending on the card type.
 *
 *  @param type the card type
 *
 *  @return a UIImage object
 */
+ (nonnull UIImage *)creditCardImage:(JPCardType)type;

/**
 *  Returns an image (back) to use for the UI depending on the card type.
 *
 *  @param type the card type
 *
 *  @return a UIImage object
 */
+ (nonnull UIImage *)creditCardBackImage:(JPCardType)type;

@end
