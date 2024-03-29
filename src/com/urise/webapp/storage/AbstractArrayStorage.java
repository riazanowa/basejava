package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> getAllResumes() {
        return Arrays.stream(storage).takeWhile(Objects::nonNull).collect(Collectors.toList());
    }

    protected Resume getResume(Integer index) {
        return storage[(int) index];
    }

    @Override
    protected void updateResume(Integer index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void deleteResume(Integer index) {
        int ind = (int) index;
        if (size - 1 - ind >= 0) System.arraycopy(storage, ind + 1, storage, ind, size - 1 - (int) index);
        storage[--size] = null;
    }

    @Override
    public void insertResume(Integer index, Resume r) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveToArray(r, (int) index);
        size++;
    }

    @Override
    protected boolean isExist(Integer index) {
        return (int) index >= 0;
    }

    protected abstract void saveToArray(Resume resume, int index);
}
