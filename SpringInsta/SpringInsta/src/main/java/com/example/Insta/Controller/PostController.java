package com.example.Insta.Controller;

import com.example.Insta.DTO.CommentDTO;
import com.example.Insta.DTO.LikeDTO;
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
    public ResponseEntity<Post> addPost(@RequestPart("postDTO") RequestPostDTO postDTO, @RequestPart("image") MultipartFile image){


        Post savedPost = postService.addPost(postDTO,image);
        if(savedPost!=null) {
            return new ResponseEntity<>(savedPost,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(savedPost,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/addcomment/{id}/comment")
    public ResponseEntity<Post> addComment(@PathVariable int id, @RequestBody CommentDTO commentDTO){
        Post updatedPost = postService.updateComment(id,commentDTO);
        return new ResponseEntity<>(updatedPost,HttpStatus.ACCEPTED);
    }

    @PutMapping("/addlike/{id}/like")
    public ResponseEntity<Post> addLike(@PathVariable int id, @RequestBody LikeDTO username){
        Post updatedPost = postService.updateLike(id,username);
        return new ResponseEntity<>(updatedPost,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable int id){
        String status = postService.deletePost(id);
        if(status.equals("Deleted")) {
            return new ResponseEntity<>(new Post(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new Post(),HttpStatus.NOT_FOUND);
        }
    }
}
