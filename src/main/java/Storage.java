import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Storage {

    private final Path dataDir;
    private final Path dataFile;

    public Storage() {
        this.dataDir = Paths.get("./data");
        this.dataFile = dataDir.resolve("buddy.txt");
        createFolderIfMissing();
    }

    private void createFolderIfMissing() {
        try {
            if (Files.notExists(dataDir)) {
                Files.createDirectories(dataDir);
            }
        } catch (IOException e) {
            System.out.println("Error creating data folder: " + e.getMessage());
        }
    }

    public List<Task> load() {
        List<Task> taskList = new ArrayList<>();
        if (!Files.exists(dataFile)) {
            return taskList;
        }

        try (BufferedReader reader = Files.newBufferedReader(dataFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                taskList.add(Task.fromDataString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return taskList;
    }

    public void save(List<Task> taskList) {
        try (BufferedWriter writer = Files.newBufferedWriter(dataFile)) {
            for (Task task : taskList) {
                writer.write(task.toDataString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

}
