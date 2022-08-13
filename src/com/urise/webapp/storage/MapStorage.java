package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private static final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getResume(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected String findSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected void updateResume(String uuid, Resume resume) {
        storage.replace(uuid, resume);
    }

    @Override
    protected void deleteResume(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void insertResume(String uuid, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAllResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isExist(String uuid) {
        return uuid != null;
    }
}
