package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static com.urise.webapp.ResumeTestData.createResume;
import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void saveOverflow() {
        Storage storage = getStorage();
        try {
            storage.clear();
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i, "name" + i));
            }
        } catch (Exception e) {
            fail("Overflow happened earlier than expected");
        }
        assertThrows(StorageException.class, () -> storage.save(createResume("uuid10001", "name10001")));
    }
}
