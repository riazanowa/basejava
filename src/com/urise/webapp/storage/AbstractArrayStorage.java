package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
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

    protected Resume getResumeByIndex(Object index) {
        return storage[(int) index];
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        storage[ (int) index] = resume;
    }

    @Override
    protected void deleteResume(Object index) {
        if (size - 1 - (int) index >= 0) System.arraycopy(storage, (int) index + 1, storage, (int) index, size - 1 - (int) index);
        storage[--size] = null;
    }

    @Override
    public void save(Resume r) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        int index = (int) getIndex(r.getUuid());
        if (index >= 0) throw new ExistStorageException(r.getUuid());
        insertResume(r, index);
        size++;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }
}
