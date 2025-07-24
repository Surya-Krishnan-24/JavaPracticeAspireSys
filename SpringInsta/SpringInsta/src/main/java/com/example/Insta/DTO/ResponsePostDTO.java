package com.example.Insta.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Lob;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResponsePostDTO {

    private int postId;
    private String caption;
    private String username;
    private int likes;
    private List<String> comments;
    @Lob
    private byte[] imageData;
    private String imageName;
    private String imageType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;
}
