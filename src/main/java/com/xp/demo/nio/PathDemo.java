package com.xp.demo.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {

    public static void main(String[] args) throws IOException {
        ByteBuffer bb=ByteBuffer.allocate(100);
        IntBuffer ib=bb.asIntBuffer();
        ib.put(new int[]{11,42,47,99,143,811,1016});
        System.out.println(ib.getClass().getName());
        System.out.println(ib.get(3));
        ib.put(3,1811);
        ib.flip();
        while (ib.hasRemaining()){
            int i=ib.get();
            System.out.println(i);
        }




    }


}
