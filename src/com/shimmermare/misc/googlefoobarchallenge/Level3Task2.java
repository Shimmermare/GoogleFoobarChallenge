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

import java.util.Arrays;

//Distinct number of partitions
public class Level3Task2 {
    //We have exact borders so why not pre-calc it?
    //Commander Lambda will be happy with a twice as fast solution!.. 1 ms vs 2 ms lol
    //Starts from n=1
    private static final int[] nCount = new int[]{
            0, 0, 1, 1, 2,
            3, 4, 5, 7, 9,
            11, 14, 17, 21, 26,
            31, 37, 45, 53, 63,
            75, 88, 103, 121, 141,
            164, 191, 221, 255, 295,
            339, 389, 447, 511, 584,
            667, 759, 863, 981, 1112,
            1259, 1425, 1609, 1815, 2047,
            2303, 2589, 2909, 3263, 3657,
            4096, 4581, 5119, 5717, 6377,
            7107, 7916, 8807, 9791, 10879,
            12075, 13393, 14847, 16443, 18199,
            20131, 22249, 24575, 27129, 29926,
            32991, 36351, 40025, 44045, 48445,
            53249, 58498, 64233, 70487, 77311,
            84755, 92863, 101697, 111321, 121791,
            133183, 145577, 159045, 173681, 189585,
            206847, 225584, 245919, 267967, 291873,
            317787, 345855, 376255, 409173, 444792,
            483329, 525015, 570077, 618783, 671417,
            728259, 789639, 855905, 927405, 1004543,
            1087743, 1177437, 1274117, 1378303, 1490527,
            1611387, 1741520, 1881577, 2032289, 2194431,
            2368799, 2556283, 2757825, 2974399, 3207085,
            3457026, 3725409, 4013543, 4322815, 4654669,
            5010687, 5392549, 5802007, 6240973, 6711479,
            7215643, 7755775, 8334325, 8953855, 9617149,
            10327155, 11086967, 11899933, 12769601, 13699698,
            14694243, 15757501, 16893951, 18108417, 19406015,
            20792119, 22272511, 23853317, 25540981, 27342420,
            29264959, 31316313, 33504745, 35839007, 38328319,
            40982539, 43812109, 46828031, 50042055, 53466623,
            57114843, 61000703, 65139007, 69545357, 74236383,
            79229675, 84543781, 90198445, 96214549, 102614113,
            109420548, 116658615, 124354421, 132535701, 141231779,
            150473567, 160293887, 170727423, 181810743, 193582641,
            206084095, 219358314, 233451097, 248410815, 264288461,
            281138047, 299016607, 317984255, 338104629, 359444903,
            382075867, 406072421, 431513601, 458482687, 487067745
    };

    public static int solution(int n) {
        if (n < 3 || n > 200) throw new IllegalArgumentException("Staircase can be built only from 3 to 200 bricks");
        return nCount[n - 1];
    }

    public static int[] genPartitionCount(int n) {
        //Starting from n=0
        int[] count = new int[n + 1];
        count[0] = 1;

        for (int i = 1; i < n; i++) {
            for (int j = n; j > i - 1; j--) {
                count[j] = count[j] + count[j - i];
            }
        }

        //Not counting those consisting from one stair aka trivial partitions
        for (int i = 0; i < n; i++) count[i]--;

        //Shift to start from 1
        return Arrays.copyOfRange(count, 1, n + 1);
    }
}
