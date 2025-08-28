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

            else if (command.trim().equals("list")) {
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

            else if (command.startsWith("todo")) {
                String[] parts = command.split(" ");
                StringBuilder sb = new StringBuilder();

                for (int i = 1; i < parts.length; i++) {
                        sb.append(parts[i]).append(" ");
                }

                String result = sb.toString().trim();

                Todo taskTodo = new Todo(result);
                tasks.add(taskTodo);

                System.out.println(buddy.barWrap("Got it. I've added this task: \n" + taskTodo.toString()
                        + "\n" + String.format("Now you have %d tasks in the list.", tasks.size())));

            }

            else if (command.startsWith("deadline")) {
                String[] parts = command.split(" ");
                StringBuilder sb = new StringBuilder();
                StringBuilder date = new StringBuilder();

                int sect = 0;
                for (String word : parts) {
                        if (word.equals("/by")) {
                            sect ++ ;
                            continue;
                        }
                        if (sect == 0) {
                            sb.append(word).append(" ");
                        } else {
                            date.append(word).append(" ");
                        }
                }

                String result = sb.toString().trim();
                String by = date.toString().trim();

                Deadline taskDeadline = new Deadline(result,by);
                tasks.add(taskDeadline);

                System.out.println(buddy.barWrap("Got it. I've added this task: \n" + taskDeadline.toString()
                        + "\n" + String.format("Now you have %d tasks in the list.", tasks.size())));

            }

            else if (command.startsWith("event")) {
                String[] parts = command.split(" ");
                StringBuilder sb = new StringBuilder();
                StringBuilder start = new StringBuilder();
                StringBuilder end = new StringBuilder();

                int sect = 0;
                for (String word : parts) {
                    if (word.equals("/from")) {
                        sect ++ ;
                        continue;
                    } else if (word.equals("/to")){
                        sect ++ ;
                        continue;
                    }

                    if (sect == 0) {
                        sb.append(word).append(" ");
                    } else if (sect == 1) {
                        start.append(word).append(" ");
                    }else {
                        end.append(word).append(" ");
                    }
                }

                String result = sb.toString().trim();
                String from = start.toString().trim();
                String to = end.toString().trim();

                Event taskEvent = new Event(result,from,to);
                tasks.add(taskEvent);

                System.out.println(buddy.barWrap("Got it. I've added this task: \n" + taskEvent.toString()
                        + "\n" + String.format("Now you have %d tasks in the list.", tasks.size())));

            }


            else {
                tasks.add(new Task(command));
                System.out.println(buddy.barWrap("added: " + command));
            }
        }
        scanner.close();
    }
}