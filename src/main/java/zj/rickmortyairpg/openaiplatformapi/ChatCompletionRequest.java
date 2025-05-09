package zj.rickmortyairpg.openaiplatformapi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatCompletionRequest {
    private String model;
    private List<Message> messages;
    private int max_completion_tokens;
}
