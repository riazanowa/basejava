package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {

    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";

    public static final Resume R_1 = new Resume(UUID_1);
    public static final Resume R_2 = new Resume(UUID_2);
    public static final Resume R_3 = new Resume(UUID_3);

    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3);
        storage.update(newResume);
        assertSame(newResume, storage.get(UUID_3));
    }

    @Test
    public void updateNotExistentResume() {
        Resume r_4 = new Resume(UUID_4);
        assertThrows(NotExistStorageException.class, () -> storage.update(r_4));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    public void deleteNonexistentResume() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    public void getAll() {
        Resume[] resumes = {R_1, R_2, R_3};
        assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void get() {
        assertEquals(R_3, storage.get(UUID_3));
    }

    @Test
    public void getNotExistentResume() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    @Test
    public void save() {
        Resume r_4 = new Resume(UUID_4);
        storage.save(r_4);
        assertSame(r_4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test
    public void saveInFullStorage() {
        try {
            storage.clear();
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (Exception e) {
            fail("Overflow happened earlier than expected");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("uuid10001")));
    }

    @Test
    public void saveExistentResume() {
        assertThrows(ExistStorageException.class, () -> storage.save(R_3));
    }
}
