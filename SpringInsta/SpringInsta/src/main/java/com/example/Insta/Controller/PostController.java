package com.example.Insta.Controller;

import com.example.Insta.DTO.CommentDTO;
import com.example.Insta.DTO.RequestPostDTO;
import com.example.Insta.Model.Post;
import com.example.Insta.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/myposts")
    public ResponseEntity<List<Post>> showMyPosts(@AuthenticationPrincipal UserDetails user){

        String username = user.getUsername();

        List<Post> posts = postService.getPosts(username);
        return new ResponseEntity<>(posts, HttpStatus.FOUND);
    }

    @GetMapping("/posts/{username}")
    public ResponseEntity<List<Post>> showPosts(@PathVariable String username){


        List<Post> posts = postService.getPosts(username);
        return new ResponseEntity<>(posts, HttpStatus.FOUND);
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> addPost(@AuthenticationPrincipal UserDetails user,@RequestPart("postDTO") RequestPostDTO postDTO, @RequestPart("image") MultipartFile image){

        String username = user.getUsername();


        Post savedPost = postService.addPost(postDTO,image,username);
        if(savedPost!=null) {
            return new ResponseEntity<>(savedPost,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(savedPost,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/addcomment/{id}")
    public ResponseEntity<Post> addComment(@AuthenticationPrincipal UserDetails user,@PathVariable int id, @RequestBody CommentDTO commentDTO){

        String username = user.getUsername();

        Post updatedPost = postService.updateComment(id,commentDTO,username);
        return new ResponseEntity<>(updatedPost,HttpStatus.ACCEPTED);
    }

    @PutMapping("/addlike/{id}")
    public ResponseEntity<Post> addLike(@AuthenticationPrincipal UserDetails user,@PathVariable int id){

        String username = user.getUsername();

        Post updatedPost = postService.updateLike(id,username);
        return new ResponseEntity<>(updatedPost,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<Post> deletePost(@AuthenticationPrincipal UserDetails user,@PathVariable int id){

        String username = user.getUsername();

        String status = postService.deletePost(id,username);
        if(status.equals("Deleted")) {
            return new ResponseEntity<>(new Post(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new Post(),HttpStatus.NOT_FOUND);
        }
    }
}
