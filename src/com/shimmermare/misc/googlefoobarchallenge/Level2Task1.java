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
import java.util.Arrays;
import java.util.List;

//Breadth-first search task
public class Level2Task1 {
    public static int solution(int[] l) {
        List<Integer> dividableBy3 = new ArrayList<>();

        //Blazing fast solution
        fastFindAllDividableBy3(dividableBy3, l, new int[l.length], 0);
        //Good readable solution
        //goodFindAllDividableBy3(dividableBy3, l);

        if (dividableBy3.isEmpty()) return 0;
        dividableBy3.sort(Integer::compareTo);
        return dividableBy3.get(dividableBy3.size() - 1);
    }

    private static void fastFindAllDividableBy3(List<Integer> dividableBy3, int[] l, int[] used, int numUsed) {
        //Sum of current selection
        //If sum of individual numbers of a number is dividable by 3, then the number itself is too
        int sum = 0;
        for (int i = 0; i < numUsed; i++) sum += l[used[i]];

        first:
        for (int i = 0; i < l.length; i++) {
            for (int j = 0; j < numUsed; j++) if (used[j] == i) continue first;

            int number = l[i];
            //If new selection is dividable by 3, add it to the list
            int newSum = sum + number;
            if (newSum != 0 && newSum % 3 == 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < numUsed; j++) sb.append(l[used[j]]);
                sb.append(number);
                dividableBy3.add(Integer.valueOf(sb.toString()));
            }

            if (numUsed + 1 < l.length) {
                used[numUsed] = i;
                fastFindAllDividableBy3(dividableBy3, l, used, numUsed + 1);
            }
        }
    }

    private static void goodFindAllDividableBy3(List<Integer> dividableBy3, int[] l) {
        Node root = new Node();
        buildTree(root, l, new boolean[l.length]);
        searchTree(dividableBy3, root);
    }

    private static void buildTree(Node parent, int[] l, boolean[] maskUsed) {
        for (int i = 0; i < l.length; i++) {
            if (maskUsed[i]) continue;

            Node node = new Node(parent, l[i]);
            parent.children.add(node);
            boolean[] newMaskUsed = Arrays.copyOf(maskUsed, maskUsed.length);
            newMaskUsed[i] = true;
            buildTree(node, l, newMaskUsed);
        }
    }

    private static void searchTree(List<Integer> dividableBy3, Node node) {
        //Sum of branch values
        int sum = 0;
        for (Node parent = node; parent.parent != null; parent = parent.parent) sum += parent.value;

        if (sum != 0 && sum % 3 == 0) {
            StringBuilder sb = new StringBuilder();
            for (Node parent = node; parent.parent != null; parent = parent.parent) sb.append(parent.value);
            dividableBy3.add(Integer.valueOf(sb.toString()));
        }

        node.children.forEach(n -> searchTree(dividableBy3, n));
    }

    private static class Node {
        Node parent;
        int value;
        List<Node> children = new ArrayList<>();

        Node() {
        }

        public Node(Node parent, int value) {
            this.parent = parent;
            this.value = value;
        }
    }
}
