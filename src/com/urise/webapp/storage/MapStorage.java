package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private static final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getResume(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected String findSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) return entry.getKey();
        }
        return null;
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        storage.replace((String) index, resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void insertResume(Resume resume, Object uuid) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Object uuid) {
        return uuid != null;
    }
}
