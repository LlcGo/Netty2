package MyChat.listen;

import MyChat.medol.Group;
import MyChat.read.ServiceRead;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author Lc
 * @Date 2023/8/10
 * @PackageName: MyChat.listen
 * @ClassName: ServiceListen
 * @Description:
 */

public class ServiceListen {

   private Group group = Group.getInstance();

   private Selector selector = group.getSelector();

   private ServerSocketChannel serverSocketChannel = group.getServerSocketChannel();

    public String listen()  {
        while (true) {
            try {
                //获取监听的事件总数
                int count = selector.select(2000);
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    //获取SelectionKey集合
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        //如果是获取连接事件
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            //设置为非阻塞
                            socketChannel.configureBlocking(false);
                            //注册到选择器中
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线了~");
                        }
                        //如果是读就绪事件
                        if (key.isReadable()) {
                            //读取消息，并且转发到其他客户端
                            ServiceRead.readData(key,selector);
                        }
                        it.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
