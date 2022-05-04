package main

import main.TcpServer.socket
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

object TcpServer {
    private val buf: ByteBuffer = ByteBuffer.allocate(600)
    private val bufReceive: ByteBuffer = ByteBuffer.allocate(600)
    val byteArray=ByteArray(500){
        0.toByte()
    }
    val localPort = 8888
    lateinit var channel: DatagramChannel
    fun initUdp() {
        try {
            channel = DatagramChannel.open();
            channel.socket().bind(InetSocketAddress(localPort));
        } catch (e: IOException) {

            e.printStackTrace();
        }
    }


    fun bytebuffer2ByteArray(buffer: ByteBuffer): ByteArray? {
        buffer.flip()
        val len = buffer.limit() - buffer.position()
        val bytes = ByteArray(len)
        for (i in bytes.indices) {
            bytes[i] = buffer.get()
        }
        return bytes
    }



    fun ip2String(s: InetAddress):String{
        var ip=s.toString()
        ip=ip.substring(ip.lastIndexOf("/")+1)
        return ip
    }

    var socket: Socket?=null

    private fun startListen() {
        while (true) {
            try {
                val buffer = ByteArray(2000)
                val input= socket?.getInputStream()
                val byteSize = input?.read(buffer)
                if (byteSize != null) {
                    if (byteSize > 0) {
                        val bytes= byteSize.let { buffer.copyOfRange(0, it) }
                        println(String(bytes))
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun send2Destination(message: String, ip: String, port: Int) {
        try {
            val buf: ByteBuffer = ByteBuffer.allocate(600)
            val configInfo = message.toByteArray()
            buf.clear()
            buf.put(configInfo)
            buf.flip()
            channel.send(buf, InetSocketAddress(ip, port))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        System.out.println("fuck")
        initUdp()
        Thread{
            socket= Socket("192.168.6.108",8888);
            startListen()
        }.start()

        val bb=ByteArray(1500) {
            0.toByte()
        }
        Thread{
            Thread.sleep(5000)
            while (true) {
                try {
                    send(bb)
                    send(bb)
                    send(bb)
                    send(bb)
                    send(bb)
                }catch (e:Exception){

                }

            }
        }.start()


    }

    private fun send(b:ByteArray){
        try {
            val output= socket?.getOutputStream()
            output?.write(b)
            output?.flush()
        }catch (e:java.lang.Exception){

        }

    }
}