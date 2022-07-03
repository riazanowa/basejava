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

    protected Resume getResumeByIndex(int index) {
        return storage[index];
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
        storage[--size] = null;
    }

    @Override
    public void save(Resume r) {
        rangeCheckForSave(r);

        int index = getIndex(r.getUuid());

        if (index >= 0) throw new ExistStorageException(r.getUuid());

        insert(r, index);

        size++;
    }

    protected void rangeCheckForSave(Resume resume) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }
}
