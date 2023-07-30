package com.sg.Music.dao;

import com.sg.Music.entities.Label;
import com.sg.Music.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
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
        final String SELECT_SONGS_BY_LABEL_ARTISTS = "SELECT s.songId FROM Song s JOIN Artist a ON s.artistId = a.artistId WHERE a.labelId = ?";
        List<Integer> songIds = jdbc.queryForList(SELECT_SONGS_BY_LABEL_ARTISTS, Integer.class, id);

        // Delete the songs associated with the label's artists
        for (int songId : songIds) {
            final String DELETE_SONG_BY_ID = "DELETE FROM Song WHERE songId = ?";
            jdbc.update(DELETE_SONG_BY_ID, songId);
        }

        // Delete the artists associated with the label
        final String DELETE_ARTISTS_BY_LABEL = "DELETE FROM Artist WHERE labelId = ?";
        jdbc.update(DELETE_ARTISTS_BY_LABEL, id);

        // Delete the label
        final String DELETE_LABEL = "DELETE FROM Label WHERE labelId = ?";
        jdbc.update(DELETE_LABEL, id);
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
