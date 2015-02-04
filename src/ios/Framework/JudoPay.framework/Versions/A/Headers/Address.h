//
//  Address.h
//  JudoPayAPI
//
//  Created by Kieran Gutteridge on 27/06/2013.
//  Copyright (c) 2013 Alternative Payments.. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Address : NSObject

@property (nonatomic, strong) NSString *line1, *line2, *line3, *town, *postcode, *country;

@end
