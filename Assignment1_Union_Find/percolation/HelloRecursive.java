/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class HelloRecursive {
    public static void hello(int c) {
        int count = c;
        System.out.println("Hello! " + count);
        //Thread.sleep(100);
        count++;
        hello(count);
    }

    public static void main(String[] args) {
        int iniC = 0;
        hello(iniC);
    }
}
