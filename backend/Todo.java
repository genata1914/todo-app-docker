package backend;

public class Todo {
    private final int id;
    //FINAL E ZASHTOTO E CONSTANTA ID ZASHTOTO STOINOSTTA NE SE PROMENQ
    private String title;
    private boolean isCompleted;

    public Todo(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.isCompleted = completed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
