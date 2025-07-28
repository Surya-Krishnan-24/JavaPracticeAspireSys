package com.example.Insta.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    private String caption;
    private String username;
    private int likes;
    @ElementCollection
    private Map<String,Boolean> userLike = new HashMap<>();
    @ElementCollection
    private Map<String,String> comments = new HashMap<>();
    @Lob
    private byte[] imageData;
    private String imageName;
    private String imageType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;
}
