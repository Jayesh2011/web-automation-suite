package com.zeuslearning.automation.io;

import java.io.IOException;

public interface IInputOutputOps {

    boolean close();

    boolean open(Object source) throws IOException;

    boolean open(String source);

    Object read();

    Object readAll();

    boolean reset();

    boolean write(Object data);

    boolean write(String data);

    public String createPath(String relativePath);

}
