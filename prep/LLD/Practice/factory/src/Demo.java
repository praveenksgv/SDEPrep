import static java.lang.IO.print;

public class Demo {
    public static void main() {
        Notifier notifier1 = NotificationFactory.create(NotificationType.EMAIL);
        Notifier notifier2 = NotificationFactory.create(NotificationType.SMS);
        notifier2.send("Hello buddy");
        notifier1.send("Hey motherf**ker");
    }
}

enum NotificationType {
    EMAIL,
    SMS
}

class NotificationFactory {
    public static Notifier create(NotificationType notificationType){
        return switch (notificationType){
            case NotificationType.EMAIL -> new EmailNotifier();
            case NotificationType.SMS -> new SMSNotifier();
        };
    }
}

interface Notifier {
    public void send(String message);
}

class EmailNotifier implements Notifier {
    public void send(String message){
        System.out.println("Sending the email notification: " + message);
    }
}

class SMSNotifier implements Notifier {
    public void send(String message){
        System.out.println("Sending the SMS notification: " + message);
    }
}