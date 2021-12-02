package edu.northeastern.cs5500.starterbot.reader;

public class Education {
    private String Name;

    public Education() {}

    public Education(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
