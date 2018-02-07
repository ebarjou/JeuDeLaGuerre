package board;

public class Info {
    private String description;
    private int value;

    public Info(String description, int value){
        this.description = description;
        this.value = value;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
