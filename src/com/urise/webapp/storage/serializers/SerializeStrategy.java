package com.urise.webapp.storage.serializers;

import com.urise.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {
    void serialize(Resume resume, OutputStream outputStream);
    Resume deserialise(InputStream inputStream);
}
