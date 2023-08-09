package NioFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Lc
 * @Date 2023/8/9
 * @PackageName: PACKAGE_NAME
 * @ClassName: NioFile.TestTransferTo
 * @Description:
 */

public class TestTransferTo {
    public static void main(String[] args) throws IOException {
        //获取文件输入流
        File file = new File("1.txt");
        FileInputStream inputStream = new FileInputStream(file);
        //从文件输入流获取通道
        FileChannel inputStreamChannel = inputStream.getChannel();
        //获取文件输出流
        FileOutputStream outputStream = new FileOutputStream(new File("2.txt"));
        //从文件输出流获取通道
        FileChannel outputStreamChannel = outputStream.getChannel();
        //创建一个byteBuffer，小文件所以就直接一次读取，不分多次循环了
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //把输入流通道的数据读取到输出流的通道
        inputStreamChannel.transferTo(0, byteBuffer.limit(), outputStreamChannel);
        //关闭通道
        outputStream.close();
        inputStream.close();
        outputStreamChannel.close();
        inputStreamChannel.close();
    }
}
