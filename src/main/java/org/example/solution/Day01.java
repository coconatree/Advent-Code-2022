package org.example.solution;

import org.example.util.Runner;
import org.example.util.Util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day01 implements Runner {


    private static final String INPUT_FILE_PATH = "src/main/resources/day_01/advent_code_day_01_input.txt";

    @Override
    public void run() {
        List<String> result = Util.readFileToString(INPUT_FILE_PATH);

        Util.log(Util.LogLevel.INFO, String.format("Result is : %s", result));

        List<Integer> calories = new LinkedList<>();

        int total = 0;

        for (String item : result) {
            if (item.equals("")) {
                calories.add(total);
                total = 0;
            }
            else {
                total += Integer.parseInt(item);
            }
        }

        Util.log(Util.LogLevel.INFO, String.format("Result is : %s", calories));

        assert calories.size() == result.stream().filter(item -> item.equals("")).toList().size();

        Collections.sort(calories);

        Util.log(Util.LogLevel.INFO, String.format("Elf with the highest amount of calories has: %s", calories.get(calories.size() - 1)));

        int topThree = sumTopThreeCalories(calories);

        Util.log(Util.LogLevel.INFO, String.format("Sum of the calories carried by top three elf is: %s", topThree));
    }

    private int sumTopThreeCalories(List<Integer> calories) {
        int size = calories.size();
        return calories.get(size - 1) + calories.get(size - 2) + calories.get(size - 3);
    }
}
