import java.util.*;

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
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(buddy.getGreeting());

        while (true) {
            String command = scanner.nextLine();

            if (command.trim().equals("bye")) {
                System.out.println(buddy.barWrap(buddy.ending));
                break;
            }

            else if (command.trim().equals("list")){
                String currList = "Here are the tasks in your list: \n";
                for (int i = 0; i < tasks.size(); i++){
                    currList += i+1 + ". " + tasks.get(i) + "\n";
                }
                System.out.println(buddy.barWrap(currList.trim()));
            }

            else if (command.startsWith("mark")) {
                String[] parts = command.split(" ");
                int idx = Integer.parseInt(parts[1]) - 1;
                Task toUpdate = tasks.get(idx);
                toUpdate.markAsDone();
                System.out.println(buddy.barWrap("Nice! I've marked this task as done: \n" + toUpdate.toString()));
            }

            else if (command.startsWith("unmark")) {
                String[] parts = command.split(" ");
                int idx = Integer.parseInt(parts[1]) - 1;
                Task toUpdate = tasks.get(idx);
                toUpdate.unmark();
                System.out.println(buddy.barWrap("OK, I've marked this task as not done yet: \n" + toUpdate.toString()));
            }

            else {
                tasks.add(new Task(command));
                System.out.println(buddy.barWrap("added: " + command));
            }
        }
        scanner.close();
    }
}