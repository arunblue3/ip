package buddy;

import buddy.exception.BuddyException;
import buddy.model.TaskList;
import buddy.parser.Parser;
import buddy.storage.Storage;
import buddy.ui.Ui;

public class Buddy {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Buddy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            if (input == null) {
                ui.showGoodbye();
                break;
            }
            if (input.isBlank()) {
                continue;
            }
            try {
                boolean shouldExit = Parser.handle(input, tasks, ui, storage);
                if (shouldExit) break;
            } catch (BuddyException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Buddy("data/buddy.txt").run();
    }
}
