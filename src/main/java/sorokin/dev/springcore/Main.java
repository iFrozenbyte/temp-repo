package sorokin.dev.springcore;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sorokin.dev.springcore.configuration.AppConfig;
import sorokin.dev.springcore.console.OperationsConsoleListener;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
        OperationsConsoleListener listener = context.getBean(OperationsConsoleListener.class);
        listener.startService();
    }
}