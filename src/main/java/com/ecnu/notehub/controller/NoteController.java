package com.ecnu.notehub.controller;

import com.ecnu.notehub.annotation.LoginRequired;
import com.ecnu.notehub.dto.NoteRequest;
import com.ecnu.notehub.search.NoteIndex;
import com.ecnu.notehub.service.NoteService;
import com.ecnu.notehub.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author onion
 * @date 2019/11/7 -7:40 下午
 */
@RestController
@RequestMapping("note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @GetMapping("/keyword")
    @LoginRequired
    public ResultEntity getByTitle(@RequestParam String title){
        Page<NoteIndex> results = noteService.findByTitle(title);
        return ResultEntity.succeed(results);
    }
    @GetMapping("/relation")
    @LoginRequired
    public ResultEntity search(@RequestParam String keyword){
        Page<NoteIndex> results = noteService.search(keyword);
        return ResultEntity.succeed(results);
    }
    @PostMapping("/addPdf")
    @LoginRequired
    public ResultEntity addPdf(@RequestParam(value = "file")MultipartFile file,
                               @RequestParam String authorId,
                               @RequestParam String authorName,
                               @RequestParam String title,
                               @RequestParam Integer authority,
                               @RequestParam String summary,
                               @RequestParam String tags,
                               @RequestParam Integer types){
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setAuthorId(authorId);
        noteRequest.setAuthorName(authorName);
        noteRequest.setFile(file);
        noteRequest.setTitle(title);
        noteRequest.setAuthority(authority);
        noteRequest.setSummary(summary);
        String[] split = tags.split(",");
        noteRequest.setTags(new HashSet<>(Arrays.asList(split)));
        noteRequest.setTypes(types);
        noteService.addPdf(noteRequest);
        return ResultEntity.succeed();
    }
    @PostMapping("/addNote")
    @LoginRequired
    public ResultEntity addNote(@RequestBody NoteRequest noteRequest){
        noteService.addNote(noteRequest);
        return ResultEntity.succeed();
    }
    @GetMapping("/downloadNote")
    @LoginRequired
    public ResultEntity downloadPdf(@RequestParam String noteId){
        String link  = noteService.findPdfById(noteId);
        return ResultEntity.succeed(link);
    }
}
