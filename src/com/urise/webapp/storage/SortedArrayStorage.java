package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveToArray(Resume resume, Object index) {
        int ind = -(int) index - 1;
        if (size - ind >= 0) System.arraycopy(storage, ind, storage, ind + 1, size - ind);
        storage[ind] = resume;
    }
}
