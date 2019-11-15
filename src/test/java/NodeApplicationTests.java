import com.ecnu.notehub.NoteApplication;
import com.ecnu.notehub.dao.NoteDao;
import com.ecnu.notehub.dao.NoteSearchDao;
import com.ecnu.notehub.domain.Note;
import com.ecnu.notehub.search.NoteIndex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author onion
 * @date 2019/11/8 -6:31 下午
 */

@SpringBootTest(classes = NoteApplication.class)
public class NodeApplicationTests {
    @Autowired
    private NoteDao noteDao;

    @Autowired
    private NoteSearchDao noteSearchDao;

    @Test
    public void testTime(){
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        System.out.println(localDateTime);
    }

    @Test
    @Transactional
    public void testSynchronize(){
        List<Note> all = noteDao.findAll();
        all.stream().forEach(e->{
            NoteIndex noteIndex = new NoteIndex();
            BeanUtils.copyProperties(e, noteIndex, "content");
            noteSearchDao.save(noteIndex);
        });
    }
    @Test
    public void addNote() throws Exception {
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/root1/Downloads/JavaProject/Notehub/src/main/resources/arch.csv"), StandardCharsets.UTF_8));
        String record;
        Note note = new Note();
        file.readLine();
        int cnt = 659;
        while ((record = file.readLine())!= null) {
            String fields[] = record.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            note.setId(cnt + "");
            note.setDownloads(Integer.parseInt(fields[0]));
            note.setTitle(fields[9]);
            note.setAuthorId(fields[13]);
            note.setVisits(Integer.parseInt(fields[17]));
            note.setContent(fields[18]);
            note.setAuthorName(fields[19]);
            note.setSummary(fields[22]);
            cnt ++;
//            note.setCreateTime(new Date());
//            note.setUpdateTime(new Date());
            note.setHates(0);
            note.setStars(0);
            note.setTypes(0);
            note.setAuthority(0);
            note.setTags(Arrays.asList("算法"));
            noteDao.save(note);
        }

    }
}
