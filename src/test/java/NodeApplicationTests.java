import com.ecnu.notehub.NoteApplication;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * @author onion
 * @date 2019/11/8 -6:31 下午
 */

@SpringBootTest(classes = NoteApplication.class)
//@WebMvcTest(value = NoteController.class)

public class NodeApplicationTests {
    @Value("${qiniu.access-key}")
    String accessKey;
    @Value("${qiniu.secret-key}")
    String secretKey;
    @Value("${qiniu.bucket}")
    String bucket;

    @Test
    public void test(){
        System.out.println(accessKey);
        System.out.println(secretKey);
        System.out.println(bucket);
    }
//    @Autowired
//    private NoteController noteController;
//    @Autowired
//    private MockMvc mockMvc;
//
//    private MockMultipartFile multipartFile;
//    @Test
//    public void testAddNote() throws Exception{
//        File file = new File("/Users/root1/Desktop/note/.pdf");
//        multipartFile = new MockMultipartFile("heap", new FileInputStream(file));
//        MvcResult mvcResult = mockMvc.perform(multipart("/note/addNote")
//                .file(multipartFile)
//                .param("authorId", "969023014")
//                .param("authorName", "onion")
//                .param("title", "heap")
//                .param("authority", "2")
//                .param("tags", "[算法,数据结构]")
//                .param("summary", "刘宇波老师算法笔记")
//                .param("types", "3")).andReturn();
//        ResultEntity result = JSON.parseObject(mvcResult.getResponse().getContentAsByteArray(), ResultEntity.class);
//        System.out.println(result);
//    }
//    @Test
//    public void download() throws UnsupportedEncodingException {
//        String fileName = "逻辑回归.pdf";
//        String domainOfBucket = "https://developer.qiniu.com/";
//        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
//        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
//        Auth auth = Auth.create(accessKey, secretKey);
//        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
//        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
//        System.out.println(finalUrl);
//    }
    @Test
    public void testUploadFile(){
        Configuration cfg = new Configuration(Region.region2());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
//        String accessKey = "SStPJbNpriAFEzb0LvB1ooO7X__CB5xpwt8cE8UE";
//        String secretKey = "aMbK1T32B4_Gidwm_zbFAVPmaBG2j-DgZkEQLEmT";
//        String bucket = "notehub";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "/Users/root1/Desktop/note/堆.pdf";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "堆.pdf";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
