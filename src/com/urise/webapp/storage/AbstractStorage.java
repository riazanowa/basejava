package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class AbstractStorage<T> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public void update(Resume r) {
        LOG.info("Update " + r);
        T searchKey = getExistingSearchKey(r.getUuid());
        updateResume(searchKey, r);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        T searchKey = getExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        T searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        T searchKey = getNonExistingSearchKey(r.getUuid());
        insertResume(searchKey, r);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return getAllResumes().stream().sorted(RESUME_COMPARATOR).collect(Collectors.toList());
    }

    private T getExistingSearchKey(String uuid) {
        T searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume with " + uuid + " doesn't exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private T getNonExistingSearchKey(String uuid) {
        T searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume with " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isExist(T searchKey);

    protected abstract Resume getResume(T searchKey);

    protected abstract T findSearchKey(String uuid);

    protected abstract void updateResume(T searchKey, Resume resume);

    protected abstract void deleteResume(T searchKey);

    protected abstract void insertResume(T searchKey, Resume resume);

    protected abstract List<Resume> getAllResumes();
}
