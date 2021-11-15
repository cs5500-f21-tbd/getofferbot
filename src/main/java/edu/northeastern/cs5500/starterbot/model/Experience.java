package edu.northeastern.cs5500.starterbot.model;

import java.util.HashMap;
import java.util.Map;
import lombok.*;

/**
 * Zimeng (Parker) Xie, CS 5500, Fall 2021 A class that defines possible experience levels of a job
 * posting, in lieu of an enum
 */
@Data
@NoArgsConstructor
public class Experience {

    // A static map to provide conversion from experience name to experience ID per suggestion from
    // Alex (in "Help")
    private static Map<String, Integer> expToId = new HashMap<>();

    static {
        expToId.put("unknown", -1);
        expToId.put("intern", 0);
        expToId.put("entry", 1);
        expToId.put("mid", 2);
        expToId.put("senior", 3);
    }

    @NonNull private Integer id;

    /**
     * Returns an Experience object by providing experience level as a string
     *
     * @param name experience level as a string
     * @return its corresponding object
     */
    public static Experience getExperienceByName(String name) {
        Experience out = new Experience();
        out.setId(expToId.get(name));
        return out;
    }
}
