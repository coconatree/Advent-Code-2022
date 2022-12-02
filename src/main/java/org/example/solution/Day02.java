package org.example.solution;

import org.example.util.Runner;
import org.example.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Day02 implements Runner {

    private static final String INPUT_FILE_PATH = "src/main/resources/day_02/advent_code_day_02_input.txt";


    static class Round {

        private final char cipher;
        private final char opponentsMove;

        private char calculatedMove;

        public Round(List<Character> moves) {
            this.cipher         = moves.get(1);
            this.opponentsMove  = moves.get(0);
        }

        private int getMovePoint(char ch) {
            return switch (ch) {
                case 'X' -> 1;
                case 'Y' -> 2;
                case 'Z' -> 3;
                default  -> 0;
            };
        }

        public int calculateRoundScore() {
            return this.playMove(this.cipher) + this.getMovePoint(this.cipher);
        }

        private int playMove(char ch) {
            return switch (ch) {
                case 'X' -> calculateRoundPoint(
                        (c) -> c == 'C',
                        (c) -> c == 'A'
                );
                case 'Y' -> calculateRoundPoint(
                        (c) -> c == 'A',
                        (c) -> c == 'B'
                );
                case 'Z' -> calculateRoundPoint(
                        (c) -> c == 'B',
                        (c) -> c == 'C'
                );
                default -> 0;
            };
        }

        public int calculateRoundScoreWithCalculatedMoves() {
            this.calculateMove();
            return this.playMove(calculatedMove) + this.getMovePoint(this.calculatedMove);
        }

        private void calculateMove() {
            this.calculatedMove = switch (this.cipher) {
                case 'X' -> calculateMoveHelper(
                        this.opponentsMove == 'A' ? Character.valueOf('Z') : Character.valueOf(' '),
                        this.opponentsMove == 'B' ? Character.valueOf('X') : Character.valueOf(' '),
                        this.opponentsMove == 'C' ? Character.valueOf('Y') : Character.valueOf(' ')

                );
                case 'Y' -> calculateMoveHelper(
                        this.opponentsMove == 'A' ? Character.valueOf('X') : Character.valueOf(' '),
                        this.opponentsMove == 'B' ? Character.valueOf('Y') : Character.valueOf(' '),
                        this.opponentsMove == 'C' ? Character.valueOf('Z') : Character.valueOf(' ')
                );
                case 'Z' -> calculateMoveHelper(
                        this.opponentsMove == 'A' ? Character.valueOf('Y') : Character.valueOf(' '),
                        this.opponentsMove == 'B' ? Character.valueOf('Z') : Character.valueOf(' '),
                        this.opponentsMove == 'C' ? Character.valueOf('X') : Character.valueOf(' ')
                );
                default -> throw new IllegalStateException("Unexpected value: " + this.cipher);
            };
        }

        private char calculateMoveHelper(
                Character supplierX,
                Character supplierY,
                Character supplierZ
        )
        {
            if (!(supplierX == ' ')) {
                return supplierX;
            }
            if (!(supplierY == ' ')) {
                return supplierY;
            }
            if (!(supplierZ == ' ')) {
                return supplierZ;
            }
            throw new IllegalArgumentException("All of the suppliers can't b empty characters");
        }

        private int calculateRoundPoint(
                Predicate<Character> predicateWin,
                Predicate<Character> predicateDraw
        )
        {
            if (predicateWin.test(this.opponentsMove)) {
                return ResultStates.WIN.value();
            }
            if (predicateDraw.test(this.opponentsMove)) {
                return ResultStates.DRAW.value();
            }
            return ResultStates.LOSE.value();
        }

        enum ResultStates {
            WIN,
            LOSE,
            DRAW;

            public int value() {
                return switch (this) {
                    case WIN  -> 6;
                    case DRAW -> 3;
                    case LOSE -> 0;
                };
            }
        }

        @Override
        public String toString() {
            return String.format(
                    "[ Round - Players Move : %s - Opponents Move : %s]",
                    this.cipher,
                    this.opponentsMove
            );
        }
    }

    public void testQuestion01Solution() {

        List<Round> testRounds = List.of(
                new Round(List.of('A', 'Y')),
                new Round(List.of('B', 'X')),
                new Round(List.of('C', 'Z'))
        );

        Util.log(Util.LogLevel.INFO, String.format("Test rounds: %s", testRounds));

        int result = testRounds.stream()
                .map(Round::calculateRoundScore)
                .reduce(0, Integer::sum);

        Util.log(Util.LogLevel.INFO, String.format("Found: %s - Result: %s", result, 15));
        assert result == 15;
    }


    @Override
    public void run() {

        testQuestion01Solution();

        List<String> fileAsString = Util.readFileToString(INPUT_FILE_PATH);

        int question01Result = solveQuestion01(fileAsString);

        Util.log(Util.LogLevel.INFO, String.format("Question 01 result: %s", question01Result));

        int question02Result = solveQuestion02(fileAsString);

        Util.log(Util.LogLevel.INFO, String.format("Question 02 result: %s", question02Result));
    }

    private int solveQuestion01(List<String> collection) {
        return collection
                .stream()
                .map(Day02::splitLine)
                .map(Day02::convertToCharacter)
                .map(Round::new)
                .map(Round::calculateRoundScore)
                .reduce(0, Integer::sum);
    }

    private int solveQuestion02(List<String> collection) {
        return collection
                .stream()
                .map(Day02::splitLine)
                .map(Day02::convertToCharacter)
                .map(Round::new)
                .map(Round::calculateRoundScoreWithCalculatedMoves)
                .reduce(0, Integer::sum);
    }

    private static String[] splitLine(String line) {
        return line.split(" ");
    }

    private static List<Character> convertToCharacter(String[] arr) {
        return Arrays.stream(arr).map(elm -> elm.charAt(0)).toList();
    }
}
