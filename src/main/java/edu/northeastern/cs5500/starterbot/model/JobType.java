package edu.northeastern.cs5500.starterbot.model;

import javax.annotation.Nonnull;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class JobType implements Model {
    private ObjectId id;
    @Nonnull private String label;
    @Nonnull private String displayName;
}
