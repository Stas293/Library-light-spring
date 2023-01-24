package org.springapp.util;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private Utils() {
    }

    public static List<Integer> getSequencePages(int total) {
        List<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            sequence.add(i);
        }
        return sequence;
    }
}
