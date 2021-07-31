package engine;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class QuizController {
    List<Quiz> quizzes = new ArrayList<>();
    @GetMapping(value = "/api/quizzes/{id}", produces = "application/json")
    @ResponseBody
    public String getQuiz(@PathVariable int id){
        //parse json
        return new Gson().toJson(getQuizJson(getQuizByID(id)));
    }

    @PostMapping(value = "/api/quizzes/{id}/solve", produces = "application/json")
    @ResponseBody
    public String solveQuiz(@RequestBody String answer, @PathVariable int id){
        return solve(getQuizByID(id), answer);
    }

    @PostMapping(value = "/api/quizzes", produces = "application/json")
    @ResponseBody
    public String newQuiz(@RequestBody String json){
        Gson gson = new Gson();
        JsonObject jObj = new JsonObject();
        try {
            jObj = new JsonParser().parse(json).getAsJsonObject();
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //test out the json
        requestHandler(jObj);

        Quiz quiz = new Quiz();
        int id = idGenerator();
        quiz.setId(id);
        quiz.setTitle(jObj.get("title").getAsString());
        quiz.setText(jObj.get("text").getAsString());
        quiz.setOptions(gson.fromJson(jObj.get("options").getAsJsonArray(), List.class));
        try {
            List<Integer> list = gson.fromJson(jObj.get("answer").getAsJsonArray(), List.class);
            quiz.setAnswer(list);
        }catch(Exception ignored) {}
        quizzes.add(quiz);

        return getQuiz(id);
    }

    @GetMapping(value = "api/quizzes", produces = "application/json")
    @ResponseBody
    public String getAll(){
        JsonArray jArr = new JsonArray();
        for(int i = 0; i < quizzes.size();i++){
            jArr.add(getQuizJson(quizzes.get(i)));
        }
        return new Gson().toJson(jArr);
    }


    private JsonObject getQuizJson(Quiz quiz){
        JsonObject jObj = new JsonObject();
        jObj.addProperty("id", quiz.getId());
        jObj.addProperty("title", quiz.getTitle());
        jObj.addProperty("text", quiz.getText());
        jObj.add("options", getJsonArr(quiz.getOptions()));
        return jObj;
    }

    private JsonArray getJsonArr(List<String> arr){
        JsonArray jArr = new JsonArray();
        for(int i = 0; i < arr.size(); i++){
            jArr.add(arr.get(i));
        }
        return jArr;
    }

    private String solve(Quiz quiz, String input){
        boolean success = false;
        String feedback = "Wrong answer! Please, try again.";
        JsonObject jObj = new JsonParser().parse(input).getAsJsonObject();
        Gson gson = new Gson();
        List<Integer> list = gson.fromJson(jObj.get("answer").getAsJsonArray(), List.class);
        List<Integer> ans = quiz.getAnswer();
        Collections.sort(list);
        Collections.sort(ans);
        if (ans.equals(list)) {
            success = true;
            feedback = "Congratulations, you're right!";
        }

        JsonObject jObj2 = new JsonObject();
        jObj2.addProperty("success", success);
        jObj2.addProperty("feedback", feedback);
        return new Gson().toJson(jObj2);
    }

    private int idGenerator(){
        return (int)(Math.random() * 999 + 1);
    }

    private Quiz getQuizByID(int id){
        for(int i = 0; i < quizzes.size(); i++){
            Quiz q = quizzes.get(i);
            if(q.getId() == id){
                return q;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private void requestHandler(JsonObject jObj) {
        Gson gson = new Gson();
        try{
            if(jObj.get("title").getAsString().isBlank()) throw new Exception();
            if(jObj.get("text").getAsString().isBlank()) throw new Exception();
            if(gson.fromJson(jObj.get("options").getAsJsonArray(), List.class).size() < 2) throw new Exception();
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}