package com.ecnu.notehub.service.impl;

import com.ecnu.notehub.dao.NoteDao;
import com.ecnu.notehub.dao.NoteSearchDao;
import com.ecnu.notehub.domain.Note;
import com.ecnu.notehub.dto.NoteRequest;
import com.ecnu.notehub.enums.ResultEnum;
import com.ecnu.notehub.enums.TypeEnum;
import com.ecnu.notehub.exception.MyException;
import com.ecnu.notehub.search.NoteIndex;
import com.ecnu.notehub.service.NoteService;
import com.ecnu.notehub.util.KeyGenerateUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * @author onion
 * @date 2019/11/15 -9:20 上午
 */
@Service
@Slf4j
public class NoteServiceImpl implements NoteService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private NoteSearchDao noteSearchDao;

    @Autowired
    private NoteDao noteDao;

    @Value("${qiniu.access-key}")
    private String accessKey;
    @Value("${qiniu.secret-key}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    @Override
    public Page<NoteIndex> findByTitle(String title){

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withHighlightFields(new HighlightBuilder.Field("title").preTags("<span style=\"color:red\">").postTags("</span>"))
                .withQuery(QueryBuilders.matchQuery("title", title))
                .build();
        return noteSearchDao.search(searchQuery);
    }

    @Override
    public Page<NoteIndex> search(String keyword) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "summary");
        nativeSearchQueryBuilder.withQuery(multiMatchQueryBuilder);
        return noteSearchDao.search(nativeSearchQueryBuilder.build());
    }

    @Override
    public void addPdf(NoteRequest noteRequest) {
        String id = KeyGenerateUtil.genUniqueKey();
        Note note = new Note();
        note.setAuthorId(noteRequest.getAuthorId());
        note.setAuthority(noteRequest.getAuthority());
        note.setAuthorName(noteRequest.getAuthorName());
        note.setSummary(noteRequest.getSummary());
        note.setTitle(noteRequest.getTitle());
        note.setCreateTime(LocalDateTime.now());
        note.setUpdateTime(LocalDateTime.now());
        note.setTags(noteRequest.getTags());
        note.setId(id);
        note.setDownloads(0);
        note.setHates(0);
        note.setStars(0);
        note.setVisits(0);
        note.setFollows(0);
        if(noteRequest.getTypes() == TypeEnum.PDF.getCode()){
            if(noteRequest.getFile() == null){
                throw new MyException(ResultEnum.FILE_NOT_EXIST);
            }
            try {
                uploadFile(noteRequest.getFile(), noteRequest.getTitle(), id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            note.setContent(noteRequest.getContent());
        }
        noteDao.save(note);
    }
    private void uploadFile(MultipartFile file, String title, String id) throws Exception{
        InputStream fileInputStream = file.getInputStream();
        String key = title + id + ".pdf";
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(file);
        try {
            Response response = uploadManager.put(fileInputStream, key, upToken, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("upload file : {}", putRet);
        } catch (QiniuException ex) {
            throw new MyException(ResultEnum.FILE_UPLOAD_ERROR);
        }
    }
}
