import java.util.Scanner;

public class Buddy {

    public String bar = "____________________________________________________________\n";

    public String greeting = " Hello! I'm Buddy \n" +
            " What can I do for you?\n";
    public String ending =
            " Bye. Hope to see you again soon!";

    public String getGreeting() {
        return bar + greeting + bar;
    }

    public String barWrap(String msg) {
        return bar + msg + "\n" + bar;
    }

    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        Scanner scanner = new Scanner(System.in);

        System.out.println(buddy.getGreeting());

        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println(buddy.barWrap(buddy.ending));
                break;
            }

            System.out.println(buddy.barWrap(command));
        }
        scanner.close();
    }
}