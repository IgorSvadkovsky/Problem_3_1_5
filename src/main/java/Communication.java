import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Communication {
    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();
    private static String setCookie = "";

    public static void main(String[] args) {
        System.out.println(getAllUsers());

        User user = new User(3L,"James", "Brown", (byte) 22);

        String addUserBody = addUser(user);
        System.out.println("+++++++++++++++ ADD USER: " + addUserBody);

        user.setName("Thomas");
        user.setLastName("Shelby");
        String updateUserBody = updateUser(user);
        System.out.println("+++++++++++++++ UPDATE USER: " + updateUserBody);

        String deleteUserBody = deleteUser(3L);
        System.out.println("+++++++++++++++ DELETE USER: " + deleteUserBody);

        System.out.println("RESULT = " + addUserBody + updateUserBody + deleteUserBody);
    }

    public static String getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                entity,
                String.class);

        setCookie = responseEntity.getHeaders().get("Set-Cookie").get(0);

        return responseEntity.getBody();
    }

    public static String addUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Cookie", setCookie);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        System.out.println("New user was added");

        return (String) responseEntity.getBody();
    }

    public static String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Cookie", setCookie);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                entity,
                String.class
        );

        System.out.println("User with id = " + user.getId() + " was updated");

        return (String) responseEntity.getBody();
    }

    public static String deleteUser(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Cookie", setCookie);
        HttpEntity<User> entity = new HttpEntity<>(headers);

        ResponseEntity responseEntity = restTemplate.exchange(
                URL + "/" + id,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        System.out.println("User with id = " + id + " was deleted");

        return (String) responseEntity.getBody();
    }
}
