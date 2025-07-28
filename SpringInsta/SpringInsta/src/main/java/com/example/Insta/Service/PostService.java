package com.example.Insta.Service;

import com.example.Insta.DTO.CommentDTO;
import com.example.Insta.DTO.LikeDTO;
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
import java.util.Map;

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

    public Post updateComment(int id, CommentDTO commentDTO) {
        Post findPost = repo.findById(id).orElse(new Post());
        if(findPost.getPostId() == 0){
            return new Post();
        }
        findPost.getComments().put(commentDTO.getUsername(), commentDTO.getComment());
        repo.save(findPost);
        return findPost;
    }

    public Post updateLike(int id, LikeDTO username){
        Post findPost = repo.findById(id).orElse(new Post());
        if(findPost.getPostId() == 0){
            return new Post();
        }
        Map<String,Boolean> userLike = findPost.getUserLike();

        if(userLike.containsKey(username.getUsername()) && userLike.get(username.getUsername())){
            userLike.replace(username.getUsername(),false);
            findPost.setLikes(findPost.getLikes() - 1);
        }
        else{
            userLike.put(username.getUsername(),true);
            findPost.setLikes(findPost.getLikes() + 1);
        }

        repo.save(findPost);
        return findPost;


    }
    public String deletePost(int id) {
        Post find = repo.findById(id).orElse(new Post());
        if(find.getPostId() !=0) {
            repo.deleteById(id);
            return "Deleted";
        }
            return "Not found";

    }


}
