//
//  Consumer.h
//  JudoPayAPI
//
//  Created by Kieran Gutteridge on 27/06/2013.
//  Copyright (c) 2013 Alternative Payments.. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Consumer : NSObject


-(id)initWithConsumerReference:(NSString *)consumerReference;


@property (nonatomic, strong) NSString *consumerToken, *yourConsumerReference, *mobileNumber, *emailAddress;

@end
