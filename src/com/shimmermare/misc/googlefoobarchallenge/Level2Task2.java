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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

//Depth-first search task
public class Level2Task2 {
    public static int solution(String n, int b) {
        if (n.length() < 2 || n.length() > 9)
            throw new IllegalArgumentException("Length of n must be between 2 and 9 (inclusive)");
        if (b < 2 || b > 10)
            throw new IllegalArgumentException("Base b must be between 2 and 10 (inclusive)");

        Deque<String> ids = new ArrayDeque<>();
        ids.addLast(n);

        while (true) {
            String last = ids.peekLast();
            String next = nextId(last, b);
            ids.addLast(next);
            int cycleLength = searchForCycle(ids);
            if (cycleLength > 0) {
                return cycleLength;
            }
        }
    }

    private static String nextId(String n, int b) {
        char[] chars = n.toCharArray();

        List<Character> ascending = new ArrayList<>(chars.length);
        for (char c : chars) ascending.add(c);
        ascending.sort(Character::compareTo);
        List<Character> descending = new ArrayList<>(chars.length);
        for (char c : chars) descending.add(c);
        descending.sort(Collections.reverseOrder(Character::compareTo));

        StringBuilder ascendingBuilder = new StringBuilder();
        for (char c : ascending) ascendingBuilder.append(c);
        String yText = ascendingBuilder.toString();
        int y = Integer.parseInt(yText, b);

        StringBuilder descendingBuilder = new StringBuilder();
        for (char c : descending) descendingBuilder.append(c);
        String xText = descendingBuilder.toString();
        int x = Integer.parseInt(xText, b);

        //Don't forget that format is localized
        int z = x - y;

        String zText = Integer.toString(z, b);
        StringBuilder zBuilder = new StringBuilder();
        for (int i = 0; i < n.length() - zText.length(); i++) zBuilder.append('0');
        zText = zBuilder.append(zText).toString();
        return zText;
    }

    private static int searchForCycle(Deque<String> ids) {
        Iterator<String> idsIterator = ids.descendingIterator();
        String last = idsIterator.next();

        int cycleLength = 0;
        boolean cycled = false;
        while (idsIterator.hasNext()) {
            String id = idsIterator.next();
            cycleLength++;
            if (id.equals(last)) {
                cycled = true;
                break;
            }
        }

        return cycled ? cycleLength : 0;
    }
}
