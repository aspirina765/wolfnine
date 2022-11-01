package com.wolfnine.backend.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtil {
    public static String saveImageWithUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        String filePath = String.format("./files/%s.png", StringUtil.generateUUID());
        FileOutputStream fos = new FileOutputStream(filePath, true);
        fos.write(response);
        fos.close();
        return filePath;
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }
}
