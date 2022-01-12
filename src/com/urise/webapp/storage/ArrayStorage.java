package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Not updated. Resume " + r.getUuid() + " doesn't exist.");
        } else {
            storage[index] = r;
            System.out.println("Resume " + r.getUuid() + " has been updated successfully.");
        }
    }

    public void save(Resume r) {
        if (size == storage.length) System.out.println("Can't be saved. The storage is full");

        if (getIndex(r.getUuid()) == -1) {
            storage[size++] = r;
            System.out.println("Resume " + r.getUuid() + " has been saved.");
        } else {
            System.out.println("Not saved. Resume " + r.getUuid() + " is already present.");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index != -1) {
            return storage[index];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int i = getIndex(uuid);

        if (i != -1) {
            if (size - 1 - i >= 0) System.arraycopy(storage, i + 1, storage, i, size - 1 - i);
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

    public boolean isPresent(String uuid) {
        return getIndex(uuid) != -1;
    }

    public int getIndex(String uuid) {
        int i;
        for (i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
