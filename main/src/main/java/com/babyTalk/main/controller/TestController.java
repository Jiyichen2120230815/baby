package com.babyTalk.main.controller;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public ResponseData<Object> test(){
        return Result.success("test");
    }

//    //相对路径
//    @PostMapping("/testFormData/relative")
//    public ResponseData<Object> testFormData1(MultipartFile file) throws IOException {
//        String url = "http://127.0.0.1:8003/infer";//访问链接
//        String filePath = "now.mp3";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        MultiValueMap<String,Object> form = new LinkedMultiValueMap<>();
//
//        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
//        if (fileSystemResource.exists()) {
//            form.add("file",fileSystemResource);
//        }
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<MultiValueMap<String,Object>> datas = new HttpEntity<>(form,headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,datas,String.class);
//        System.out.println(responseEntity);
//        if (responseEntity.getBody() == null) {
//            return Result.error("responseEntity.getBody() is null");
//        }
//        final String result = responseEntity.getBody().toString();
//        return Result.success(result);
//    }
//
//    //绝对路径
//    @PostMapping("/testFormData/absolutely")
//    public ResponseData<Object> testFormData2(MultipartFile file) throws IOException {
//        String url = "http://127.0.0.1:8003/infer";//访问链接
//        String filePath = "/var/www/baby_talk_8002/now.mp3";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        MultiValueMap<String,Object> form = new LinkedMultiValueMap<>();
//
//        FileSystemResource fileSystemResource = new FileSystemResource(filePath);
//        if (fileSystemResource.exists()) {
//            form.add("file",fileSystemResource);
//        }
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<MultiValueMap<String,Object>> datas = new HttpEntity<>(form,headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,datas,String.class);
//        System.out.println(responseEntity);
//        if (responseEntity.getBody() == null) {
//            return Result.error("responseEntity.getBody() is null");
//        }
//        final String result = responseEntity.getBody().toString();
//        return Result.success(result);
//    }

    //通过传递文件参数  mp3
    @PostMapping("/testFormData_http")
    public ResponseData<Object> testFormData_http(MultipartFile file) throws IOException {
        String url = "http://127.0.0.1:8003/infer";//访问链接
        //Objects.requireNonNull    如果所要判断的元素为 null, 则返回空指针异常 NullPointerException, 否则直接返回对应的对象
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        convFile.createNewFile();//创建这个文件


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String,Object> form = new LinkedMultiValueMap<>();

        FileSystemResource fileSystemResource = new FileSystemResource(convFile);
        if (fileSystemResource.exists()) {
            form.add("file",fileSystemResource);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String,Object>> datas = new HttpEntity<>(form,headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,datas,String.class);
        System.out.println(responseEntity);
        if (responseEntity.getBody() == null) {
            return Result.error("responseEntity.getBody() is null");
        }
        final String result = responseEntity.getBody();
        return Result.success(result);
    }

    @PostMapping
    public ResponseData<Object> testPost(MultipartFile file) throws IOException {

        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8003/infer";//访问链接
//        MultiValueMap<String,String> map = new LinkedMultiValueMap();
        Map<String,String> map = new HashMap<>();
        Map<String, InputStream> map11 = new HashMap<>();
        map11.put("file",file.getInputStream());
        final ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, map11, String.class);
        System.out.println("stringResponseEntity.getBody()"+stringResponseEntity.getBody());
        System.out.println("stringResponseEntity.getStatusCode()"+stringResponseEntity.getStatusCode());
        System.out.println("stringResponseEntity.toString()"+stringResponseEntity.toString());
        map.put("stringResponseEntity.getBody()",stringResponseEntity.getBody());
        map.put("stringResponseEntity.getStatusCode()",stringResponseEntity.getStatusCode().toString());
        map.put("stringResponseEntity.toString()",stringResponseEntity.toString());
        return Result.success(map);
    }

//    @OperationLogAnnotation(type = CommonConstants.TEST,content = "输出了hello")
//    @GetMapping("/hello")
////    @PreAuthorize("hasAnyRole('ROLE_DEV','ROLE_PM')")
//    public ResponseData<Object> hello(){
//        return Result.success("hello");
//    }
//
////    @PreAuthorize("hasAnyRole('ROLE_DEV','ROLE_PM')")
////@OperationLogAnnotation(operation = CommonConstants.TEST)
//    @GetMapping("/index")
//    @UnLogin
//    public String index(@RequestBody @Validated LoginVO loginVO){
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        System.out.println(request.getContextPath());
//        System.out.println(request.getParameter("username"));
////        request.getParameter()
//        return "index";
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping("/admin")
//    public String admint(){
//        return "index";
//    }
//    @PreAuthorize("hasAnyAuthority('1')")
//    @GetMapping("/admin1")
//    public String admint1(){
//        return "index1";
//    }
//    @PreAuthorize("hasAnyAuthority('2')")
//    @GetMapping("/admin2")
//    public String admint2(){
//        return "index2";
//    }
//    @PreAuthorize("hasAnyAuthority('3')")
//    @GetMapping("/admin3")
//    public String admint3(){
//        return "index3";
//    }
//
//    @PostMapping
//    public String postTest(@RequestBody Object aaa){
//        System.out.println(aaa);
//
//        return "111";
//    }
//
//    @GetMapping
//    public String getAll(){
//        return null;
//    }
}
