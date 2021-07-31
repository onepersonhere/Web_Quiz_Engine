package engine;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    int id;
    String title;
    String text;
    List<String> options;
    List<Integer> answer = new ArrayList<>();
}
