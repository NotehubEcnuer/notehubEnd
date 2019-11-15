package com.ecnu.notehub.task;

import com.ecnu.notehub.dao.NoteDao;
import com.ecnu.notehub.dao.NoteSearchDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author onion
 * @date 2019/11/15 -1:28 下午
 * 定时任务，每天早上2点拷贝Mongodb数据到ElasticSearch
 */
@Slf4j
@Component
public class SynchronizeData {
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private NoteSearchDao noteSearchDao;

//    @Scheduled(cron = "0 0 2 ?")
//    public void copyMongoToEs(){
//        log.info("开始拷贝Mongodb数据到ElasticSearch");
//        List<Note> all = noteDao.findAllByUpdateTimeAfter(new Date());
//        all.forEach(e->{
//            NoteIndex noteIndex = new NoteIndex();
//            BeanUtils.copyProperties(e, noteIndex, "content");
//            noteSearchDao.save(noteIndex);
//        });
//    }

}
