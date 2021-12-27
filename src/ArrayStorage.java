import static java.util.Arrays.copyOf;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storage[i].uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        int i;
        for (i = 0; i < size(); i++) {
            if (uuid.equals(storage[i].toString())) break;
        }

        for (int j = i; j <= size() - 2; j++) {
            storage[j] = storage[j + 1];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return copyOf(storage, size());
    }

    int size() {
        int count = 0;
        for (int i = 0; i < storage.length; count++) {
            if (storage[i] == null) {
                break;
            }
            count++;
        }
        return count;
    }
}
