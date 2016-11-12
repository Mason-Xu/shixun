package xiangmu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class BirdGame extends JPanel {

    Bird bird;
    Column column1, column2;
    Ground ground;
    BufferedImage background;
    Huaji huaji1;

    Boolean gameOver;
    /**
     * 游戏状态
     */
    int state;
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int GAME_OVER = 2;
    BufferedImage gameOverImage;
    BufferedImage startImage;


    //分数
    int score;

    /**
     * 初始化BirdGame 的属性变量
     */
    public BirdGame() throws Exception {

        gameOver = false;
        state = START;
        startImage = ImageIO.read(getClass().getResource("start.jpg"));
        gameOverImage = ImageIO.read(getClass().getResource("gameover.jpg"));
        bird = new Bird();
        huaji1 = new Huaji(1);
        column1 = new Column(1);
        column2 = new Column(2);
        ground = new Ground();

        background = ImageIO.read(getClass().getResource("bg.jpg"));
    }

    /**
     * BirdGame中添加方法action()
     */
    public void action() throws Exception {
        MouseListener l = new MouseAdapter() {
            //鼠标按下
            public void mousePressed(MouseEvent e) {
                try {
                    switch (state) {
                        case GAME_OVER:
                            column1 = new Column(1);
                            column2 = new Column(2);
                            bird = new Bird();
                            huaji1 = new Huaji(1);
                            score = 0;
                            state = START;
                            break;
                        case START:
                            state = RUNNING;
                            break;
                        case RUNNING:

                            //让小鸟向上飞翔
                            bird.flappy();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };//鼠标监听事件
        addMouseListener(l);
        while (true) {
//				if(!gameOver){
//					ground.step();
//					column1.step();
//					column2.step();
//					bird.step();
//					bird.fly();
//
//				}
//
//
            switch (state) {
                case START:
                    bird.fly();
                    ground.step();
                    break;
                case RUNNING:
                    column1.step();
                    column2.step();
                    bird.step();
                    bird.fly();
                    ground.step();
                    if (bird.hit(ground) || bird.hit(column1) || bird.hit(column2)) {
                        state = GAME_OVER;
                    }

                    //计分逻辑
                    if (bird.x == column1.x || bird.x == column2.x) {
                        score += 10;
                    }
            }
            repaint();
            Thread.sleep(1000 / 30);//一秒刷新30次
        }
    }

    /**
     * 启动软件的方法
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame();
        BirdGame game = new BirdGame();
        frame.add(game);
        frame.setSize(440, 670);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.action();

    }

    /**
     * 重写（修改）paint方法实现绘制
     */
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(column1.image, column1.x - column1.width / 2, column1.y - column1.height / 2, null);
        g.drawImage(column2.image, column2.x - column2.width / 2, column2.y - column2.height / 2, null);
        g.drawImage(ground.image, ground.x, ground.y, null);
        g.drawImage(huaji1.image,column1.x - column1.width / 2,200,null);

        //旋转(rotate)绘图坐标系，是API的方法
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(-bird.alpha, bird.x, bird.y);
        g.drawImage(bird.image, bird.x - bird.width / 2, bird.y - bird.height / 2, null);
        g2.rotate(bird.alpha, bird.x, bird.y);


        //在panit方法中添加绘制分数的方法
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 40);
        g.setFont(f);
        g.drawString("" + score, 40, 60);
        g.setColor(Color.white);
        g.drawString("" + score, 40 - 3, 60 - 3);

        //	if(gameOver) {
        //		g.drawImage(gameOverImage,0,0,null);
        //	}
        //在paint方法中添加显示游戏状态的代码
        switch (state) {
            case GAME_OVER:
                g.drawImage(gameOverImage, 0, 0, null);
                break;
            case START:
                g.drawImage(startImage, 0, 0, null);
                break;
        }
    }

    /**
     * 地面
     */
    class Ground {
        BufferedImage image;
        int x, y;
        int width;
        int height;

        public Ground() throws Exception {
            image = ImageIO.read(getClass().getResource("ground.jpg"));
            width = image.getWidth();
            height = image.getHeight();
            x = 0;
            y = 500;
            height = image.getHeight();
            x = 0;
            y = 500;
        }

        //地面的类中，添加方法，地面移动一步
        public void step() {
            x--;
            if (x == -109) {
                x = 0;
            }
        }
    }

    /**
     * 柱子类型，x，y是柱子的中心点的位置
     */
    class Column {
        BufferedImage image;
        int x, y;
        int width;
        int height;
        int gap;//柱子间的缝隙
        int distance;//俩个柱子间的距离
        Random random = new Random();/**生成随机数*/
        /**
         * 构造器：初始化数据，n代表第几个柱子
         */
        public Column(int n) throws Exception {
            image = ImageIO.read(getClass().getResource("column.jpg"));
            width = image.getWidth();
            height = image.getHeight();
            gap = 144;
            distance = 245;
            x = 550 + (n - 1) * distance;
            y = random.nextInt(218) + 132;
        }

        /**
         * 在Column类中方法step,在action方法中调用
         */
        public void step() {
            x--;
            if (x == -width / 2) {
                x = distance * 2 - width / 2;
                y = random.nextInt(218) + 132;
            }
        }

    }

    /**
     * 鸟的类型，x,y是鸟的类型的中心位置
     */
    class Bird {
        BufferedImage image;
        int x, y;
        int width;
        int height;
        int size;//鸟的大小，用于碰撞检测
        //在Bird类中增加属性，用于计算鸟的位置
        double g;//重力加速度
        double t;//两次位置的时间间隔
        double v0;//初始上抛速度
        double speed;//当前的上抛速度
        double s;//经过时间t后的位移
        double alpha;//是鸟的倾角 弧度单位

        //定义一组图片，是鸟的动画帧
        BufferedImage[] images;
        //定义动画帧数组的下标位置
        int index;


        public Bird() throws Exception {
            image = ImageIO.read(getClass().getResource("0.jpg"));
            width = image.getWidth();
            height = image.getHeight();
            x = 132;
            y = 280;
            size = 40;

            g = 9.8;//定义加速度
            v0 = 20;//定义初始速度
            t = 0.25;//定义时间
            speed = v0;
            s = 0;//位移
            alpha = 0;//倾角

            //创建一个数组，包含八个元素
            images = new BufferedImage[8];
            for (int i = 0; i < 8; i++) {
                images[i] = ImageIO.read(getClass().getResource(i + ".jpg"));
            }
            index = 0;
        }

        //在Bird类里面添加飞翔（fly）的方法
        public void fly() {
            index++;
            image = images[(index / 12) % 8];
        }

        //在Bird类中，添加鸟的移动方法
        public void step() {
            double v0 = speed;
            s = v0 * t + t * t * g / 2;
            y = y - (int) s;//计算鸟的坐标位置
            double v = v0 - g * t;//计算下次的速度
            speed = v;

            //调用JAVA API提供的反正切函数，计算倾角
            alpha = Math.atan(s / 8);
        }

        public void flappy() {
            //重新设置初始速度，重新向上飞
            speed = v0;
        }

        public boolean hit(Ground ground) {
            boolean hit = y + size / 2 > ground.y;
            if (hit) {
                //将小鸟放置到地面上
                y = ground.y - size / 2;
                //为什么要设置倾角呢，使小鸟坠地时，有摔倒的效果
                alpha = -3.1415926 / 2;
            }
            return hit;
        }

        //检测当前的鸟是否撞到柱子
        public boolean hit(Column column) {
            //先检测是否在柱子的范围之内
            if (x > column.x - column.width / 2 - size / 2 && x < column.x + column.width / +size / 2) {
                //检测是否在柱子的缝隙之内
                if (y > column.y - column.gap / 2 + size / 2 && y < column.y + column.gap / 2 - size / 2) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    class Huaji {
        BufferedImage image;
        int x, y;
        int width;
        int height;
        int distance;
        Random random = new Random();

        public Huaji(int n) throws Exception {
            image = ImageIO.read(getClass().getResource("huaji.jpg"));
            width = image.getWidth();
            height = image.getHeight();
            distance = 245;
            x = 550 + (n - 1) * distance;
            y = random.nextInt(218) + 132;
        }

        public void step() {
            x--;
            if (x == -width / 2) {
                x = distance * 2 - width / 2;
                y = random.nextInt(218) + 132;
            }
        }
    }
}

