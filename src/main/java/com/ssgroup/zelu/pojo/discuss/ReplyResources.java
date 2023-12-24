package com.ssgroup.zelu.pojo.discuss;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@Setter
public class ReplyResources {

    private List<String> images;

    private List<String> videos;

    private List<String> audios;

    @Nullable
    public static String toString(ReplyResources resources) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(resources);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Nullable
    public static ReplyResources fromString(String str) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, ReplyResources.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
