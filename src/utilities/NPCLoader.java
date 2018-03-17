package utilities;

import controllers.Patrol;
import controllers.RandomMovement;
import objects.NPC;
import game.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * Manages all the NPC data to be used in the game.
 */
public class NPCLoader {
    private static TreeMap<Integer, TreeMap<Integer, List<NPC>>> NPCs = new TreeMap<>();

    /**
     * Loads all NPC data and stores in a map.
     */
    public static void loadNPCs(CyclicBarrier barrier) {
        try {
            /* School Hall G */
            NPCs.put(0, new TreeMap<>());
            NPCs.get(0).put(0, Arrays.asList(new NPC(16, 19, 1, Constants.DOWN_TILE, false, new RandomMovement(false)),
                    new NPC(1, 6, 3, Constants.LEFT_TILE, false, null),
                    new NPC(4, 19, 20, 0, Constants.DOWN_TILE, false, new RandomMovement(false)),
                    new NPC(13, 7, 10, 0, Constants.DOWN_TILE, false, new RandomMovement(false))));
            NPCs.get(0).put(1, Arrays.asList(new NPC(4, 7, 10, 2, Constants.DOWN_TILE, false, new RandomMovement(true)),
                    new NPC(18, 16, 20, 4, Constants.DOWN_TILE, false, new RandomMovement(false))));
            /* School Hall 1F */
            NPCs.put(1, new TreeMap<>());
            NPCs.get(1).put(0, Arrays.asList(new NPC(4, 2, 2, Constants.UP_TILE, false, new RandomMovement(true)),
                    new NPC(12, 11, 4, Constants.UP_TILE, false, new RandomMovement(false)),
                    new NPC(2, 12, 10, 1, Constants.RIGHT_TILE, false, new RandomMovement(false))));
            NPCs.get(1).put(1, Collections.singletonList(
                    new NPC(2, 7, 20, 1, Constants.DOWN_TILE, false, new RandomMovement(false))));
            /* Design Tech Classroom */
            NPCs.put(2, new TreeMap<>());
            NPCs.get(2).put(0, Collections.singletonList(new NPC(9, 2, 5, Constants.DOWN_TILE, false, null)));
            NPCs.get(2).put(1, Arrays.asList(new NPC(2, 3, 3, Constants.DOWN_TILE, false, null),
                    new NPC(9, 2, 5, Constants.DOWN_TILE, false, null)));
            NPCs.get(2).put(2, Collections.singletonList(new NPC(4, 4, 10, 4, Constants.LEFT_TILE, false, null)));

            NPCs.get(2).put(3, Arrays.asList(new NPC(1, 4, 0, Constants.RIGHT_TILE, false, null),
                    new NPC(2, 3, 1, Constants.DOWN_TILE, false, null), new NPC(3, 3, 4, Constants.DOWN_TILE, false, null),
                    new NPC(5, 6, 2, Constants.DOWN_TILE, false, null), new NPC(3, 7, 3, Constants.RIGHT_TILE, false, null),
                    new NPC(5, 4, 5, Constants.DOWN_TILE, false, null)));

            NPCs.get(2).put(12, Arrays.asList(new NPC(9, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(11, 5, 1, Constants.UP_TILE, false, null), new NPC(13, 5, 4, Constants.UP_TILE, false, null),
                    new NPC(9, 7, 2, Constants.UP_TILE, false, null), new NPC(11, 7, 3, Constants.UP_TILE, false, null),
                    new NPC(9, 2, 5, Constants.DOWN_TILE, false, null)));
            /* Food Tech Classroom */
            NPCs.put(3, new TreeMap<>());
            NPCs.get(3).put(0, Collections.singletonList(new NPC(8, 2, 6, Constants.DOWN_TILE, false, null)));
            NPCs.get(3).put(1, Collections.singletonList(new NPC(8, 2, 6, Constants.DOWN_TILE, false, null)));
            NPCs.get(3).put(2, Collections.singletonList(new NPC(14, 5, 10, 3, Constants.UP_TILE, false, null)));

            NPCs.get(3).put(4, Arrays.asList(new NPC(13, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(13, 7, 1, Constants.UP_TILE, false, null), new NPC(14, 5, 4, Constants.UP_TILE, false, null),
                    new NPC(14, 9, 2, Constants.UP_TILE, false, null),
                    new NPC(13, 3, 6, Constants.DOWN_TILE, false, null)));

            NPCs.get(3).put(13, Arrays.asList(new NPC(8, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(4, 5, 1, Constants.UP_TILE, false, null), new NPC(8, 7, 4, Constants.UP_TILE, false, null),
                    new NPC(4, 7, 2, Constants.UP_TILE, false, null), new NPC(6, 7, 3, Constants.UP_TILE, false, null),
                    new NPC(8, 2, 6, Constants.DOWN_TILE, false, null)));
            /* 1F Classrooms */
            NPCs.put(4, new TreeMap<>());
            NPCs.get(4).put(0, Arrays.asList(new NPC(5, 2, 8, Constants.DOWN_TILE, false, null),
                    new NPC(16, 2, 9, Constants.DOWN_TILE, false, null)));
            NPCs.get(4).put(1, Arrays.asList(new NPC(5, 2, 8, Constants.DOWN_TILE, false, null),
                    new NPC(16, 2, 9, Constants.DOWN_TILE, false, null)));
            NPCs.get(4).put(2, Arrays.asList(new NPC(8, 7, 2, Constants.UP_TILE, false, null),
                    new NPC(16, 5, 4, Constants.UP_TILE, false, null)));

            NPCs.get(4).put(6, Arrays.asList(new NPC(6, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(7, 5, 1, Constants.UP_TILE, false, null), new NPC(8, 5, 4, Constants.UP_TILE, false, null),
                    new NPC(8, 7, 2, Constants.UP_TILE, false, null), new NPC(5, 2, 8, Constants.DOWN_TILE, false, null)));
            NPCs.get(4).put(7, Arrays.asList(new NPC(12, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(14, 5, 1, Constants.UP_TILE, false, null), new NPC(16, 5, 4, Constants.UP_TILE, false, null),
                    new NPC(12, 7, 2, Constants.UP_TILE, false, null),
                    new NPC(16, 2, 9, Constants.DOWN_TILE, false, null)));

            NPCs.get(4).put(14, Arrays.asList(new NPC(12, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(14, 5, 1, Constants.UP_TILE, false, null), new NPC(16, 5, 4, Constants.UP_TILE, false, null),
                    new NPC(12, 7, 2, Constants.UP_TILE, false, null), new NPC(16, 7, 3, Constants.UP_TILE, false, null),
                    new NPC(16, 2, 7, Constants.DOWN_TILE, false, null)));
            NPCs.get(4).put(15, Arrays.asList(new NPC(6, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(7, 5, 1, Constants.UP_TILE, false, null), new NPC(8, 5, 4, Constants.UP_TILE, false, null),
                    new NPC(8, 7, 2, Constants.UP_TILE, false, null), new NPC(7, 7, 3, Constants.UP_TILE, false, null),
                    new NPC(5, 2, 8, Constants.DOWN_TILE, false, null)));
            NPCs.get(4).put(16, Arrays.asList(new NPC(12, 5, 0, Constants.UP_TILE, false, null),
                    new NPC(14, 5, 1, Constants.UP_TILE, false, null), new NPC(16, 5, 4, Constants.UP_TILE, false, null),
                    new NPC(12, 7, 2, Constants.UP_TILE, false, null), new NPC(16, 7, 3, Constants.UP_TILE, false, null),
                    new NPC(16, 2, 9, Constants.DOWN_TILE, false, null)));
            NPCs.get(4).put(9, Arrays.asList(new NPC(8, 7, 2, Constants.UP_TILE, false, null),
                    new NPC(16, 5, 4, Constants.UP_TILE, false, null)));
            /* Canteen */
            NPCs.put(5, new TreeMap<>());
            NPCs.get(5).put(0, Arrays.asList(new NPC(8, 4, 30, 0, Constants.DOWN_TILE, false, null),
                    new NPC(9, 3, 30, 1, Constants.DOWN_TILE, false, new Patrol(new Point(9, 3), new Point(12, 3),
                            new Point(15, 3), new Point(12, 3)))));
            NPCs.get(5).put(1, Arrays.asList(new NPC(8, 4, 30, 0, Constants.DOWN_TILE, false, null),
                    new NPC(9, 3, 30, 1, Constants.DOWN_TILE, false, new Patrol(new Point(9, 3),new Point(12, 3),
                            new Point(15, 3), new Point(12, 3))),
                    new NPC(14, 7, 0, Constants.RIGHT_TILE, false, null),
                    new NPC(14, 8, 1, Constants.RIGHT_TILE, false, null),
                    new NPC(17, 7, 4, Constants.LEFT_TILE, false, null),
                    new NPC(23, 3, 2, Constants.LEFT_TILE, false, null),
                    new NPC(8, 7, 20, 2, Constants.RIGHT_TILE, false, null)));
            NPCs.get(5).put(2, Arrays.asList(new NPC(8, 4, 30, 0, Constants.DOWN_TILE, false, null),
                    new NPC(9, 3, 30, 1, Constants.DOWN_TILE, false, new Patrol(new Point(9, 3), new Point(12, 3),
                            new Point(15, 3), new Point(12, 3)))));
            /* Yard */
            NPCs.put(6, new TreeMap<>());
            NPCs.get(6).put(0, Arrays.asList(new NPC(34, 24, 0, Constants.DOWN_TILE, false, new RandomMovement(false)),
                    new NPC(27, 23, 7, Constants.DOWN_TILE, false, null)));
            NPCs.get(6).put(1, Arrays.asList(new NPC(27, 23, 7, Constants.DOWN_TILE, false, null),
                    new NPC(35, 26, 20, 3, Constants.DOWN_TILE, false, new RandomMovement(true))));
            NPCs.get(6).put(2, Collections.singletonList(
                    new NPC(34, 24, 0, Constants.DOWN_TILE, false, new RandomMovement(false))));
            NPCs.get(6).put(5, Arrays.asList(new NPC(32, 17, 0, Constants.UP_TILE, false, null),
                    new NPC(23, 12, 2, Constants.LEFT_TILE, false, null),
                    new NPC(27, 16, 7, Constants.LEFT_TILE, false, null)));
            NPCs.get(6).put(9, Arrays.asList(new NPC(32, 17, 0, Constants.UP_TILE, false, null),
                    new NPC(28, 18, 10, 4, Constants.LEFT_TILE, false, null)));
            /* Staffroom */
            NPCs.put(8, new TreeMap<>());
            NPCs.get(8).put(11, Arrays.asList(new NPC(7, 8, 5, Constants.DOWN_TILE, true, null),
                    new NPC(2, 14, 6, Constants.RIGHT_TILE, true, new Patrol(new Point(2, 14), new Point(4, 14),
                            new Point(6, 14), new Point(4, 14))),
                    new NPC(9, 11, 7, Constants.RIGHT_TILE, true, new Patrol(new Point(9, 11), new Point(14, 11))),
                    new NPC(10, 12, 9, Constants.DOWN_TILE, true, null),
                    new NPC(2, 9, 8, Constants.UP_TILE, true, new Patrol(new Point(2, 9)))));
            System.out.println("NPCs loaded");
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static TreeMap<Integer, List<NPC>> getNPCs(int index) { return new TreeMap<>(NPCs.get(index)); }
}
