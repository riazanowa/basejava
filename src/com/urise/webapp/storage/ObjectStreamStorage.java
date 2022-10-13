package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializers.SerializeStrategy;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {

    public ObjectStreamStorage(File directory, SerializeStrategy serializer) {
        super(directory, serializer);
    }

    protected void doWrite(Resume resume, OutputStream outputStream) {
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(resume);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw  new StorageException("Error read resume", null, e);
        }
    }
}
