package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {
    private static final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());

        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
       storage.set(index, r);
    }

    @Override
    public void delete(String uuid) {
        Resume resume = get(uuid);
        if (!storage.contains(resume)) throw new NotExistStorageException(uuid);
        storage.remove(resume);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public Resume get(String uuid) {
        return storage.stream().filter(resume ->
                Objects.equals(resume.getUuid(), uuid))
                .findFirst()
                .orElseThrow(() -> new NotExistStorageException(uuid));
    }

    @Override
    public void save(Resume r) {
        if (storage.contains(r)) throw new ExistStorageException(r.getUuid());
        storage.add(r);
    }

    @Override
    public int getIndex(String uuid) {
        return storage.indexOf(get(uuid));
    }
}
