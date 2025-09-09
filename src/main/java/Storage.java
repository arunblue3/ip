import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Storage {

    private final Path dataDir;
    private final Path dataFile;

    public Storage(String filePath) {
        Path p = Paths.get(filePath);
        this.dataDir = p.getParent() == null ? Paths.get(".") : p.getParent();
        this.dataFile = p;
        createFolderIfMissing();
    }

    public Storage() {
        this("data/buddy.txt");
    }

    private void createFolderIfMissing() {
        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            if (!Files.exists(dataFile)) {
                Files.createFile(dataFile);
            }
        } catch (IOException e) {
            System.out.println("Error creating data storage: " + e.getMessage());
        }
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(dataFile);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                tasks.add(Task.fromDataString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
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
