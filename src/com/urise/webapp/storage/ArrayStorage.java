package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
            System.out.println("Resume " + r.getUuid() + " has been updated successfully.");
        } else {
            System.out.println("Not updated. Resume " + r.getUuid() + " doesn't exist.");
        }
    }

    public void save(Resume r) {
        if (size == storage.length) System.out.println("Can't be saved. The storage is full");

        if (getIndex(r.getUuid()) != -1) {
            System.out.println("Not saved. Resume " + r.getUuid() + " is already present.");
        } else {
            storage[size++] = r;
            System.out.println("Resume " + r.getUuid() + " has been saved.");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index != -1) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " doesn't exists.");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index != -1) {
            if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            storage[--size] = null;
            System.out.println("Resume " + uuid + " has been deleted");
        } else {
            System.out.println("Can't be deleted. The resume " + uuid + " doesn't exist.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
