package com.backendchallenge.vuttr.controller.dto;

import java.util.ArrayList;

public record createToolDto(String title, String link, String description, ArrayList<String> tags) {
}
