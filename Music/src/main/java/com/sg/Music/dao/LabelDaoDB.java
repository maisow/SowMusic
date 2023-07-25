package com.sg.Music.dao;

import com.sg.Music.entities.Label;
import com.sg.Music.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LabelDaoDB implements LabelDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Label getLabelByID(int id) {
        try {
            final String SELECT_LABEL_BY_ID = "SELECT * FROM Label WHERE labelId = ?";
            return jdbc.queryForObject(SELECT_LABEL_BY_ID, new LabelMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Label> getAllLabels() {
        final String SELECT_ALL_LABELS = "SELECT * FROM label";
        return jdbc.query(SELECT_ALL_LABELS, new LabelMapper());
    }

    @Override
    public Label addLabel(Label label) {
        final String INSERT_LABEL = "INSERT INTO label(labelName, labelDescription) VALUES(?,?)";
        jdbc.update(INSERT_LABEL,
                label.getName(),
                label.getDescription());

        // Retrieve the last inserted ID
        String selectLastIdQuery = "SELECT LAST_INSERT_ID()";
        int labelId = jdbc.queryForObject(selectLastIdQuery, Integer.class);

        // Set the label ID
        label.setId(labelId);
        return label;
    }

    @Override
    public void updateLabel(Label label) {

        final String UPDATE_LABEL = "UPDATE label SET labelName = ?, labelDescription = ? WHERE labelId = ?";
        jdbc.update(UPDATE_LABEL,
                        label.getName(),
                        label.getDescription(),
                        label.getId());
    }

    @Override
    @Transactional
    public void deleteLabelByID(int id) {
        final String DELETE_LABEL_BY_ARTIST = "DELETE FROM Artist WHERE label = ?";
        jdbc.update(DELETE_LABEL_BY_ARTIST, id);

        final String DELETE_LABEL = "DELETE FROM label WHERE labelId =?";
        jdbc.update(DELETE_LABEL, id);

    }

    @Override
    public List<Label> getSongByGenre(Label label) {
        final String SELECT_SONG_BY_GENRE = "SELECT * FROM Artist WHERE labelId = ?";
        return jdbc.query(SELECT_SONG_BY_GENRE, new LabelMapper(), label.getId());
    }

    public static final class LabelMapper implements RowMapper<Label> {


        @Override
        public Label mapRow(ResultSet rs, int index) throws SQLException {
            Label label = new Label();
            label.setId(rs.getInt("labelId"));
            label.setName(rs.getString("labelName"));
            label.setDescription(rs.getString("labelDescription"));
            return label;
        }
    }
}
