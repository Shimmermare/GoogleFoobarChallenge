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

package com.shimmermare.misc.googlefoobarchallenge;

import java.util.ArrayList;
import java.util.List;

public class Level1 {
    public static int[] solution(int area) {
        //For some reason input validation lead to test failure
        //if (area < 1 || area > 100000)
        //    throw new IllegalArgumentException("Area must be between 1 and 100000 inclusive (" + area + ")");

        List<Integer> squares = new ArrayList<>();
        while (area > 0) {
            int nextSquareArea = nextSquareArea(area);
            area -= nextSquareArea;
            squares.add(nextSquareArea);
        }
        return squares.stream().mapToInt(i -> i).toArray();
    }

    private static int nextSquareArea(int area) {
        int sqrtNext = (int) Math.floor(Math.sqrt(area));
        return sqrtNext * sqrtNext;
    }
}
