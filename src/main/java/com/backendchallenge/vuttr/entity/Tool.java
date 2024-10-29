package com.backendchallenge.vuttr.entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "tool")
@Getter
@Setter
@AllArgsConstructor
public class Tool {

    @Id
    @Indexed(unique = true)
    private String id;

    private String title;
    private String link;
    private String description;
    private ArrayList<String> tags;

    public Tool() {
    }
}
