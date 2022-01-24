package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected boolean insert(Resume resume, int position) {
        int pos = (-(position) - 1);
        for (int i = size; i > pos; i--) {
            storage[i] = storage[i-1];
        }
        storage[pos] = resume;
        size++;
        return true;
    }
}
