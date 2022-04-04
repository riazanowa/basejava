package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest {
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";

    public static final Resume R_1;
    public static final Resume R_2;
    public static final Resume R_3;

    static {
        R_1 = new Resume(UUID_1);
        R_2 = new Resume(UUID_2);
        R_3 = new Resume(UUID_3);
    }

    private Storage storage;

    public ListStorageTest() {
        storage = new ListStorage();
    }

    @BeforeEach
    void setUp() {
        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
    }

    @AfterEach
    public void cleanUpEach() {
        storage.clear();
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updatedResume = new Resume(UUID_3);
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get(UUID_3));
    }

    @Test
    void updateNotExistentResume() {
        Resume r_4 = new Resume(UUID_4);
        assertThrows(NotExistStorageException.class, () -> storage.update(r_4));
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    void deleteNonExistentResume() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void getAll() {
        Resume[] resumes = {R_1, R_2, R_3};
        assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void get() {
        assertEquals(R_3, storage.get(UUID_3));
    }

    @Test
    void getNonExistentResume() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    @Test
    void save() {
        Resume r_4 = new Resume(UUID_4);
        storage.save(r_4);
        assertSame(r_4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test
    void saveExistentResume() {
        assertThrows(ExistStorageException.class, () -> storage.save(R_3));
    }
}
