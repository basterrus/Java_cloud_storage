import java.util.HashMap;
import java.util.Map;

public class AuthServer {

    private static Map<String, String> loginMap = new HashMap<>();
    static {
        loginMap.put("user","Qwer1234");
        loginMap.put("putin","Qwer1234");
        loginMap.put("gitler","Qwer1234");
    }

    public boolean authSuccess(String login, String password){
        String tempPass = loginMap.get(login);
        if (tempPass!=null && tempPass.equals(password)){
            return true;
        }else{
            return false;
        }

    }

}
