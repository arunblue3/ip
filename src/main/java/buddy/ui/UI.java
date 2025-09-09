package buddy.ui;

import java.util.Scanner;

public class UI {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm buddy.Buddy");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showMessage(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    public void showError(String msg) {
        showLine();
        System.out.println("Error: " + msg);
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLoadingError() {
        showError("Failed to load previous tasks. Starting with an empty list.");
    }

    public void showGoodbye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    public void close() {
        scanner.close();
    }
}
