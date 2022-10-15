package com.urise.webapp.storage.serializers;

import com.urise.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {
    void doWrite(Resume resume, OutputStream outputStream);
    Resume doRead(InputStream inputStream);
}
