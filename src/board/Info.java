package board;

public class Info {
    private String description;
    private Object value;

    public Info(String description, Object value){
        this.description = description;
        this.value = value;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
