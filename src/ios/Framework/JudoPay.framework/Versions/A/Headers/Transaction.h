//
//  Transaction.h
//  JudoClient
//
//  Created by Kieran Gutteridge on 06/02/2013.
//  Copyright (c) 2013 Alternative Payments. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>
#import "Receipt.h"
#import "Consumer.h"
#import "Card.h"

@interface Transaction : NSObject

@property (nonatomic, strong) NSString *judoID;
@property (nonatomic) float amount;
@property (nonatomic, strong) Receipt *receipt;
@property (nonatomic, strong) NSDictionary *yourPaymentMetaData;
@property (nonatomic, strong) NSString *yourPaymentReference;
@property (nonatomic, strong) NSString *currency;
@property (nonatomic, strong) Consumer *consumer;
@property (nonatomic, strong) Card *card;

-(id)initWithAmount:(float)amount andJudoID:(NSString *)judoID andYourPaymentReference:(NSString *)payRef andYourPaymentMetaData:(NSDictionary *)metaData andCurrency:(NSString *)symbol andConsumer:(Consumer *)cons andCard:(Card *)cardRef;


@end
