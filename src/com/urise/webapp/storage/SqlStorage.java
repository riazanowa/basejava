package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SQLHelper;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {
    private Connection connection;

    private SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.transactionExecute(con -> {
                    try (PreparedStatement ps1 = con.prepareStatement("DELETE FROM resume;")) {
                        ps1.execute();
                    }
                    try (PreparedStatement ps2 = con.prepareStatement(" DELETE FROM contact;")) {
                        ps2.execute();
                    }
                    return null;
                }
        );
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?; ")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET value = ? WHERE type = ? AND resume_uuid = ?; ")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, e.getValue());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, r.getUuid());
                    ps.addBatch();
                }
                boolean isNoneUpdated = Arrays.stream(ps.executeBatch()).anyMatch(x -> x == 0);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact(type, value, resume_uuid) VALUES (?, ?, ?);")) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, e.getKey().name());
                            ps.setString(2, e.getValue());
                            ps.setString(3, r.getUuid());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r\n" +
                "         LEFT JOIN contact c ON r.uuid = c.resume_uuid" +
                "WHERE r.uuid = ?;", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                readContacts(rs, resume);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
            }
            return null;
    });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume\n" +
                "         LEFT JOIN contact c on resume.uuid = c.resume_uuid\n" +
                "         ORDER BY full_name, uuid;\n", ps -> {
            Map<String, Resume> resumeMap = new HashMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String resumeUuid = rs.getString("uuid");
                Resume resume = resumeMap.get(resumeUuid);
                if (resume == null) {
                    resumeMap.put(resumeUuid, new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
                readContacts(rs, resume);
            }
            return resumeMap.values().stream().sorted(Comparator.comparing(Resume::getUuid)).collect(Collectors.toList());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume;", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private Resume readContacts(ResultSet rs, Resume resume) throws SQLException {
        resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
        return resume;
    }
}
