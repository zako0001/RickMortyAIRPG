package zj.rickmortyairpg.openaimodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    private Long id;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;



}
