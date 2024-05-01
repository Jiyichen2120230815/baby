package com.babyTalk.base.http.httpclient.util;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class HttpFileUtils {

    @Value("${path.static}")
    private String staticPath;

    /**
     * 需要改进  TODO
     * @param file
     * @param url
     * @return
     * @throws IOException
     */
    //通过传递文件参数  mp3
    public static String getResponseEntityBody(MultipartFile file,String url,String path) throws IOException {
//        String url = "http://127.0.0.1:8003/infer";//访问链接
        //Objects.requireNonNull    如果所要判断的元素为 null, 则返回空指针异常 NullPointerException, 否则直接返回对应的对象
        File convFile = new File(path);
        if (!convFile.exists()) {
            convFile.mkdirs();
        }

        final File filePath = new File(path + file.getOriginalFilename());

//        convFile.createNewFile();//创建这个文件
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(file.getBytes());
        outputStream.close();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String,Object> form = new LinkedMultiValueMap<>();

        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        if (fileSystemResource.exists()) {
            form.add("file",fileSystemResource);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String,Object>> datas = new HttpEntity<>(form,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,datas,String.class);
        System.out.println(responseEntity);
        if (responseEntity.getBody() == null) {
            return "responseEntity.getBody() is null";
        }
        return responseEntity.getBody();
    }
}
