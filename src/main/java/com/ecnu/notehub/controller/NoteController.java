package com.ecnu.notehub.controller;

import com.ecnu.notehub.dao.NoteDao;
import com.ecnu.notehub.dao.NoteSearchDao;
import com.ecnu.notehub.domain.Note;
import com.ecnu.notehub.search.NoteIndex;
import com.ecnu.notehub.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author onion
 * @date 2019/11/7 -7:40 下午
 */
@RestController
public class NoteController {
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private NoteSearchDao noteSearchDao;
    @Autowired
    private NoteService noteService;
    @GetMapping("/keyword")
    public Page<NoteIndex> getByTitle(@RequestParam String title){
        return noteService.findByTitle(title);
    }
    @PostMapping("/synchronize")
    public String synchronize(){
        List<Note> all = noteDao.findAll();
        all.stream().forEach(e->{
            NoteIndex noteIndex = new NoteIndex();
            BeanUtils.copyProperties(e, noteIndex, "content");
            noteSearchDao.save(noteIndex);
        });
        return "finish";
    }
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
