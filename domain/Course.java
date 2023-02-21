package domain;

public class Course {
    
    //Class attributes
    private String name;
    private String topic;
    private String introduction;
    private String level;

    //Constructor
    public Course(String name, String topic, String introduction, String level){
        this.name = name;
        this.topic = topic;
        this.introduction = introduction;
        this.level = level;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getLevel() {
        return level;
    }

}
