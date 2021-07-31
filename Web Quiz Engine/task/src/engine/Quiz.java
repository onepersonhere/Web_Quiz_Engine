package engine;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @Column
    int id;

    @Column
    String title;

    @Column
    String text;

    @ElementCollection
    @Column
    List<String> options;

    @ElementCollection
    @Column
    List<Integer> answer = new ArrayList<>();
}
