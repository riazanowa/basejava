package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Random;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        if (isPresent(r.getUuid())) {
            Resume resume1 = get(r.getUuid());
            resume1.setUuid("uuid" + new Random().nextInt(10000));
            System.out.println("Successfully updated");
        } else {
            System.out.println("Not updated. Resume doesn't exist.");
        }
    }

    public void save(Resume r) {
        if (size() == storage.length) System.out.println("Can't be saved. The storage is full");

        if (!isPresent(r.getUuid())) {
            storage[size++] = r;
            System.out.println("Saved.");
        } else {
            System.out.println("Not saved. Resume is already present.");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) return storage[i];
        }
        return null;
    }

    public void delete(String uuid) {
        int i;
        for (i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) break;
        }

        for (int j = i; j < size - 1; j++) {
            storage[j] = storage[j + 1];
        }
        storage[--size] = null;
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
        if (get(uuid) != null) return true;
        return false;
    }
}
