package com.blogging.PJBlogging.controller;

import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.model.ImageModel;
import com.blogging.PJBlogging.model.User;
import com.blogging.PJBlogging.repository.ImageRepository;
import com.blogging.PJBlogging.repository.UserRepository;
import com.blogging.PJBlogging.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @PostMapping("/upload")
    public ResponseEntity<ImageModel> storeImage(@RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        System.out.println("Original file size: " + multipartFile.getBytes().length);
        ImageModel image = imageRepository.findByUserAndUsageStr(authService.getCurrentUser(), "profilePic").orElse(
         ImageModel.builder().imgName(multipartFile.getOriginalFilename())
                .type(multipartFile.getContentType())
                .picByte(compressBytes(multipartFile.getBytes()))
                .user(authService.getCurrentUser())
                .usageStr("profilePic")
                .build()
        );
        image.setImgName(multipartFile.getOriginalFilename());
        image.setType(multipartFile.getContentType());
        image.setPicByte(compressBytes(multipartFile.getBytes()));
        imageRepository.save(image);
        System.out.println("Image inserted.");
        return ResponseEntity.ok().body(image);
    }
    @GetMapping("/read/{userName}")
    public ImageModel getImage(@PathVariable("userName") String userName) throws  IOException{
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SpringPJBloggingException("User not found with image!"));
        //final Optional<ImageModel> imageRetrived = imageRepository.findByUser(user);
        System.out.println("User found");
        ImageModel imageRetrived = imageRepository.findByUser(user).orElseThrow(() -> new SpringPJBloggingException("Image not found"));
        System.out.println("Image from DB retrived");
        ImageModel image = ImageModel.builder().imgName(imageRetrived.getImgName())
                .type(imageRetrived.getType())
                .picByte(decompressBytes(imageRetrived.getPicByte())).build();
        System.out.println("sending response");
        return  image;
    }

    private byte[] decompressBytes(byte[] picByte) {
        Inflater inflater = new Inflater();
        inflater.setInput(picByte);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(picByte.length);
        System.out.println("Byte Length: " + picByte.length);
        byte[] buffer = new byte[1024];
        try{
            while(!inflater.finished()){
                int count = inflater.inflate(buffer);
                byteArrayOutputStream.write(buffer,0,count);
            }
            byteArrayOutputStream.close();
            } catch(IOException ioe) {
            System.out.println("IOException: " + ioe);
        } catch (DataFormatException dfe) {
        System.out.println("DataFormatException: " + dfe);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] compressBytes(byte[] bytes) {
        Deflater deflater = new Deflater();
        deflater.setInput(bytes);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bytes.length);
        byte[] buffer = new byte[1024];
        while(!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer,0,count);
        }
        try {
            outputStream.close();

        }catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Compressed Image Bytesize: " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
}
