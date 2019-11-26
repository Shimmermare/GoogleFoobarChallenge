/*
 * MIT License
 *
 * Copyright (c) 2019 Shimmermare <shimmermare@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.shimmermare.misc.googlefoobarchallenge.level3;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public class FuelInjectionPerfection {
    private static final BigInteger THREE = BigInteger.valueOf(3L);

    public static int solution(String numStr) {
        BigInteger num = new BigInteger(numStr);

        int ops = 0;
        //Using bit-ops wherever possible. Div op of 309 digit number is fun for CPU, probably.
        //It's less steps if you divide numbers with more trailing zeros,
        // because the less you have the less times you can divide repeatedly.
        while (!num.equals(ONE)) {
            System.out.println(num.toString());
            if (!num.testBit(0)) {
                num = num.shiftRight(1);
            }
            else if (num.equals(THREE))
            {
                //The number 3 (0110) is special case because both
                // increment (0100) and decrement (0010) results in a power of 2,
                // so it's better to chose the smaller one.
                num = num.subtract(ONE);
            }
            else {
                BigInteger numIncrement = num.add(ONE);
                BigInteger numDecrement = num.subtract(ONE);
                num = numIncrement.getLowestSetBit() > numDecrement.getLowestSetBit() ? numIncrement : numDecrement;
            }
            ops++;
        }
        return ops;
    }
}
