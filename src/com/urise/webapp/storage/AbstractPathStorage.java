package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializers.SerializeStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbstractPathStorage extends AbstractStorage<Path> {

    private SerializeStrategy serializer;
    private Path directory;

    protected AbstractPathStorage(String dir, SerializeStrategy serializer) {
        this.serializer = serializer;
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directoy must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected void doWrite(Resume resume, OutputStream outputStream) {
        serializer.serialize(resume, outputStream);
    }

    protected Resume doRead(InputStream inputStream) throws IOException {
        return serializer.deserialise(inputStream);
    }


    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", this.toString());
        }
    }

    @Override
    public int size() {
        int count = 0;
        try {
            count = (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error");
        }
        return count;
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    protected Resume getResume(Path searchKey) {
        try (FileInputStream fis = new FileInputStream(searchKey.toString());
             BufferedInputStream bos = new BufferedInputStream(fis)) {
            return doRead(bos);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void updateResume(Path searchKey, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey.toFile())));
        } catch (IOException e) {

        }
    }

    @Override
    protected void deleteResume(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error ", searchKey.toString());
        }

    }

    @Override
    protected void insertResume(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
            updateResume(searchKey, resume);
        } catch (IOException e) {
            throw new StorageException("File creation error", e);
        }

    }

    @Override
    protected List<Resume> getAllResumes() {
        List<Resume> resumes = null;
        try {
            resumes = Files.list(directory)
                    .map(this::getResume)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resumes;
    }
}
