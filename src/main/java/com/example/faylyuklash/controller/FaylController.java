package com.example.faylyuklash.controller;

import com.example.faylyuklash.been.FaylBeen;
import com.example.faylyuklash.been.FaylSourceBeen;
import com.example.faylyuklash.model.Fayl;
import com.example.faylyuklash.model.FaylSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/fayl")
public class FaylController {

    @Autowired
    FaylBeen faylBeen;

    @Autowired
    FaylSourceBeen sourceBeen;

    @PostMapping("/yuklash")
    public String joylash(MultipartHttpServletRequest request) throws IOException {
        long current = System.currentTimeMillis();
        Iterator<String> malumot=request.getFileNames();
        MultipartFile fayl=request.getFile(malumot.next());
        if(fayl!=null){
            String orginalNomi=fayl.getOriginalFilename();
            long hajmi=fayl.getSize();
            String contentType=fayl.getContentType();
            byte[] source=fayl.getBytes();
            Fayl fayl2=new Fayl();
            fayl2.setArginalFaylNomi(orginalNomi);
            fayl2.setContentType(contentType);
            fayl2.setHajmi(hajmi);

            FaylSource source1=new FaylSource();
            source1.setSource(source);
            source1.setFayl(fayl2);
            sourceBeen.save(source1);
            System.out.println(System.currentTimeMillis() - current);
            papkagajoylash(request);
            return "Joylandi ID"+fayl2.getId();
        }
        return "Joylanmadi";
    }

    @GetMapping("/yuklash/{id}")
    public void yuklash(@PathVariable Integer id, HttpServletResponse res) throws IOException {
        Optional<Fayl> optional=faylBeen.findById(id);
        if(optional.isPresent()){
            Fayl fayl=optional.get();
            Optional<FaylSource> optional1=sourceBeen.findByFaylId(id);
            if(optional1.isPresent()){
                FaylSource faylSource=optional1.get();
                res.setContentType(fayl.getContentType());
                res.setHeader("Content-Disposition","attachment: filename=\""+fayl.getArginalFaylNomi()+"\"");
                FileCopyUtils.copy(faylSource.getSource(),res.getOutputStream());
            }
        }
    }

    String manzil="C:\\Users\\User\\IdeaProjects\\Faylyuklash\\Base\\";
    @PostMapping("/papkagaYuklash")
    public String papkagajoylash(MultipartHttpServletRequest request) throws IOException {
        long current = System.currentTimeMillis();
        Iterator<String> iterator=request.getFileNames();
        MultipartFile multipartFile= request.getFile(iterator.next());
        if(multipartFile!=null){
            Fayl fayl=new Fayl();
            fayl.setArginalFaylNomi(multipartFile.getOriginalFilename());
            fayl.setContentType(multipartFile.getContentType());
            fayl.setHajmi(multipartFile.getSize());
            String newname=multipartFile.getOriginalFilename();
            String[] split = newname.split("\\.");
            String s = UUID.randomUUID().toString()+"."+split[split.length-1];
            fayl.setNewname(s);
            faylBeen.save(fayl);
            Path paths=Paths.get(manzil+s);
            Files.copy(multipartFile.getInputStream(),paths);
            System.out.println(System.currentTimeMillis() - current);
            return "Joylandi";
            }
        return "Joylanmadi";
    }
    
    @GetMapping("/papkadanYuklash/{id}")
    public void papkadanYuklash(@PathVariable Integer id, HttpServletResponse res) throws IOException {
        Optional<Fayl> optional = faylBeen.findById(id);
        if(optional.isPresent()){
            Fayl fayl = optional.get();
            res.setContentType(fayl.getContentType());
            res.setHeader("Content-Disposition","attachment: filename=\"" + fayl.getArginalFaylNomi() + "\"");
            FileInputStream fileInputStream = new FileInputStream(manzil + "/" + fayl.getNewname());
            FileCopyUtils.copy(fileInputStream, res.getOutputStream());
        }
    }
}
