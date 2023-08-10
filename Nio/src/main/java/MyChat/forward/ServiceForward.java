package MyChat.forward;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Author Lc
 * @Date 2023/8/10
 * @PackageName: MyChat.forword
 * @ClassName: ServiceFroword
 * @Description:
 */

public class ServiceForward {

    /**
     * 转发消息到其他客户端
     * msg 消息
     * noNotifyChannel 不需要通知的Channel
     */
    public static void notifyAllClient(String msg, SocketChannel noNotifyChannel, Selector selector)  {
        try {
            System.out.println("服务器转发消息~");
            for (SelectionKey selectionKey : selector.keys()) {
                Channel channel = selectionKey.channel();
                //channel的类型实际类型是SocketChannel，并且排除不需要通知的通道
                if (channel instanceof SocketChannel && channel != noNotifyChannel) {
                    //强转成SocketChannel类型
                    SocketChannel socketChannel = (SocketChannel) channel;
                    //通过消息，包裹获取一个缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                    socketChannel.write(byteBuffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
