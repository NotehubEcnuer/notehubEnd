package com.ecnu.notehub.controller;

import com.ecnu.notehub.dao.NoteDao;
import com.ecnu.notehub.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        noteDao.save(note);
        return "success";
    }
}
