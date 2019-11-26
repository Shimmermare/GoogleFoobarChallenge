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

public class BombBaby {
    public static String solution(String mStr, String fStr) {
        //"No larger than 10^50" is w-a-ay out of long range. BigInts ftw.
        BigInteger m = new BigInteger(mStr);
        BigInteger f = new BigInteger(fStr);
        //Input validation because why not?
        if (m.compareTo(BigInteger.ZERO) <= 0 || f.compareTo(BigInteger.ZERO) <= 0) return "impossible";
        //BigInteger max = BigInteger.TEN.pow(50);
       // if (m.compareTo(max) >= 0 || f.compareTo(max) >= 0)
        //    throw new IllegalArgumentException();

        BigInteger genNum = countGen(m, f);
        return genNum.equals(BigInteger.valueOf(-1L)) ? "impossible" : String.valueOf(genNum);
    }

    //Reverse counting generations
    private static BigInteger countGen(BigInteger m, BigInteger f) {
        BigInteger gen = BigInteger.ZERO;
        while (true) {
            int oneCompM = m.compareTo(BigInteger.ONE);
            int oneCompF = f.compareTo(BigInteger.ONE);
            if (oneCompM == 0 && oneCompF == 0) break;
            if (oneCompM < 0 || oneCompF < 0) return BigInteger.valueOf(-1L);
            int mCompF = m.compareTo(f);
            if (mCompF == 0) return BigInteger.valueOf(-1L);
            //Had to use division instead of simple subtraction cycle for optimization
            else if (mCompF > 0) {
                if (oneCompF == 0) {
                    gen = gen.add(m).subtract(BigInteger.ONE);
                    m = BigInteger.ONE;
                } else {
                    BigInteger diff = m.subtract(f);
                    BigInteger mul = diff.divide(f).add(BigInteger.ONE);
                    gen = gen.add(mul);
                    m = m.subtract(f.multiply(mul));
                }
            } else {
                if (oneCompM == 0) {
                    gen = gen.add(f).subtract(BigInteger.ONE);
                    f = BigInteger.ONE;
                } else {
                    BigInteger diff = f.subtract(m);
                    BigInteger mul = diff.divide(m).add(BigInteger.ONE);
                    gen = gen.add(mul);
                    f = f.subtract(m.multiply(mul));
                }
            }
        }
        return gen;
    }
}
