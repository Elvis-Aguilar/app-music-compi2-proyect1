package  com.example.reproductorapp.objects;

import java.util.ArrayList;

public class ErrorSingleton {

    private static ErrorSingleton instance;
    private ArrayList<Errores> errores = new ArrayList<>();

    private String ipConfig= "192.168.0.110";

    public static ErrorSingleton getInstance(){
        if (instance == null){
            instance = new ErrorSingleton();
        }
        return  instance;
    }


    public String getIpConfig() {
        return ipConfig;
    }

    public void setIpConfig(String ipConfig) {
        this.ipConfig = ipConfig;
    }

    public ArrayList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(ArrayList<Errores> errores) {
        this.errores = errores;
    }
}
