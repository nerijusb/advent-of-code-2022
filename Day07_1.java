import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Part one of
 * <a href="https://adventofcode.com/2022/day/7">Day seven</a>
 *
 * @author Nerijus
 */
public class Day07_1 {
    public static void main(String[] args) {
        System.out.println("Sum of directories of at most 100000: " + new Day07_1().getResult());
    }

    private int getResult() {
        return readFileStructure()
                .getAllDirectories()
                .stream()
                .filter(d -> d.size() <= 100000)
                .mapToInt(Directory::size)
                .sum();
    }

    protected Directory readFileStructure() {
        LinkedList<String> lines = new LinkedList<>(Inputs.readStrings("Day07"));
        Directory currentDir = new Directory(null, "/");
        lines.pop(); // skip first "$ cd /"
        while (!lines.isEmpty()) {
            // read structure
            String line = lines.pop();
            if (line.startsWith("$ cd")) {
                String newDir = line.replace("$ cd ", "");
                if ("..".equals(newDir)) {
                    currentDir = currentDir.up();
                } else {
                    // go to sub-dir
                    if (!currentDir.contains(newDir)) {
                        currentDir = new Directory(currentDir, newDir);
                        currentDir.directories.add(currentDir);
                    } else {
                        currentDir = currentDir.directories.stream().filter(d -> d.name.equals(newDir)).findFirst().orElseThrow();
                    }
                }
            } else if (!line.startsWith("$ ls")) {
                // read files/folders
                if (line.startsWith("dir")) {
                    // folder
                    String dirName = line.replace("dir ", "");
                    Directory newDir = new Directory(currentDir, dirName);
                    currentDir.directories.add(newDir);
                } else {
                    // file
                    String[] parts = line.split(" ");
                    AOCFile file = new AOCFile(Integer.parseInt(parts[0]), parts[1]);
                    currentDir.files.add(file);
                }
            }
        }
        return currentDir.getRoot();
    }

    static class Directory {
        String name;
        Directory parent;
        List<Directory> directories = new ArrayList<>();
        List<AOCFile> files = new ArrayList<>();

        Directory(Directory parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        Directory up() {
            return parent;
        }

        int size() {
            int fullSize = 0;
            for (AOCFile file : files) {
                fullSize += file.size;
            }
            for (Directory directory : directories) {
                fullSize += directory.size();
            }
            return fullSize;
        }

        Directory getRoot() {
            if (parent == null) {
                return this;
            } else {
                return parent.getRoot();
            }
        }

        List<Directory> getAllDirectories() {
            List<Directory> all = new ArrayList<>();
            all.add(this);
            directories.forEach(d -> all.addAll(d.getAllDirectories()));
            return all;
        }

        public boolean contains(String dir) {
            return directories.stream().anyMatch(d -> dir.equals(d.name));
        }

        @Override
        public String toString() {
            return name + " (dir)";
        }
    }

    record AOCFile(int size, String name) {
        @Override
        public String toString() {
            return name + " (file, size=" + size + ")";
        }
    }
}
