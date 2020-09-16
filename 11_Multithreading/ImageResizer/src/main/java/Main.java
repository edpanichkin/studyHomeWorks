import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static int newWidth = 300;
    private static final int threadCount = Runtime.getRuntime().availableProcessors();
    private static String srcFolder = "C:\\foto\\src";
    private static String dstFolder = "C:\\foto\\dst";
    private static long startProgram = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        System.out.println("Threads to be created: " + threadCount);
        threadDivider(files, threadCount + 4);
        System.out.println("ALL Threads are DONE");
    }

    public static void threadDivider(File[] files, int divider){
        int newLength = files.length/divider;
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < divider - 1; i++) {
            File[] filesPart = new File[newLength];
            System.arraycopy(files,i * newLength, filesPart, 0, newLength);
            System.out.printf("Start Thread num %s, / files count %s\n",
                    i + 1, filesPart.length);
            threadList.add(new Thread(new ImageResizer(filesPart, newWidth, dstFolder, startProgram)));
        }
        int lastLength = files.length - (divider - 1) * newLength;
        File[] filesPart = new File[lastLength];
        System.arraycopy(files,(divider - 1) * newLength, filesPart, 0, lastLength);
        System.out.printf("Start Thread num %s, / files count %s\n", divider, filesPart.length);
        threadList.add(new Thread(new ImageResizer(filesPart, newWidth, dstFolder, startProgram)));
        threadList.forEach(Thread::start);
        try {
            for (Thread t : threadList) {
                t.join();
            }
        }
            catch (InterruptedException e) {
                e.printStackTrace();
        }
    }
}
