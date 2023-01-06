package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private Connection connection;

    public SqlStorage() {
        this.connection = SQLHelper.getConnection();
    }

    @Override
    public void clear() {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?; ")) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
        } catch (SQLException e) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume = new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return resume;
    }

    @Override
    public void delete(String uuid) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString( "full_name")));
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return resumes;
    }

    @Override
    public int size() {
        int size = 0;
        try (PreparedStatement ps = connection.prepareStatement("SELECT count(*) FROM resume;")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return size;
    }
}
