package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    public Integer findSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void updateResume(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteResume(Integer index) {
        storage.remove((int) index);
    }

    public void insertResume(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getAllResumes() {
        return storage;
    }
}
