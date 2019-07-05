package team.rjgc.GymSys.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author ReMidDream
 * @date 2018-04-11 20:28
 **/
public interface FileService {

    /**
     * 策略模式
     * 重载匿名内部类predicate的Test方法,对file进行过滤
     * 推荐使用 Lambda
     * @param files
     * @param predicate
     * @return 上传路径
     */
    List<String> fileUpload(MultipartFile[] files, Predicate<MultipartFile> predicate);

    /**
     *
     * @param path 路径
     * @param fileName 文件名( 必须带后缀)
     * @return ResponseEntity<byte[]> 直接返回到页面,无需其他处理
     */
    ResponseEntity<byte[]> fileDownload(String path, String fileName);


    public String doUpload(MultipartFile file) throws IOException;

}
