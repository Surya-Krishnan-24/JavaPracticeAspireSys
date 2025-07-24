package com.example.Insta.Service;

import com.example.Insta.DTO.RequestPostDTO;
import com.example.Insta.Exception.ImageNotFoundException;
import com.example.Insta.Model.Post;
import com.example.Insta.Repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepo repo;

    public List<Post> getPosts() {
        List<Post> posts = repo.findAll();
        return posts;
    }

    public Post addPost(RequestPostDTO postDTO, MultipartFile image) {
        Post post = new Post();
        post.setCaption(postDTO.getCaption());
        try {
            post.setImageData(image.getBytes());
        }
        catch (IOException e){
            throw new ImageNotFoundException(e.getMessage());
        }
        post.setLikes(0);
        post.setImageName(image.getOriginalFilename());
        post.setImageType(image.getContentType());
        post.setUsername(postDTO.getUsername());
        post.setReleaseDate(new Date());
        Post savedPost = repo.save(post);
        return savedPost;
    }
}
