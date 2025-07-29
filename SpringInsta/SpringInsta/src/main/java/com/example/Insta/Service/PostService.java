package com.example.Insta.Service;

import com.example.Insta.DTO.CommentDTO;
import com.example.Insta.DTO.RequestPostDTO;
import com.example.Insta.Exception.ImageNotFoundException;
import com.example.Insta.Exception.UserNotFoundException;
import com.example.Insta.Model.Post;
import com.example.Insta.Model.User;
import com.example.Insta.Repo.PostRepo;
import com.example.Insta.Repo.UserRepo;
import jakarta.transaction.Transactional;
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
    PostRepo postrepo;
    @Autowired
    UserRepo userrepo;


    @Transactional
    public List<Post> getPosts(String username) {
        User user = userrepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not Exist"));

        List<Post> posts = postrepo.findByUsername(username);
        return posts;
    }

    public Post addPost(RequestPostDTO postDTO, MultipartFile image, String username) {

        User checkUser = userrepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Create a Account before posting a post"));

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
        post.setUsername(username);
        post.setReleaseDate(new Date());
        Post savedPost = postrepo.save(post);
        return savedPost;
    }

    public Post updateComment(int id, CommentDTO commentDTO, String username) {

        User user = userrepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Create a Account to post a Comment"));

        Post findPost = postrepo.findById(id).orElse(new Post());
        if(findPost.getPostId() == 0){
            return new Post();
        }
        findPost.getComments().put(username, commentDTO.getComment());
        postrepo.save(findPost);
        return findPost;
    }

    public Post updateLike(int id, String username){

        User user = userrepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Create a Account to post a Comment"));

        Post findPost = postrepo.findById(id).orElse(new Post());
        if(findPost.getPostId() == 0){
            return new Post();
        }
        Map<String,Boolean> userLike = findPost.getUserLike();

        if(userLike.containsKey(username) && userLike.get(username)){
            userLike.replace(username,false);
            findPost.setLikes(findPost.getLikes() - 1);
        }
        else{
            userLike.put(username,true);
            findPost.setLikes(findPost.getLikes() + 1);
        }

        postrepo.save(findPost);
        return findPost;


    }
    public String deletePost(int id, String username) {

        Post find = postrepo.findById(id).orElse(new Post());
        if(find.getUsername().equals(username) ) {
            if (find.getPostId() != 0) {
                postrepo.deleteById(id);
                return "Deleted";
            }
        }
            return "Not found";

    }


}
