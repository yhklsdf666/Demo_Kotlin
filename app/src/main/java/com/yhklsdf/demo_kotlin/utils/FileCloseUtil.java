package com.yhklsdf.demo_kotlin.utils;

import java.io.Closeable;
import java.io.IOException;

public class FileCloseUtil {
    public static <T extends Closeable>void close(T ... io){
        for (Closeable temp : io) {
            if (temp != null) {
                try {
                    temp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
