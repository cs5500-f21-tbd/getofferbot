package edu.northeastern.cs5500.starterbot.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class JsonDistance {
    @SerializedName("distance")
    Double kilometers;
}
