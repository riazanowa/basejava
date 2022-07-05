package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object index = getExistingIndex(r.getUuid());
        updateResume(index, r);
    }

    public void delete(String uuid) {
        Object index = getExistingIndex(uuid);
        deleteResume(index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = getExistingIndex(uuid);
        return getResumeByIndex(index);
    }

    public void save(Resume r) {
        Object index = getNonExistingIndex(r.getUuid());
        insertResume(r, index);
    }

    protected Object getExistingIndex(String uuid) throws NotExistStorageException {
        Object index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected Object getNonExistingIndex(String uuid) throws NotExistStorageException {
        Object index = getIndex(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    protected abstract boolean isExist(Object index);

    protected abstract Resume getResumeByIndex(Object index);

    protected abstract Object getIndex(String uuid);

    protected abstract void updateResume(Object index, Resume resume);

    protected abstract void deleteResume(Object index);

    protected abstract void insertResume(Resume resume, Object index);
}
