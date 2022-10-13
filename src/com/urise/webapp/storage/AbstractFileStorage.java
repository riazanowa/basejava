package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializers.SerializeStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AbstractFileStorage extends AbstractStorage<File> {
    private SerializeStrategy serializer;
    private File directory;


    public AbstractFileStorage(File directory, SerializeStrategy serializer) {
        this.serializer = serializer;
        Objects.requireNonNull(directory, "Must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected Resume getResume(File file) {
        Resume resume = null;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            resume = doRead(bis);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resume;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(File file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected void insertResume(File file, Resume resume) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            file.createNewFile();
            doWrite(resume, bos);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected void doWrite(Resume resume, OutputStream outputStream) {
        serializer.serialize(resume, outputStream);
    }

    protected Resume doRead(InputStream inputStream) throws IOException {
        return serializer.deserialise(inputStream);
    }

    @Override
    protected List<Resume> getAllResumes() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            resumes.add(getResume(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }
}
