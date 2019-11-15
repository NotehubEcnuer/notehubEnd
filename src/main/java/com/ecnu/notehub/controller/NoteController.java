package com.ecnu.notehub.controller;

import com.ecnu.notehub.search.NoteIndex;
import com.ecnu.notehub.service.NoteService;
import com.ecnu.notehub.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author onion
 * @date 2019/11/7 -7:40 下午
 */
@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;
    @GetMapping("/keyword")
    public ResultEntity getByTitle(@RequestParam String title){
        Page<NoteIndex> results = noteService.findByTitle(title);
        return ResultEntity.succeed(results);
    }
}
