package engine.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> userList = new ArrayList<>();
    @Autowired
    UserService service;

    @PostMapping(value = "/api/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody String json){
        Import();
        JsonObject jObj = new JsonParser().parse(json).getAsJsonObject();
        for(User user : userList){
            if(user.getEmail().equals(jObj.get("email").getAsString())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        newUser(json);
    }

    private void newUser(String json){
        User user = new User();
        JsonObject jObj = new JsonParser().parse(json).getAsJsonObject();
        if(!jObj.get("email").getAsString().contains("@") ||
                !jObj.get("email").getAsString().contains("."))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if(jObj.get("password").getAsString().length() < 5 ||
                jObj.get("password").getAsString().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String password = jObj.get("password").getAsString();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setEmail(jObj.get("email").getAsString());
        user.setPassword(passwordEncoder.encode(password));
        userList.add(user);
        service.saveNewUser(user);
    }

    public void Import(){
        userList = service.getAllUser();
    }
}
