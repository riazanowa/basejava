package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

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
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    protected Resume getResumeByIndex(Object index) {
        return storage.get((int)index);
    }

    @Override
    public Integer getIndex(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(Object index, Resume resume) {
        storage.set((int) index, resume);
    }

    @Override
    protected void deleteResume(Object index) {
        storage.remove((int)index);
    }

    public void insertResume(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }
}
