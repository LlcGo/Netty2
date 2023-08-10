package MyChat.medol;

import Chat.GroupChatServer;
import MyChat.contant.Constant;
import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static MyChat.contant.Constant.PORT;

/**
 * @Author Lc
 * @Date 2023/8/10
 * @PackageName: MyChat.medol
 * @ClassName: Group
 * @Description:
 */

@Data
public class Group {
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;


    private Group() {
        try {
            //打开一个选择器
            this.selector = Selector.open();
            //打开serverSocketChannel
            this.serverSocketChannel = ServerSocketChannel.open();
            //绑定地址，端口号
            this.serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", PORT));
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            //把通道注册到选择器中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private static final Group INSTANCE = new Group();
    }

    public static Group getInstance(){
       return SingletonHolder.INSTANCE;
    }

    public Selector getSelector() {
        return selector;
    }



    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }


}
