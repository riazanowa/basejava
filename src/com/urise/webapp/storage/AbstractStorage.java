package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        int index = getIndex(r.getUuid());

        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }

        set(index, r);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        fastDelete(index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResumeByIndex(index);
    }

    public void save(Resume r) {
        rangeCheckForSave(r);

        int index = getIndex(r.getUuid());

        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        insert(r, index);
        changeSize();
    }

    protected abstract void changeSize();

    protected abstract void rangeCheckForSave(Resume resume);

    protected abstract Resume getResumeByIndex(int index);

    protected abstract int getIndex(String uuid);

    protected abstract void set(int index, Resume resume);

    protected abstract void fastDelete(int index);

    protected abstract void insert(Resume resume, int position);
}
