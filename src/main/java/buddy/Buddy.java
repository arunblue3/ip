package buddy;

import buddy.exception.BuddyException;
import buddy.model.TaskList;
import buddy.parser.Parser;
import buddy.storage.Storage;
import buddy.ui.UI;

public class Buddy {

    private Storage storage;
    private TaskList tasks;
    private UI ui;

    public Buddy(String filePath) {
        ui = new UI();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            String input = ui.readCommand();
            try {
                boolean shouldExit = Parser.handle(input, tasks, ui, storage);
                if (shouldExit) break;
            } catch (BuddyException | RuntimeException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Buddy("data/buddy.txt").run();
    }
}
