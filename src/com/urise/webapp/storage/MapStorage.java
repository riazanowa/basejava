package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private static final Map<Integer, Resume> storage = new HashMap<>();
    private static int counter = 0;

    @Override
    protected Resume getResumeByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected int getIndex(String uuid) {
        for (Map.Entry<Integer, Resume> entry : storage.entrySet()) {
            if (entry.getValue().getUuid().equals(uuid)) return entry.getKey();
        }
        return -1;
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.replace(index, resume);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
        counter--;
    }

    @Override
    protected void insert(Resume resume, int position) {
        storage.put(++counter, resume);
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
}
