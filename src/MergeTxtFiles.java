import java.io.*;
import java.util.Map;
import java.util.TreeMap;

    public class MergeTxtFiles {
        private TreeMap<String, File> textFiles = new TreeMap<>();

        public static void main (String[] args) {
            MergeTxtFiles mergeTxtFiles = new MergeTxtFiles();
            String directoryPath = mergeTxtFiles.readDirectoryPathFromConsole();
            Map<String,File> sortedTextFiles = mergeTxtFiles.getTextFiles(directoryPath);
            if (mergeTxtFiles.isDirectoryCorrect(sortedTextFiles)) {
                StringBuffer unionText = new StringBuffer();
                for (Map.Entry<String, File> pair : sortedTextFiles.entrySet()) {
                    unionText.append(mergeTxtFiles.readTxtFile(pair.getValue()));
                }
                mergeTxtFiles.writeFile(unionText.toString());
                System.out.println("Слияние текстовых файлов прошло успешно.");
            }
        }

        public String readDirectoryPathFromConsole() {
            String directoryPath = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                directoryPath = reader.readLine();
            }
            catch(IOException e) {}
            return directoryPath;
        }

        public TreeMap<String,File> getTextFiles(String directory) {
            File dir = new File(directory);
            if (dir.listFiles() == null) {
                return null;
            }
            for (File file : dir.listFiles()) {
                if (file.getName().endsWith((".txt"))) {
                    textFiles.put(file.getName(), file);
                } else if (file.isDirectory()) {
                    getTextFiles(file.getPath());
                }
            }
            return textFiles;
        }


        public String readTxtFile(File fileToRead) {
            StringBuffer textFromFile = new StringBuffer();
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToRead))) {
                while ((line = reader.readLine()) != null) {
                    textFromFile.append(line);
                }
            }
            catch(IOException e) {}

            return textFromFile.toString();
        }

        public void writeFile(String text) {

            try(FileWriter fileWriter = new FileWriter("unionFile.txt")) {
                fileWriter.write(text);
            }
            catch(IOException e) {}
        }

        public boolean isDirectoryCorrect (Map<String,File> sortedTextFiles) {
            if (sortedTextFiles == null) {
                System.out.println("Введена некорректная директория");
                return false;
            }
            else if (sortedTextFiles.isEmpty()) {
                System.out.println("Текстовых файлов не обнаруженно");
                return false;
            }
            return true;
        }
    }
