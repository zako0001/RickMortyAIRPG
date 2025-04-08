package zj.rickmortyai.openaimodel;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionRequest {

    private String model;
    private List<Message> messages;
    private int max_completion_tokens;



}
