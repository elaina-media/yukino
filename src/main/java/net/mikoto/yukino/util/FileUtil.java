package net.mikoto.yukino.util;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author mikoto
 * @date 2022/1/2 1:33
 */
public final class FileUtil {
    /**
     * Create a new dir or don't do anything.
     *
     * @param dirName The name of the dir.
     */
    public static void createDir(@NotNull String dirName) {
        File dir = new File(System.getProperty("user.dir") + dirName);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("Can't create dir");
            }
        }
    }

    /**
     * Create a new file of don't do anything.
     *
     * @param file  The path of the file.
     * @param input The data in the file.
     * @throws IOException An error.
     */
    public static void createFile(@NotNull File file, @NotNull String input) throws IOException {
        if (!file.exists()) {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(input);
                fileWriter.close();
            } else {
                System.err.println("Can't create config");
            }
        }
    }

    /**
     * Write data into the file.
     *
     * @param file  The path of the file.
     * @param input The data in the file.
     * @throws IOException An error.
     */
    public static void writeFile(@NotNull File file, @NotNull String input) throws IOException {
        if (!file.exists()) {
            createDir(file.getParent());
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(input);
                fileWriter.close();
            } else {
                System.err.println("Can't create config");
            }
        } else {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(input);
            fileWriter.close();
        }
    }

    /**
     * Read data from file.
     *
     * @param file The file.
     * @return The result.
     * @throws IOException IOException.
     */
    @NotNull
    public static String readFile(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        // Read single file
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}
