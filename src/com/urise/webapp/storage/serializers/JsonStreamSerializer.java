package com.urise.webapp.storage.serializers;

import com.urise.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public class JsonStreamSerializer implements SerializeStrategy{
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) {

    }

    @Override
    public Resume doRead(InputStream inputStream) {
        return null;
    }
}
