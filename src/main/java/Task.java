public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    public abstract String getType();

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }


    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String toDataString() {
        switch (getType()) {
            case "T":
                return "T | " + (isDone() ? 1 : 0) + " | " + getDescription();
            case "D":
                return "D | " + (isDone() ? 1 : 0) + " | " + getDescription()
                        + " | " + ((Deadline) this).getBy();
            case "E":
                return "E | " + (isDone() ? 1 : 0) + " | " + getDescription()
                        + " | " + ((Event) this).getFrom() + "-" + ((Event) this).getTo();
            default:
                return "";
        }
    }


    public static Task fromDataString(String data) {
        String[] parts = data.split("\\|");
        String type = parts[0].trim();
        boolean done = parts[1].trim().equals("1");
        String desc = parts[2].trim();

        switch (type) {
            case "T": {
                Task t = new Todo(desc);
                if (done) t.markAsDone();
                return t;
            }
            case "D": {
                Task t = new Deadline(desc, parts[3].trim());
                if (done) t.markAsDone();
                return t;
            }
            case "E": {
                String[] times = parts[3].trim().split("-");
                Task t = new Event(desc, times[0], times[1]);
                if (done) t.markAsDone();
                return t;
            }
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
    }

}