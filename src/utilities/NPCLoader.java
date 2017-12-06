package utilities;

import objects.NPC;
import game.Constants;

import java.util.*;
import java.util.List;

/**
 * Created by lmweav on 25/11/2017.
 */
public class NPCLoader {
    //public static NPC[/*map*/][/*time*/] NPCs = new NPC[7][3];
    public static Map<Integer, List<List<NPC>>> NPCs = new TreeMap<>();

    static {
        /* School Hall G */
        NPCs.put(0, new ArrayList<>());
        NPCs.get(0).add(Arrays.asList(new NPC(16, 19, 1, Constants.DOWN), new NPC(1, 6, 3, Constants.LEFT)));
        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());

        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());
        NPCs.get(0).add(Collections.emptyList());


        /* School Hall 1F */
        NPCs.put(1, new ArrayList<>());
        NPCs.get(1).add(Arrays.asList(new NPC(4, 2, 2, Constants.UP), new NPC(12, 11, 4, Constants.UP)));
        NPCs.get(1).add(Collections.emptyList());
        NPCs.get(1).add(Collections.emptyList());

        NPCs.get(1).add(Collections.emptyList());
        NPCs.get(1).add(Collections.emptyList());
        NPCs.get(1).add(Collections.emptyList());
        NPCs.get(1).add(Collections.emptyList());
        NPCs.get(1).add(Collections.emptyList());

        /* Design Tech Classroom */
        NPCs.put(2, new ArrayList<>());
        NPCs.get(2).add(Collections.singletonList(new NPC(9, 2, 5, Constants.DOWN)));
        NPCs.get(2).add(Arrays.asList(new NPC(2, 3, 3, Constants.DOWN), new NPC(9, 2, 5, Constants.DOWN)));
        NPCs.get(2).add(Collections.emptyList());

        NPCs.get(2).add(Arrays.asList(new NPC(1, 4, 0, Constants.RIGHT), new NPC(2, 3, 1, Constants.DOWN),
                new NPC(3, 3, 4, Constants.DOWN), new NPC(5, 6, 2, Constants.DOWN), new NPC(3, 7, 3, Constants.RIGHT),
                new NPC(5, 4, 5, Constants.DOWN)));
        NPCs.get(2).add(Collections.emptyList());
        NPCs.get(2).add(Collections.emptyList());
        NPCs.get(2).add(Collections.emptyList());
        NPCs.get(2).add(Collections.emptyList());

        /* Food Tech Classroom */
        NPCs.put(3, new ArrayList<>());
        NPCs.get(3).add(Collections.singletonList(new NPC(8, 2, 6, Constants.DOWN)));
        NPCs.get(3).add(Collections.singletonList(new NPC(8, 2, 6, Constants.DOWN)));
        NPCs.get(3).add(Collections.singletonList(new NPC(14, 5, 1, Constants.UP)));

        NPCs.get(3).add(Collections.emptyList());
        NPCs.get(3).add(Arrays.asList(new NPC(13, 5, 0, Constants.UP), new NPC(13, 7, 1, Constants.UP),
                new NPC(14, 5, 4, Constants.UP), new NPC(14, 9, 2, Constants.UP), new NPC(13, 3, 6, Constants.DOWN)));
        NPCs.get(3).add(Collections.emptyList());
        NPCs.get(3).add(Collections.emptyList());
        NPCs.get(3).add(Collections.emptyList());

        /* 1F Classrooms */
        NPCs.put(4, new ArrayList<>());
        NPCs.get(4).add(Arrays.asList(new NPC(5, 2, 8, Constants.DOWN), new NPC(16, 2, 9, Constants.DOWN)));
        NPCs.get(4).add(Arrays.asList(new NPC(5, 2, 8, Constants.DOWN), new NPC(16, 2, 9, Constants.DOWN)));
        NPCs.get(4).add(Arrays.asList(new NPC(6, 5, 2, Constants.UP), new NPC(14, 7, 4, Constants.UP)));

        NPCs.get(4).add(Collections.emptyList());
        NPCs.get(4).add(Collections.emptyList());
        NPCs.get(4).add(Collections.emptyList());
        NPCs.get(4).add(Arrays.asList(new NPC(6, 5, 0, Constants.UP), new NPC(7, 5, 1, Constants.UP),
                new NPC(8, 5, 4, Constants.UP), new NPC(8, 7, 2, Constants.UP), new NPC(5, 2, 8, Constants.DOWN)));
        NPCs.get(4).add(Arrays.asList(new NPC(12, 5, 0, Constants.UP), new NPC(14, 5, 1, Constants.UP),
                new NPC(16, 5, 4, Constants.UP), new NPC(12, 7, 2, Constants.UP), new NPC(16, 2, 9, Constants.DOWN)));

        /* Canteen */
        NPCs.put(5, new ArrayList<>());
        NPCs.get(5).add(Collections.emptyList());
        NPCs.get(5).add(Arrays.asList(new NPC(14, 7, 0, Constants.RIGHT), new NPC(14, 8, 1, Constants.RIGHT),
                new NPC(17, 7, 4, Constants.LEFT), new NPC(23, 3, 2, Constants.LEFT)));
        NPCs.get(5).add(Collections.emptyList());

        NPCs.get(5).add(Collections.emptyList());
        NPCs.get(5).add(Collections.emptyList());
        NPCs.get(5).add(Collections.emptyList());
        NPCs.get(5).add(Collections.emptyList());
        NPCs.get(5).add(Collections.emptyList());

        /* Yard */
        NPCs.put(6, new ArrayList<>());
        NPCs.get(6).add(Arrays.asList(new NPC(29, 19, 0, Constants.DOWN), new NPC(22, 18, 7, Constants.DOWN)));
        NPCs.get(6).add(Collections.singletonList(new NPC(22, 18, 7, Constants.DOWN)));
        NPCs.get(6).add(Collections.singletonList(new NPC(29, 19, 0, Constants.DOWN)));

        NPCs.get(6).add(Collections.emptyList());
        NPCs.get(6).add(Collections.emptyList());
        NPCs.get(6).add(Arrays.asList(new NPC(27, 12, 0, Constants.UP), new NPC(18, 7, 2, Constants.LEFT),
                new NPC(22, 13, 7, Constants.LEFT)));
        NPCs.get(6).add(Collections.emptyList());
        NPCs.get(6).add(Collections.emptyList());

    }
}
