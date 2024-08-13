package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    private String getStringFromFile(File file) throws IOException {
        StringBuilder s = new StringBuilder();

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int r;
            while ((r = fileInputStream.read()) != -1) {
                s.append((char) r);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return s.toString();
    }

    private String getInfoByKey(String key, String text) throws Exception {
        int keyIdx = text.indexOf(key) + key.length();
        if (keyIdx < 0) {
            throw new Exception("Key not found!");
        }

        int tailIdx = text.substring(keyIdx).indexOf("\r\n") + keyIdx;
        if (tailIdx < keyIdx) {
            throw new Exception("Info not found!");
        }

        return text.substring(keyIdx, tailIdx);
    }

    public Profile getDataFromFile(File file) {
        Profile profile = null;

        try {
            String s = getStringFromFile(file);
            profile = new Profile(
                getInfoByKey("Name: ", s),
                Integer.valueOf(getInfoByKey("Age: ", s)),
                getInfoByKey("Email: ", s),
                Long.valueOf(getInfoByKey("Phone: ", s))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profile;
    }
}
