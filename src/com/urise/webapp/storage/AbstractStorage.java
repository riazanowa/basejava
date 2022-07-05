package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        updateResume(searchKey, r);
    }

    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public void save(Resume r) {
        Object searchKey = getNonExistingSearchKey(r.getUuid());
        insertResume(r, searchKey);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNonExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object index);

    protected abstract Resume getResume(Object index);

    protected abstract Object findSearchKey(String uuid);

    protected abstract void updateResume(Object index, Resume resume);

    protected abstract void deleteResume(Object index);

    protected abstract void insertResume(Resume resume, Object index);
}
