package edu.northeastern.cs5500.starterbot.model;

import javax.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Education implements Model {
    private ObjectId id;
    @Nonnull private String Name;
}
