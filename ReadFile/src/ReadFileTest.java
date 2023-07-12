import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class ReadFileTest {

    @Test
    public void makeArr() throws IOException {
        File file1 = new File("temp.txt");
        File file2 = new File("temp2.txt");
        FileWriter fileWriter1 = new FileWriter(file1);
        FileWriter fileWriter2 = new FileWriter(file2);
        if (!file1.exists()){
            file1.createNewFile();
        }
        fileWriter1.write("2 35 -1 0 72 -15 8 100");
        fileWriter1.flush();
        fileWriter1.close();
        if (!file2.exists()){
            file2.createNewFile();
        }
        fileWriter2.write("55 63 -88 -77 -20 49 50 -58 23 -91 51 69 22 -36 -39 -83 55 -81 -57 -98 13 11 -52 -8 -82" +
                " -30 -30 -46 1 6 -78 -88 91 -62 7 52 -92 13 90 -73 15 -51 -60 2 29 -1 -80 -74 26 20 44 11 -14 -1 23 -26 -87 " +
                "-54 -37 72 1 59 -17 59 -16 75 -96 -79 26 -19 -10 42 -60 1 -78 -31 -59 -73 8 -56 -36 55 56 -37 91 9 -77 -64 35 " +
                "-83 -54 -27 61 4 30 -6 0 48 -51 27 -37 75 -13 31 74 93 -3 62 10 -19");
        fileWriter2.flush();
        fileWriter2.close();
        ReadFile newFile1 = new ReadFile();
        newFile1.setFileName("temp.txt");
        ReadFile newFile2 = new ReadFile();
        newFile2.setFileName("temp2.txt");
        assertArrayEquals(newFile1.makeArray(), new int[] {2, 35, -1, 0, 72, -15, 8, 100});
        assertArrayEquals(newFile2.makeArray(), new int[] {55, 63, -88, -77, -20, 49, 50, -58, 23, -91, 51, 69, 22, -36, -39, -83, 55,
                -81, -57, -98, 13, 11, -52, -8, -82, -30, -30, -46, 1, 6, -78, -88, 91, -62, 7, 52, -92, 13, 90, -73, 15, -51, -60, 2,
                29, -1, -80, -74, 26, 20, 44, 11, -14, -1, 23, -26, -87, -54, -37, 72, 1, 59, -17, 59, -16, 75, -96, -79, 26, -19, -10,
                42, -60, 1, -78, -31, -59, -73, 8, -56, -36, 55, 56, -37, 91, 9, -77, -64, 35, -83, -54, -27, 61, 4, 30, -6, 0, 48, -51, 27});
    }

    @Test
    public void setName() {
        ReadFile file = new ReadFile();
        assertTrue(file.setFileName("temp.txt"));
        assertTrue(file.setFileName("temp2.txt"));
        assertFalse(file.setFileName("temp100.txt"));
    }
}
