package com.example.Insta.Controller;

import com.example.Insta.DTO.RequestPostDTO;
import com.example.Insta.Model.Post;
import com.example.Insta.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> showPosts(){
        List<Post> posts = postService.getPosts();
        return new ResponseEntity<>(posts, HttpStatus.FOUND);
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addPost(@RequestPart("postDTO") RequestPostDTO postDTO, @RequestPart("image") MultipartFile image){


        Post savedPost = postService.addPost(postDTO,image);
        if(savedPost!=null) {
            return new ResponseEntity<>("Post Uploaded", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
