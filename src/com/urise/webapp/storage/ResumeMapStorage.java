package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeMapStorage extends AbstractStorage<Resume> {
    private static final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void updateResume(Resume searchKey, Resume resume) {
        storage.replace((searchKey).getUuid(), resume);
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        storage.remove(( searchKey).getUuid());
    }

    @Override
    protected void insertResume(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Resume resume) {
        return resume;
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    public List<Resume> getAllResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
}
