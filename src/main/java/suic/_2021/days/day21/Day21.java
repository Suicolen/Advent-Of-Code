package suic._2021.days.day21;

import suic._2021.Puzzle;
import suic.util.FileUtils;
import suic.util.LongPair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 implements Puzzle<Long> {


    private List<String> input;
    private Player p1;
    private Player p2;
    private State initialState;
    private final Dice d = new Dice();

    private final Map<Integer, Integer> frequencies = Map.of(
            3, 1,
            4, 3,
            5, 6,
            6, 7,
            7, 6,
            8, 3,
            9, 1
    ); // table of possibilities after 3 rolls (roll -> frequency)

    private final Map<State, LongPair> cache = new HashMap<>();
    private final LongPair p1Wins = new LongPair(1, 0);
    private final LongPair p2Wins = new LongPair(0, 1);


    @Override
    public void parse() {
        input = FileUtils.readResource(getClass().getSimpleName() + "Input.txt");
    }

    @Override
    public void init() {
        parse();
        String p1Line = input.get(0);
        String p2Line = input.get(1);
        int p1StartingPos = Integer.parseInt(p1Line.substring(p1Line.indexOf(':') + 2));
        int p2StartingPos = Integer.parseInt(p2Line.substring(p2Line.indexOf(':') + 2));
        p1 = new Player(p1StartingPos);
        p2 = new Player(p2StartingPos);
        initialState = new State(p1StartingPos, 0, p2StartingPos, 0, true);
    }

    @Override
    public Long solvePart1() {
        long result;
        while (true) {
            p1.advance(d);
            if (p1.score >= 1000) {
                result = p2.score * d.rolls;
                break;
            }

            p2.advance(d);
            if (p2.score >= 1000) {
                result = p1.score * d.rolls;
                break;
            }
        }

        return result;
    }

    @Override
    public Long solvePart2() {
        LongPair wins = countWins(initialState);
        return Math.max(wins.getLeft(), wins.getRight());
    }

    private LongPair countWins(State state) {
        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        LongPair result;

        if (state.p1Score >= 21) {
            result = p1Wins;
        } else if (state.p2Score >= 21) {
            result = p2Wins;
        } else {
            LongPair p = new LongPair(0, 0);
            frequencies.forEach((roll, frequency) -> {
                LongPair pair = countWins(play(state, roll));
                p.add(frequency * pair.getLeft(), frequency * pair.getRight());
            });
            result = p;
        }

        cache.put(state, result);
        return result;
    }

    private State play(State state, int roll) {
        if (state.p1Turn) {
            int p1Pos = ((state.p1Pos + roll) - 1) % 10 + 1;
            return new State(p1Pos, state.p1Score + p1Pos, state.p2Pos, state.p2Score, false);
        } else {
            int p2Pos = ((state.p2Pos + roll) - 1) % 10 + 1;
            return new State(state.p1Pos, state.p1Score, p2Pos, state.p2Score + p2Pos, true);
        }
    }

    private record State(int p1Pos, int p1Score, int p2Pos, int p2Score, boolean p1Turn) {
    }

    private static class Player {
        private int pos;
        private long score = 0;

        public Player(int pos) {
            this.pos = pos;
        }

        private void advance(Dice d) {
            pos += d.roll();
            pos = (pos - 1) % 10 + 1;
            score += pos;
        }
    }

    private static class Dice {

        private int value = 1;
        private int rolls = 0;

        private int roll() {
            int result = 0;
            for (int i = 0; i < 3; i++) {
                result += value;
                value++;
            }
            rolls += 3;
            return result;
        }
    }
}
