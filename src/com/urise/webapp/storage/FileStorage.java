package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializers.SerializeStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final SerializeStrategy serializer;
    private final File directory;


    public FileStorage(File directory, SerializeStrategy serializer) {
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
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            return doRead(bis);
        } catch (IOException ex) {
            throw new StorageException("Couldn't get resume ");
        }
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(File file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Couldn't update resume: ", resume.getUuid());
        }
    }

    @Override
    protected void deleteResume(File file) {
       if (!file.delete()) {
           throw new StorageException("The file hasn't been deleted.");
       }
    }

    @Override
    protected void insertResume(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't save resume", file.getName(), e);
        }
        updateResume(file, resume);
    }

    protected void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        serializer.doWrite(resume, outputStream);
    }

    protected Resume doRead(InputStream inputStream) throws IOException {
        return serializer.doRead(inputStream);
    }

    @Override
    protected List<Resume> getAllResumes() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getFiles()) {
            resumes.add(getResume(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        for (File file : getFiles()) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        return getFiles().length;
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files != null) {
            return files;
        } else {
            throw new StorageException("Couldn't get files. Directory is empty.");
        }
    }
}
