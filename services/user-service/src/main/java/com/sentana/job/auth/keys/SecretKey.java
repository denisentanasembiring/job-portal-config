package com.sentana.job.auth.keys;

import java.io.*;
import java.util.Scanner;

public class SecretKey {
    static final String fileName = "/app/job-app/key/secret-key.txt";
    static File file = new File(System.getProperty("user.home"), fileName);
    public static String getKey() {
        String key = null;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                Scanner scanner = new Scanner(line);
                while (scanner.hasNext()){
                    key = scanner.nextLine();
                }
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}
