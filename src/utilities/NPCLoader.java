package utilities;

import controllers.Patrol;
import controllers.RandomMovement;
import objects.NPC;
import game.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by lmweav on 25/11/2017.
 */
public class NPCLoader {

    public static Map<Integer, List<List<NPC>>> NPCs = new TreeMap<>();

    static {
        /* School Hall G */
        NPCs.put(0, new ArrayList<>());
        NPCs.get(0).add(Arrays.asList(new NPC(16, 19, 1, Constants.DOWN, new RandomMovement(false)),
                new NPC(1, 6, 3, Constants.LEFT, null), new NPC(4, 19, 20, 0, Constants.DOWN, new RandomMovement(false)),
                new NPC(13, 7, 10, 0, Constants.DOWN, new RandomMovement(false))));
        NPCs.get(0).add(Arrays.asList(new NPC(4, 7, 10, 2, Constants.DOWN, new RandomMovement(true)),
                new NPC(18, 16, 20, 4, Constants.DOWN, new RandomMovement(false))));
        NPCs.get(0).add(Collections.emptyList());

        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());

        NPCs.get(0).add(Collections.emptyList());

        /* School Hall 1F */
        NPCs.put(1, new ArrayList<>());
        NPCs.get(1).add(Arrays.asList(new NPC(4, 2, 2, Constants.UP, new RandomMovement(true)),
                new NPC(12, 11, 4, Constants.UP, new RandomMovement(false)),
                new NPC(2, 12, 10, 1, Constants.RIGHT, new RandomMovement(false))));
        NPCs.get(1).add(Collections.singletonList(new NPC(2, 7, 20, 1, Constants.DOWN, new RandomMovement(false))));
        NPCs.get(1).add(Collections.emptyList());

        /* Design Tech Classroom */
        NPCs.put(2, new ArrayList<>());
        NPCs.get(2).add(Collections.singletonList(new NPC(9, 2, 5, Constants.DOWN, null)));
        NPCs.get(2).add(Arrays.asList(new NPC(2, 3, 3, Constants.DOWN, null), new NPC(9, 2, 5, Constants.DOWN, null)));
        NPCs.get(2).add(Collections.singletonList(new NPC(4, 4, 10, 4, Constants.LEFT, null)));

        NPCs.get(2).add(Arrays.asList(new NPC(1, 4, 0, Constants.RIGHT, null), new NPC(2, 3, 1, Constants.DOWN, null),
                new NPC(3, 3, 4, Constants.DOWN, null), new NPC(5, 6, 2, Constants.DOWN, null),
                new NPC(3, 7, 3, Constants.RIGHT, null), new NPC(5, 4, 5, Constants.DOWN, null)));

        /* Food Tech Classroom */
        NPCs.put(3, new ArrayList<>());
        NPCs.get(3).add(Collections.singletonList(new NPC(8, 2, 6, Constants.DOWN, null)));
        NPCs.get(3).add(Collections.singletonList(new NPC(8, 2, 6, Constants.DOWN, null)));
        NPCs.get(3).add(Collections.singletonList(new NPC(14, 5, 10, 3, Constants.UP, null)));

        NPCs.get(3).add(Collections.emptyList());
        NPCs.get(3).add(Arrays.asList(new NPC(13, 5, 0, Constants.UP, null), new NPC(13, 7, 1, Constants.UP, null),
                new NPC(14, 5, 4, Constants.UP, null), new NPC(14, 9, 2, Constants.UP, null),
                new NPC(13, 3, 6, Constants.DOWN, null)));

        /* 1F Classrooms */
        NPCs.put(4, new ArrayList<>());
        NPCs.get(4).add(Arrays.asList(new NPC(5, 2, 8, Constants.DOWN, null), new NPC(16, 2, 9, Constants.DOWN, null)));
        NPCs.get(4).add(Arrays.asList(new NPC(5, 2, 8, Constants.DOWN, null), new NPC(16, 2, 9, Constants.DOWN, null)));
        NPCs.get(4).add(Arrays.asList(new NPC(8, 7, 2, Constants.UP, null), new NPC(16, 5, 4, Constants.UP, null)));

        NPCs.get(4).add(Collections.emptyList());
        NPCs.get(4).add(Collections.emptyList());
        NPCs.get(4).add(Collections.emptyList());
        NPCs.get(4).add(Arrays.asList(new NPC(6, 5, 0, Constants.UP, null), new NPC(7, 5, 1, Constants.UP, null),
                new NPC(8, 5, 4, Constants.UP, null), new NPC(8, 7, 2, Constants.UP, null),
                new NPC(5, 2, 8, Constants.DOWN, null)));
        NPCs.get(4).add(Arrays.asList(new NPC(12, 5, 0, Constants.UP, null), new NPC(14, 5, 1, Constants.UP, null),
                new NPC(16, 5, 4, Constants.UP, null), new NPC(12, 7, 2, Constants.UP, null),
                new NPC(16, 2, 9, Constants.DOWN, null)));

        /* Canteen */
        NPCs.put(5, new ArrayList<>());
        NPCs.get(5).add(Arrays.asList(new NPC(8, 4, 30, 0, Constants.DOWN, null),
                new NPC(9, 3, 30, 1, Constants.DOWN, new Patrol(new Point(9, 3),new Point(13, 3), new Point(16, 3)))));
        NPCs.get(5).add(Arrays.asList(new NPC(8, 4, 30, 0, Constants.DOWN, null),
                new NPC(9, 3, 30, 1, Constants.DOWN, new Patrol(new Point(9, 3),new Point(13, 3), new Point(16, 3))),
                new NPC(14, 7, 0, Constants.RIGHT, null), new NPC(14, 8, 1, Constants.RIGHT, null),
                new NPC(17, 7, 4, Constants.LEFT, null), new NPC(23, 3, 2, Constants.LEFT, null),
                new NPC(8, 7, 20, 2, Constants.RIGHT, null)));
        NPCs.get(5).add(Arrays.asList(new NPC(8, 4, 30, 0, Constants.DOWN, null),
                new NPC(9, 3, 30, 1, Constants.DOWN, new Patrol(new Point(9, 3),new Point(13, 3), new Point(16, 3)))));

        /* Yard */
        NPCs.put(6, new ArrayList<>());
        NPCs.get(6).add(Arrays.asList(new NPC(34, 24, 0, Constants.DOWN, new RandomMovement(false)),
                new NPC(27, 23, 7, Constants.DOWN, null)));
        NPCs.get(6).add(Arrays.asList(new NPC(27, 23, 7, Constants.DOWN, null),
                new NPC(35, 26, 20, 3, Constants.DOWN, new RandomMovement(true))));
        NPCs.get(6).add(Collections.singletonList(new NPC(34, 24, 0, Constants.DOWN, new RandomMovement(false))));

        NPCs.get(6).add(Collections.emptyList());
        NPCs.get(6).add(Collections.emptyList());
        NPCs.get(6).add(Arrays.asList(new NPC(32, 17, 0, Constants.UP, null), new NPC(23, 12, 2, Constants.LEFT, null),
                new NPC(27, 16, 7, Constants.LEFT, null)));

        /* Bedroom */
        NPCs.put(7, new ArrayList<>());
        NPCs.get(7).add(Collections.emptyList());
        NPCs.get(7).add(Collections.emptyList());
        NPCs.get(7).add(Collections.emptyList());

        NPCs.get(7).add(Collections.emptyList());
        NPCs.get(7).add(Collections.emptyList());
        NPCs.get(7).add(Collections.emptyList());
        NPCs.get(7).add(Collections.emptyList());
        NPCs.get(7).add(Collections.emptyList());

        NPCs.get(7).add(Collections.emptyList());

    }
}
