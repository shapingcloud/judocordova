//
//  Consumer.h
//  JudoPayAPI
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

/**
 *  This class is designed to help you handle and define a consumer or a user of your application through unique variables.
 */
@interface Consumer : NSObject

/**
 *  An encrypted token that identifies the consumer on the judoPay API.
 */
@property (nullable, nonatomic, strong) NSString *consumerToken;

/**
 *  Your reference for the consumer. This could be a unique customer id or device id.
 */
@property (nullable, nonatomic, strong) NSString *yourConsumerReference;

/**
 *  The consumers mobile number. (optional)
 */
@property (nullable, nonatomic, strong) NSString *mobileNumber;

/**
 *  The consumers email address. (optional)
 */
@property (nullable, nonatomic, strong) NSString *emailAddress;

/**
 *  Used for initializing a new consumber object with a consumer reference.
 *
 *  @param consumerReference a given reference
 *
 *  @return returns an instance of the type Consumer
 */
- (nonnull instancetype)initWithConsumerReference:(nonnull NSString *)consumerReference NS_DESIGNATED_INITIALIZER;

@end
