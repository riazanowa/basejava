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

        updateResume(index, r);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }

        deleteResume(index);
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

        int index = getIndex(r.getUuid());

        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        insert(r, index);
    }

    protected abstract Resume getResumeByIndex(int index);

    protected abstract int getIndex(String uuid);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    protected abstract void insert(Resume resume, int position);
}
