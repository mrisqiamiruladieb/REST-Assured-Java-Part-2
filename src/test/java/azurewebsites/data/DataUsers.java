package azurewebsites.data;

import org.json.JSONObject;

import java.util.Random;

public class DataUsers {

    // create instance of Random class
    Random rand = new Random();

    // Generate random integers in range 0 to 999
    int rand_int = rand.nextInt(1000);

    public JSONObject createUserSuccess() {

        // create instance of JSONObject class
        JSONObject payload = new JSONObject();

        // Insert key value pair to jsonObject
        payload.put("id", rand_int);
        payload.put("userName", "Hello User" + rand_int);
        payload.put("password", "okay_user" + rand_int);

        return payload;

    }

    public JSONObject createUserValidationByFillingInAFewData() {

        // create instance of JSONObject class
        JSONObject payload = new JSONObject();

        // Insert key value pair to jsonObject
        payload.put("id", rand_int);
        payload.put("userName", "Hello Baron" + rand_int);

        return payload;

    }

    public JSONObject successUpdateUser() {

        // create instance of JSONObject class
        JSONObject payload = new JSONObject();

        // Insert key value pair to jsonObject
        payload.put("id", rand_int);
        payload.put("userName", "Hello User-Update" + rand_int);
        payload.put("password", "okay_user-Update" + rand_int);

        return payload;

    }

    public JSONObject validateUpdateUserWithRegisteredData() {

        // create instance of JSONObject class
        JSONObject payload = new JSONObject();

        // Insert key value pair to jsonObject
        payload.put("id", 4);
        payload.put("userName", "User 4");
        payload.put("password", "Password4");

        return payload;

    }
}
