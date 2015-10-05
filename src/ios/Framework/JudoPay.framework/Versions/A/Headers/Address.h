//
//  Address.h
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
 *  The Address class is designed to help you handle and construct postal addresses on your custom user interface.
 */
@interface Address : NSObject

/**
 *  The first line of the address.
 */
@property (nullable, nonatomic, strong) NSString *line1;

/**
 *  The second line of the address.
 */
@property (nullable, nonatomic, strong) NSString *line2;

/**
 *  The third line of the address.
 */
@property (nullable, nonatomic, strong) NSString *line3;

/**
 *  The postal town of the address.
 */
@property (nullable, nonatomic, strong) NSString *town;

/**
 *  The full post code associated with the address.
 */
@property (nullable, nonatomic, strong) NSString *postcode;

/**
 *  The country of the address
 */
@property (nullable, nonatomic, strong) NSString *country;

@end
