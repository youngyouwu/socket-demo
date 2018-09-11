package com.young.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author young
 * create_date 2018/9/11 13:30
 * @version 1.0
 */
public class Util {
    public static byte[] getByteArray(Object object) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(object);
        return stream.toByteArray();
    }

    public static Object getObject(byte[] bytes) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(stream);
        return in.readObject();
    }
}
