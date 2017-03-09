package com.ccf.controller;

import com.ccf.excel.Read;
import com.ccf.excel.Write;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by chenchaofei on 2017/3/8.
 */
@RestController
@EnableAutoConfiguration
public class Example {

    private int counter=0;

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello/{myName}")
    String index(@PathVariable String myName) {
        return "Hello "+myName+"!!!";
    }

    @SuppressWarnings("deprecation")
    @RequestMapping("/now")
    String hehe() {
        return "现在时间：" + (new Date()).toLocaleString();
    }

    @RequestMapping("/counter")
    int counter() {
        return ++this.counter;
    }

    @RequestMapping("/excel/write")
    ResponseEntity<InputStreamResource> write(Write ew, StopWatch sw) {
        try {
            sw.start("write excel");
            String filePath = ew.gen();
            sw.stop();
            System.out.println("gen time:" + sw.prettyPrint());

            FileSystemResource file = new FileSystemResource(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/excel/read")
    Map<Integer, String> read(Read er) {
        try {
            // 对读取Excel表格标题测试
            Map<Integer, String> map = er.getContent("/tmp/test.xlsx");
//            for (int i = 0; i < map.size(); i++) {
//                System.out.println(map.get(i));
//            }
            return map;
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
        return null;
    }
}
