public class RunMultiThreadClient {
public static void main(String[] args){
    Thread threadTerminal1 = new Thread(new Client("src/main/resources/terminal1.xml"));
    threadTerminal1.start();

    Thread threadTerminal2 = new Thread(new Client("src/main/resources/terminal2.xml"));
    threadTerminal2.start();

    //Thread threadTerminal3 = new Thread(new Client("C:\\Users\\DotinSchool2\\Desktop\\DotinProject2\\src\\main\\resources\\terminal3.xml"));
   //threadTerminal3.start();
}
}
