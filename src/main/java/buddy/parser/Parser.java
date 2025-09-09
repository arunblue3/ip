package buddy.parser;

import buddy.exception.BuddyException;
import buddy.exception.EmptyDescriptionException;
import buddy.exception.UnknownCommandException;
import buddy.model.*;
import buddy.storage.Storage;
import buddy.ui.Ui;

public class Parser {

    /*
     * Re-use notice:
     * I switched this parsing logic to use cmd.substring(...) after observing this approach in other implementations.
     * Previously I rebuilt the command using StringBuilder. No code was copied; only the idea.
     */

    public static boolean handle(String input, TaskList tasks, Ui ui, Storage storage) throws BuddyException {
        String cmd = input.trim();
        if (cmd.isEmpty()) {
            return false;
        }

        if (cmd.equals("bye")) {
            storage.save(tasks.asList());
            ui.showGoodbye();
            return true;
        }

        if (cmd.equals("list")) {
            ui.showMessage(tasks.listAsString());
            return false;
        }

        if (cmd.startsWith("mark ")) {
            int idx = Integer.parseInt(cmd.substring(5).trim());
            Task t = tasks.get(idx);
            t.markAsDone();
            ui.showMessage("Nice! I've marked this task as done:\n  " + t);
            return false;
        }

        if (cmd.startsWith("unmark ")) {
            int idx = Integer.parseInt(cmd.substring(7).trim());
            Task t = tasks.get(idx);
            t.unmark();
            ui.showMessage("OK, I've marked this task as not done yet:\n  " + t);
            return false;
        }

        if (cmd.startsWith("delete ")) {
            int idx = Integer.parseInt(cmd.substring(7).trim());
            Task removed = tasks.delete(idx);
            ui.showMessage("Noted. I've removed this task:\n  " + removed + "\nNow you have " + tasks.size() + " tasks in the list.");
            return false;
        }

        if (cmd.startsWith("todo")) {
            String desc = cmd.length() > 4 ? cmd.substring(4).trim() : "";
            if (desc.isEmpty()) {
                throw new EmptyDescriptionException("todo");
            }
            Task t = new Todo(desc);
            tasks.add(t);
            ui.showMessage("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
            return false;
        }

        if (cmd.startsWith("deadline")) {
            String rest = cmd.substring("deadline".length()).trim();
            int byIdx = rest.lastIndexOf("/by");
            if (byIdx < 0) {
                throw new BuddyException("Usage: deadline <desc> /by <date>");
            }
            String desc = rest.substring(0, byIdx).trim();
            String by = rest.substring(byIdx + 3).trim();
            if (desc.isEmpty()) {
                throw new EmptyDescriptionException("deadline");
            }
            if (by.isEmpty()) {
                throw new BuddyException("Deadline date is missing. Usage: deadline <desc> /by <date>");
            }
            Task t = new Deadline(desc, by);
            tasks.add(t);
            ui.showMessage("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
            return false;
        }

        if (cmd.startsWith("event")) {
            String rest = cmd.substring("event".length()).trim();
            int fromIdx = rest.lastIndexOf("/from");
            int toIdx = rest.lastIndexOf("/to");
            if (fromIdx < 0 || toIdx < 0 || toIdx < fromIdx) {
                throw new BuddyException("Usage: event <desc> /from <from> /to <to>");
            }
            String desc = rest.substring(0, fromIdx).trim();
            String from = rest.substring(fromIdx + 5, toIdx).trim();
            String to = rest.substring(toIdx + 3).trim();
            if (desc.isEmpty()) {
                throw new EmptyDescriptionException("event");
            }
            if (from.isEmpty()) {
                throw new BuddyException("Event start time is missing. Usage: event <desc> /from <from> /to <to>");
            }
            if (to.isEmpty()) {
                throw new BuddyException("Event end time is missing. Usage: event <desc> /from <from> /to <to>");
            }
            Task t = new Event(desc, from, to);
            tasks.add(t);
            ui.showMessage("Got it. I've added this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.");
            return false;
        }

        if (cmd.equals("save")) {
            storage.save(tasks.asList());
            ui.showMessage("Saved.");
            return false;
        }
        else {
            throw new UnknownCommandException();
        }

    }
}
