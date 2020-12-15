package suic;

/* Copyright (c) 2020 SizableShrimp.
 * This file is provided under version 3 of the GNU Lesser General Public License.
 * AdventOfCode2020
 * Copyright (C) 2020 SizableShrimp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.util.Arrays;
import java.util.function.Predicate;

// modified version of SizableShrimp's Main class
public class AdventCalender {

    private static final String BASE_PACKAGE = AdventCalender.class.getPackageName() + ".days.";
    public static final int YEAR = 2020;


    public static void main(String[] args) {
        run();
    }

    private static void run() {
        System.out.println("-----------------------------------------------------------");
        run(15);
    }

    private static void run(int dayOfMonth) {
        try {
            Class<?> clazz = Class.forName(BASE_PACKAGE + pad(dayOfMonth));
            System.out.println("Day " + dayOfMonth + " Solution");
            Puzzle<?, ?> puzzle = (Puzzle<?, ?>) clazz.getDeclaredConstructor().newInstance();
            System.out.println("Part 1 result = " + puzzle.solvePart1());
            System.out.println("Part 2 result = " + puzzle.solvePart2());
            System.out.println("-----------------------------------------------------------");

        } catch (ClassNotFoundException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("-----------------------------------------------------------");
    }

    private static void runAll() {
        for (int i = 1; i <= 25; i++) {
            run(i);
        }
    }

    private static void runRange(int start, int end) {
        for (int i = start; i <= end; i++) {
            run(i);
        }
    }

    public static String pad(int i) {
        return String.format("day%02d.Day%02d", i, i);
    }
}