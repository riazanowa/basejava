package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private static final Map<String, Resume> storage = new HashMap<>();
    private static int counter = 0;

    @Override
    protected Resume getResumeByIndex(Object index) {
        return storage.get((String) index);
    }

    @Override
    protected String getIndex(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) return entry.getKey();
        }
        return null;
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        storage.replace( (String) index, resume);
    }

    @Override
    protected void deleteResume(Object index) {
        storage.remove(index);
        counter--;
    }

    @Override
    protected void insertResume(Resume resume, Object index) {
        ++counter;
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
        counter = 0;
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
    protected boolean isExist(Object index) {
        return index != null;
    }
}
