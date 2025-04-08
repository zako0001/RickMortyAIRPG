package zj.rickmortyai.openaimodel;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionResponse {

    private List<Choice> choices;

}
