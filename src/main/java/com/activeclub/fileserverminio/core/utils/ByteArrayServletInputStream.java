package com.activeclub.fileserverminio.core.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by liuxd on 2020/06/20.
 */
public class ByteArrayServletInputStream extends ServletInputStream {

    private ByteArrayInputStream byteArrayInputStream;

    public ByteArrayServletInputStream(ByteArrayInputStream byteArrayInputStream) {
        this.byteArrayInputStream = byteArrayInputStream;
    }

    @Override
    public int read() throws IOException {
        return byteArrayInputStream.read();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}