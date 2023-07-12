import java.io.*;
import java.util.Scanner;

public class ReadFile {
    private File file;

    public boolean setFileName(String fileName) {
        this.file = new File(fileName);
        return file.exists();
    }

    public File getFileName() {
        return this.file;
    }

    public int[] makeArray() throws IOException {
        if (!file.exists()) {
            throw new IOException("File doesn't exist.");
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        String[] strArr = line.split(" ");
        int len = Math.min(strArr.length, 100);
        int[] numArr = new int[len];
        for (int i = 0; i < strArr.length; i++) {
            if (i < 100){
                numArr[i] = Integer.parseInt(strArr[i]);
            }
            else break;
        }
        return numArr;
    }
}
