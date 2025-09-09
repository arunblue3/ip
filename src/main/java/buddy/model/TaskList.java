package buddy.model;

import buddy.exception.BuddyException;

import java.util.*;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task delete(int oneBasedIndex) throws BuddyException {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new BuddyException("Invalid task index");
        }
        return tasks.remove(idx);
    }

    public Task get(int oneBasedIndex) throws BuddyException {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new BuddyException("Invalid task index");
        }
        return tasks.get(idx);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return tasks;
    }

    public String listAsString() {
        if (tasks.isEmpty()) {
            return "Your list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /** Returns all tasks whose description contains the keyword. */
    public List<Task> find(String keyword) {
        String k = keyword.toLowerCase();
        List<Task> matches = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(k)) {
                matches.add(t);
            }
        }
        return matches;
    }

    /** Formats matches into a numbered list. */
    public String findAsString(String keyword) {
        List<Task> matches = find(keyword);
        if (matches.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
