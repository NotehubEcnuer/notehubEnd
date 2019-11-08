package com.ecnu.notehub.controller;

import com.ecnu.notehub.dao.NoteDao;
import com.ecnu.notehub.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author onion
 * @date 2019/11/7 -7:40 下午
 */
@RestController
public class NoteController {
    @Autowired
    private NoteDao noteDao;
    @PostMapping("/addNote")
    public String addNote(@RequestBody Note note){
        note.setCreateTime(new Date());
        note.setUpdateTime(new Date());
        note.setDownloads(0);
        note.setHates(0);
        note.setStars(0);
        note.setVisits(0);
        note.setTypes(0);
        note.setAuthority(0);
        noteDao.save(note);
        return "success";
    }
}
