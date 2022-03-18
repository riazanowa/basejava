package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
            System.out.println("Resume " + r.getUuid() + " has been updated successfully.");
        } else {
            throw new NotExistStorageException(r.getUuid());
            //System.out.println("Not updated. Resume " + r.getUuid() + " doesn't exist.");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index >= 0) {
            if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            storage[--size] = null;
            System.out.println("Resume " + uuid + " has been deleted");
        } else {
            throw new NotExistStorageException(uuid);
            //System.out.println("Can't be deleted. The resume " + uuid + " doesn't exist.");
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            //System.out.println("Resume " + uuid + " doesn't exists.");
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public void save(Resume r) {
        if (size == storage.length) {
            //System.out.println("Can't be saved. The storage is full");
            throw new StorageException("Storage overflow", r.getUuid());
        }

        int index = getIndex(r.getUuid());

        if (index >= 0) {
            //System.out.println("Not saved. Resume " + r.getUuid() + " is already present.");
            throw new ExistStorageException(r.getUuid());
        } else {
            insert(r, index);
            size++;
            //System.out.println("Resume " + r.getUuid() + " has been saved.");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int position);


}
