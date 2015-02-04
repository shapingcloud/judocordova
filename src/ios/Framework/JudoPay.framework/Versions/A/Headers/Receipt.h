//
//  Receipt.h
//  JudoClient
//
//  Created by Kieran Gutteridge on 07/03/2013.
//  Copyright (c) 2013 Alternative Payments. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "Card.h"
#import "Consumer.h"
#import "JudoSDKManager.h"


@interface Receipt : NSObject

-(id)initWithDictionary:(NSDictionary *)dict;

-(NSString *)getFormattedDate;
-(UIImage *)receiptStatusImage;


@property (nonatomic) int receiptId, originalReceiptId;
@property (nonatomic, strong) NSString* transactionType, *result, *message, *currency, *judoID, *merchantName, *appearsOnStatementsAs, *consumerRef, *paymentRef;
@property (nonatomic, strong) NSDate *createdAt;
@property (nonatomic, strong) NSMutableDictionary *metaData;
@property (nonatomic) double originalAmount, refunds, netAmount, amount, partnerServiceFee;
@property (nonatomic, strong) Card *card;



@end
