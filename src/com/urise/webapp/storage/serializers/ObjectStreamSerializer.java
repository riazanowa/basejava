package com.urise.webapp.storage.serializers;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements SerializeStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) {
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(resume);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) {
        Resume resume = null;
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            resume = (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw  new StorageException("Error read resume", null, e);
        }
        return resume;
    }
}
