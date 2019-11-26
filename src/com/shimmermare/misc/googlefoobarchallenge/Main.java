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

import com.shimmermare.misc.googlefoobarchallenge.level1.SolarDoomsday;
import com.shimmermare.misc.googlefoobarchallenge.level2.HeyIAlreadyDidThat;
import com.shimmermare.misc.googlefoobarchallenge.level2.PleasePassTheCodedMessages;
import com.shimmermare.misc.googlefoobarchallenge.level3.BombBaby;
import com.shimmermare.misc.googlefoobarchallenge.level3.FuelInjectionPerfection;
import com.shimmermare.misc.googlefoobarchallenge.level3.GrandestStaircaseOfThemAll;
import com.shimmermare.misc.googlefoobarchallenge.level4.BringingAGunToAGuardFight;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * I don't want to use unit testing frameworks because of nature of the challenge.
 */
public final class Main {
    public static void main(String[] args) {
        System.out.println("Level 1:");
        solarDoomsday();

        System.out.println("Level 2:");
        pleasePassTheCodedMessages();
        heyIAlreadyDidThat();

        System.out.println("Level 3:");
        bombBaby();
        grandestStaircaseOfThemAll();
        fuelInjectionPerfection();

        System.out.println("Level 4:");
        bringingAGunToAGuardFight();
    }

    private static void solarDoomsday() {
        System.out.println("    • Solar Doomsday");
        solarDoomsdayCase(12);
        solarDoomsdayCase(15324);
    }

    private static void solarDoomsdayCase(int area) {
        long start = System.nanoTime();
        int[] result = SolarDoomsday.solution(area);
        long finish = System.nanoTime();
        System.out.println("        Case: (" + area + ") Result: " + Arrays.toString(result)
                + " Time: " + (finish - start));
    }

    private static void pleasePassTheCodedMessages() {
        System.out.println("    • Please Pass the Coded Messages");
        pleasePassTheCodedMessagesCase(new int[]{3, 1, 4, 1});
        pleasePassTheCodedMessagesCase(new int[]{3, 1, 4, 1, 5, 9});
    }

    private static void pleasePassTheCodedMessagesCase(int[] l) {
        long start = System.nanoTime();
        int result = PleasePassTheCodedMessages.solution(l);
        long finish = System.nanoTime();
        System.out.println("        Case: (" + Arrays.toString(l) + ") Result: " + result + " Time: " + (finish - start));
    }

    private static void heyIAlreadyDidThat() {
        System.out.println("    • Hey, I Already Did That!");
        heyIAlreadyDidThatCase("1211", 10);
        heyIAlreadyDidThatCase("210022", 3);
    }

    private static void heyIAlreadyDidThatCase(String n, int b) {
        long start = System.nanoTime();
        int result = HeyIAlreadyDidThat.solution(n, b);
        long finish = System.nanoTime();
        System.out.println("        Case: (\"" + n + "\"" + b + ") Result: " + result + " Time: " + (finish - start));
    }

    private static void bombBaby() {
        System.out.println("    • Bomb, Baby!");
        bombBabyCase("2", "1");
        bombBabyCase("4", "7");
        bombBabyCase(BigInteger.TEN.pow(50).subtract(BigInteger.ONE).toString(), BigInteger.TEN.pow(50).toString());
    }

    private static void bombBabyCase(String m, String f) {
        long start = System.nanoTime();
        String result = BombBaby.solution(m, f);
        long finish = System.nanoTime();
        System.out.println("        Case: (\"" + m + "\", \"" + f + "\") Result: " + result + " Time: " + (finish - start));
    }

    private static void grandestStaircaseOfThemAll() {
        System.out.println("    • The Grandest Staircase Of Them All");
        grandestStaircaseOfThemAllCase(3);
        grandestStaircaseOfThemAllCase(200);
    }

    private static void grandestStaircaseOfThemAllCase(int n) {
        long start = System.nanoTime();
        int result = GrandestStaircaseOfThemAll.solution(n);
        long finish = System.nanoTime();
        System.out.println("        Case: (" + n + ") Result: " + result + " Time: " + (finish - start));
    }

    private static void fuelInjectionPerfection() {
        System.out.println("    • Fuel Injection Perfection");
        fuelInjectionPerfectionCase("4");
        fuelInjectionPerfectionCase("15");
        fuelInjectionPerfectionCase(BigInteger.TEN.pow(309).toString());
    }

    private static void fuelInjectionPerfectionCase(String num) {
        long start = System.nanoTime();
        int result = FuelInjectionPerfection.solution(num);
        long finish = System.nanoTime();
        System.out.println("        Case: (" + num + ") Result: " + result + " Time: " + (finish - start));
    }

    private static void bringingAGunToAGuardFight() {
        System.out.println("    • Bringing a Gun to a Guard Fight");
        bringingAGunToAGuardFightCase(new int[]{3, 2}, new int[]{1, 1}, new int[]{2, 1}, 4);
        bringingAGunToAGuardFightCase(new int[]{4, 4}, new int[]{1, 1}, new int[]{3, 3}, 12);
        bringingAGunToAGuardFightCase(new int[]{300, 275}, new int[]{150, 150}, new int[]{185, 100}, 500);
        bringingAGunToAGuardFightCase(new int[]{500, 500}, new int[]{100, 200}, new int[]{450, 350}, 1000);
        bringingAGunToAGuardFightCase(new int[]{1250, 1250}, new int[]{1, 1}, new int[]{1249, 1249}, 10000);
    }

    private static void bringingAGunToAGuardFightCase(int[] dimensions, int[] yourPos, int[] guardPos, int maxDistance) {
        long start = System.nanoTime();
        int result = BringingAGunToAGuardFight.solution(dimensions, yourPos, guardPos, maxDistance);
        long finish = System.nanoTime();
        System.out.println("        Case: (" + Arrays.toString(dimensions) + ", " + Arrays.toString(yourPos) + ", "
                + Arrays.toString(guardPos) + ", " + maxDistance + ") Result: " + result + " Time: " + (finish - start));
    }
}