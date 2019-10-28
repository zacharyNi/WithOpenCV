package com.nzd.photo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;


import javax.swing.*;

public class Window extends JFrame{
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private JButton[] buttons;
    private String[] name = {"去雾","油画","锐化","边缘获取"};
    private JTextField textfield;
    private JPanel MyButtons;
    Mat mat;
    JLabel imageView;
    public Window()
    {
        setTitle("图片处理器");

        setSize(300,400);
        this.setLocation(700, 350);

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        textfield = new JTextField(30);
        textfield.setEditable(true);
        textfield.setHorizontalAlignment(textfield.LEFT);
        textfield.setPreferredSize(new Dimension(200,30));//setprefersize
        container.add(textfield,BorderLayout.NORTH);

        MyButtons = new JPanel();
        MyButtons.setLayout(new GridLayout(4,4));
        buttons = new JButton[name.length];
        for(int i = 0;i < name.length;i++)
        {
            buttons[i] = new JButton(name[i]);
            buttons[i].addActionListener(new MyActionListener());
            MyButtons.add(buttons[i]);
        }
        container.add(MyButtons,BorderLayout.CENTER);
        setVisible(true);

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }

    public static void main(String agrs[])
    {

        Window w = new Window();
    }

    class MyActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String path = textfield.getText();
            Mat imread =  Imgcodecs.imread(path);
            if(e.getActionCommand() == "去雾"){
               mat = PhotoFactory.Filter.disFog(imread, 20);
               imshow();
            }else if(e.getActionCommand() == "油画"){
                mat = PhotoFactory.Filter.oilPainting(imread);
                imshow();
            }else if(e.getActionCommand() == "锐化"){
                mat = PhotoFactory.Filter.sharpen(imread, 10, 2);
                imshow();
            }else if(e.getActionCommand() == "边缘获取"){
                mat = PhotoFactory.Filter.edgeDetection(imread);
                imshow();
            }
        }

    }

    public void imshow() {

        Image loadedImage = toBufferedImage(mat);
        JFrame frame = createJFrame(mat.width(), mat.height());
        imageView.setIcon(new ImageIcon(loadedImage));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    private JFrame createJFrame( int width, int height) {
        JFrame frame = new JFrame();
        imageView = new JLabel();
        final JScrollPane imageScrollPane = new JScrollPane(imageView);
        imageScrollPane.setPreferredSize(new Dimension(width, height));
        frame.add(imageScrollPane, BorderLayout.CENTER);
        return frame;
    }

    private Image toBufferedImage(Mat matrix) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (matrix.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = matrix.channels() * matrix.cols() * matrix.rows();
        byte[] buffer = new byte[bufferSize];
        matrix.get(0, 0, buffer);
        // 获取所有的像素点
        BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }

}
