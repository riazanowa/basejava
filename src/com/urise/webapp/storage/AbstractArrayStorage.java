package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void deleteResume(Object index) {
        int ind = (int) index;
        if (size - 1 - ind >= 0) System.arraycopy(storage, ind + 1, storage, ind, size - 1 - (int) index);
        storage[--size] = null;
    }

    @Override
    public void insertResume(Resume r, Object index) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveToArray(r, index);
        size++;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }

    protected abstract void saveToArray(Resume resume, Object index);
}
