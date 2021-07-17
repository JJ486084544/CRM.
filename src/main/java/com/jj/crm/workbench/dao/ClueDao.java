package com.jj.crm.workbench.dao;


import com.jj.crm.workbench.entity.Clue;

public interface ClueDao {


    int saveClue(Clue clue);

    Clue detailClueById(String id);

    Clue getClueById(String clueId);

    int deleteClue(String clueId);
}
