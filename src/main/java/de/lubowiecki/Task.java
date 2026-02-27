package de.lubowiecki;

public class Task {

    private int id;

    private String name;

    private boolean done;

    public Task() {
    }

    public Task(int id, String name, boolean done) {
        this.id = id;
        this.name = name;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void toggleDone() {
        this.done = !done;
    }

    @Override
    public String toString() {
        return name + " (" + (done ? "erledigt" : "offen") + ")";
    }
}
