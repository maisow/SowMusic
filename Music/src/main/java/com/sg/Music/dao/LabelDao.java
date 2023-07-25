package com.sg.Music.dao;

import com.sg.Music.entities.Label;
import com.sg.Music.entities.Song;

import java.util.List;

public interface LabelDao {

    Label getLabelByID(int id);
    List<Label> getAllLabels();
    Label addLabel(Label label);
    void updateLabel(Label label);
    void deleteLabelByID(int id);
    List<Label> getSongByGenre(Label label);

}
