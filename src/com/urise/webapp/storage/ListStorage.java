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

    protected Resume getResumeByIndex(int index) {
        return storage.get(index);
    }

    @Override
    public int getIndex(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }

    public void insert(Resume resume, int position) {
        storage.add(resume);
    }
}
